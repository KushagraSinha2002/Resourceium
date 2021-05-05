package com.bitlegion.server.uploads;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.AccountRepository;

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

@Controller
@RequestMapping(path = "/folders")
@CrossOrigin
public class FoldersController {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/{userID}")
    public ResponseEntity<Iterable<Folder>> getListFolders(@PathVariable Integer userID) {
        Optional<Account> maybeUser = accountRepository.findById(userID);
        if (maybeUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Collection<Folder> folders = folderRepository.findByAccount(maybeUser.get());
        return ResponseEntity.status(HttpStatus.OK).body(folders);
    }

    @GetMapping("/folder/{folderID}")
    public ResponseEntity<Folder> getFolderDetails(@PathVariable Integer folderID) {
        Optional<Folder> maybeFolder = folderRepository.findById(folderID);
        if (maybeFolder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(maybeFolder.get());
    }

    @PostMapping("/create/{userID}")
    public ResponseEntity<Folder> createFolder(@PathVariable Integer userID,
            @RequestBody HashMap<String, String> reqBody) {
        Optional<Account> maybeUser = accountRepository.findById(userID);
        if (maybeUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String title = reqBody.get("title");
        Folder folder = new Folder();
        folder.setTitle(title);
        folder.setAccount(maybeUser.get());
        folderRepository.save(folder);
        return ResponseEntity.status(HttpStatus.OK).body(folder);
    }
}
