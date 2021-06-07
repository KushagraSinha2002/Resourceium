package com.bitlegion.server.discussions;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.Token;
import com.bitlegion.server.accounts.TokenChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/all")
    public @ResponseBody Iterable<Discussion> getListDiscussions() {
        return discussionRepository.findAll();
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<?> createDiscussion(HttpServletRequest request,
            @RequestBody HashMap<String, String> reqBody) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            String description = reqBody.get("description");
            String name = reqBody.get("name");
            if (discussionRepository.findByNameIgnoreCase(name).isPresent()) {
                return ResponseEntity.badRequest().body("A discussion with this name already exists");
            }
            Discussion discussion = new Discussion();
            discussion.setCreatedBy(token.getAccount());
            discussion.setName(name);
            discussion.setDescription(description);
            discussionRepository.save(discussion);
            return ResponseEntity.status(HttpStatus.OK).body(discussion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
