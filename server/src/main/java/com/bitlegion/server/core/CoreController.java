package com.bitlegion.server.core;

import java.util.HashMap;

import com.bitlegion.server.accounts.AccountRepository;
import com.bitlegion.server.uploads.FileRepository;
import com.bitlegion.server.uploads.FolderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/core")
@CrossOrigin
public class CoreController {
    @Autowired
    private AccountRepository accountRepository;

    @Value("${spring-dev-mode}")
    private Boolean devMode;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    @GetMapping(path = "/stats")
    public @ResponseBody HashMap<String, Long> getStats() {
        HashMap<String, Long> map = new HashMap<>();

        if (devMode) {
            map.put("accountsCount", (long) 259);
            map.put("filesCount", (long) 69);
            map.put("foldersCount", (long) 800);
            return map;
        } else {
            map.put("accountsCount", accountRepository.count());
            map.put("filesCount", fileRepository.count());
            map.put("foldersCount", folderRepository.count());
            return map;
        }
    }
}