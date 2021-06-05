package com.bitlegion.server.uploads;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.Token;
import com.bitlegion.server.accounts.TokenChecker;
import com.bitlegion.server.uploads.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(path = "/Tags")
@CrossOrigin
public class TagController {

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private TagRepository tagRepository;
    
    @PostMapping("/addTag")
    public @ResponseBody ResponseEntity<?> addTag(HttpServletRequest request,
            @RequestBody HashMap<String, String> reqBody) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            String name = reqBody.get("name");
            String colour = reqBody.get("colour");
            if (!tagRepository.findByNameAndColour(name, colour).isEmpty()) {
                return ResponseEntity.badRequest().body("A Tag with the same colour exists.");
            }
            Tag tag = new Tag();
            tag.setName(name);
            tag.setColour(colour);
            tagRepository.save(tag);
            return ResponseEntity.status(HttpStatus.OK).body(tag);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
