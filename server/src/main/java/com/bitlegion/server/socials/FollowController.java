package com.bitlegion.server.socials;

import java.util.Collection;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/follow")
@CrossOrigin
public class FollowController {

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FollowRepository followRepository;

    /* Get all the followers of this account */
    @GetMapping(path = "/followers/{accountID}")
    public ResponseEntity<Collection<Follow>> getFollowers(@PathVariable Integer accountID) {
        Optional<Account> maybeAccount = accountRepository.findById(accountID);
        if (maybeAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Account account = maybeAccount.get();
        Collection<Follow> followers = followRepository.findAllByFollowing(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(followers);
    }

    /* Get all the accounts this account follows */
    @GetMapping(path = "/following/{accountID}")
    public ResponseEntity<Collection<Follow>> getFollowings(@PathVariable Integer accountID) {
        Optional<Account> maybeAccount = accountRepository.findById(accountID);
        if (maybeAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Account account = maybeAccount.get();
        Collection<Follow> followings = followRepository.findAllByFollower(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(followings);
    }

    @PostMapping(path = "/toggle/{followingID}")
    public ResponseEntity<?> toggleFollow(HttpServletRequest request, @PathVariable Integer followingID) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Account follower = token.getAccount();
            Optional<Account> maybeFollowing = accountRepository.findById(followingID);
            if (maybeFollowing.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Account following = maybeFollowing.get();
            Optional<Follow> maybeFollow = followRepository.findByFollowerAndFollowing(follower, following);
            if (maybeFollow.isPresent()) {
                Follow follow = maybeFollow.get();
                followRepository.delete(follow);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted successfully!");
            }
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            followRepository.save(follow);
            return ResponseEntity.status(HttpStatus.CREATED).body(follow);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
