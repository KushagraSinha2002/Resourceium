package com.bitlegion.server.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/")
@CrossOrigin
public class PingController {

    @Autowired
    private Sleeper sleeper;

    @GetMapping
    public @ResponseBody ResponseEntity<String> pingServer() {
        sleeper.pause(5000);
        return ResponseEntity.ok().body("ping successful!");
    }

}
