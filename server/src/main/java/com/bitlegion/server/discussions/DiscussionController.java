package com.bitlegion.server.discussions;

import java.util.Arrays;
import java.util.HashMap;
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

    @GetMapping("/account/{accountID}")
    public @ResponseBody Iterable<Discussion> getListDiscussionsByAccount(HttpServletRequest request,
            @PathVariable Integer accountID) {
        Optional<Account> maybeAccount = accountRepository.findById(accountID);
        Account account = maybeAccount.get();
        return account.getDiscussions();
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<?> createDiscussion(HttpServletRequest request,
            @RequestBody HashMap<String, String> reqBody) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            String name = reqBody.get("name");
            if (discussionRepository.findByNameIgnoreCase(name).isPresent()) {
                return ResponseEntity.badRequest().body("A discussion with this name already exists");
            }
            Discussion discussion = new Discussion();
            String description = reqBody.get("description");
            discussion.setCreatedBy(token.getAccount());
            discussion.setName(name);
            discussion.setDescription(description);
            discussionRepository.save(discussion);
            String stringifiedUserIDs = reqBody.get("userIDs");
            if (stringifiedUserIDs != null) {
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
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
