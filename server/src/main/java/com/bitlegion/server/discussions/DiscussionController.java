package com.bitlegion.server.discussions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.AccountRepository;
import com.bitlegion.server.accounts.Token;
import com.bitlegion.server.accounts.TokenChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/discussions")
@CrossOrigin
public class DiscussionController {

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/{discussionID}")
    public @ResponseBody Discussion getDiscussion(@PathVariable Integer discussionID) {
        return discussionRepository.findById(discussionID).get();
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Discussion> getListDiscussions() {
        return discussionRepository.findAll();
    }

    @GetMapping(path = "/get-by-name")
    public ResponseEntity<Collection<Discussion>> getByName(@RequestParam String name) {
        Collection<Discussion> discussions = discussionRepository.findAllByNameContainingIgnoreCase(name);
        return ResponseEntity.status(HttpStatus.OK).body(discussions);
    }

    @DeleteMapping("/remove-account/{discussionID}/{accountID}")
    public @ResponseBody ResponseEntity<?> removeAccount(HttpServletRequest request, @PathVariable Integer discussionID,
            @PathVariable Integer accountID) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Optional<Discussion> maybeDiscussion = discussionRepository.findById(discussionID);
            Optional<Account> maybeAccount = accountRepository.findById(accountID);
            if (maybeDiscussion.isEmpty() || maybeAccount.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Discussion discussion = maybeDiscussion.get();
            Account requester = token.getAccount();
            Account createdBy = discussion.getCreatedBy();
            Account account = maybeAccount.get();
            System.out.println();
            System.out.println();
            System.out.println(requester);
            System.out.println(createdBy);
            System.out.println();
            System.out.println();
            if (createdBy.getId() != requester.getId()) {
                return ResponseEntity.badRequest()
                        .body("You do not have the permission to remove from this discussion");
            }
            if (account == createdBy) {
                return ResponseEntity.badRequest().body("You can not remove yourself");
            }
            discussion.removeAccount(account);
            discussionRepository.save(discussion);
            account.removeDiscussion(discussion);
            accountRepository.save(account);
            return ResponseEntity.ok().body(discussion);
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace()[0].toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/accounts/{discussionID}")
    public @ResponseBody ResponseEntity<Set<Account>> getDiscussionAccounts(@PathVariable Integer discussionID) {
        Optional<Discussion> maybeDiscussion = discussionRepository.findById(discussionID);
        if (maybeDiscussion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Discussion discussion = maybeDiscussion.get();
        return ResponseEntity.ok().body(discussion.getAccounts());
    }

    @GetMapping("/account/{accountID}")
    public @ResponseBody Iterable<Discussion> getListDiscussionsByAccount(HttpServletRequest request,
            @PathVariable Integer accountID) {
        Optional<Account> maybeAccount = accountRepository.findById(accountID);
        Account account = maybeAccount.get();
        return account.getDiscussions();
    }

    @PostMapping("/join")
    public @ResponseBody ResponseEntity<?> joinDiscussion(HttpServletRequest request,
            @RequestParam String inviteString) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Optional<Discussion> maybeDiscussion = discussionRepository.findByInviteString(inviteString);
            if (maybeDiscussion.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Account account = token.getAccount();
            Discussion discussion = maybeDiscussion.get();

            discussion.addAccount(account);
            discussionRepository.save(discussion);

            account.addDiscussion(discussion);
            accountRepository.save(account);
            return ResponseEntity.ok().body(discussion);
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace()[0].toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<?> createDiscussion(HttpServletRequest request,
            @RequestBody DiscussionDetails discussionDetails) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            if (discussionRepository.findByNameIgnoreCase(discussionDetails.getName()).isPresent()) {
                return ResponseEntity.badRequest().body("A discussion with this name already exists");
            }
            Discussion discussion = new Discussion();
            discussion.setCreatedBy(token.getAccount());
            discussion.setName(discussionDetails.getName());
            discussion.setDescription(discussionDetails.getDescription());
            discussion.setInviteString();
            discussionRepository.save(discussion);
            Account account = token.getAccount();
            account.addDiscussion(discussion);
            accountRepository.save(account);

            discussion.addAccount(account);
            discussionRepository.save(discussion);

            String stringifiedUserIDs = discussionDetails.getUserIDs();
            if (!stringifiedUserIDs.isEmpty()) {
                List<Integer> ids = Arrays.stream(stringifiedUserIDs.split(",")).map(Integer::parseInt)
                        .collect(Collectors.toList());
                Iterable<Account> accounts = accountRepository.findAllById(ids);
                accounts.forEach(acc -> {
                    acc.addDiscussion(discussion);
                    accountRepository.save(acc);
                });
                accounts.forEach(acc -> {
                    discussion.addAccount(acc);
                });
                discussionRepository.save(discussion);
            }
            return ResponseEntity.status(HttpStatus.OK).body(discussion);
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace()[0].toString());
            return ResponseEntity.badRequest().build();
        }
    }
}
