package com.bitlegion.server.discussions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("/all")
    public @ResponseBody Iterable<Discussion> getListDiscussions() {
        return discussionRepository.findAll();
    }

    @GetMapping(path = "/get-by-name")
    public ResponseEntity<Collection<Discussion>> getByName(@RequestParam String name) {
        Collection<Discussion> discussions = discussionRepository.findAllByNameContainingIgnoreCase(name);
        return ResponseEntity.status(HttpStatus.OK).body(discussions);
    }

    @GetMapping("/account/{accountID}")
    public @ResponseBody Iterable<Discussion> getListDiscussionsByAccount(HttpServletRequest request,
            @PathVariable Integer accountID) {
        Optional<Account> maybeAccount = accountRepository.findById(accountID);
        Account account = maybeAccount.get();
        return account.getDiscussions();
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
            discussionRepository.save(discussion);
            String stringifiedUserIDs = discussionDetails.getUserIDs();
            System.out.println(stringifiedUserIDs);
            if (!stringifiedUserIDs.isEmpty()) {
                List<Integer> ids = Arrays.stream(stringifiedUserIDs.split(",")).map(Integer::parseInt)
                        .collect(Collectors.toList());
                Iterable<Account> accounts = accountRepository.findAllById(ids);
                accounts.forEach(acc -> {
                    acc.addDiscussion(discussion);
                    accountRepository.save(acc);
                });
            }
            return ResponseEntity.status(HttpStatus.OK).body(discussion);
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace()[0].toString());
            return ResponseEntity.badRequest().build();
        }
    }
}
