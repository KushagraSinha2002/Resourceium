package com.bitlegion.server.uploads;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.TokenChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.AccountRepository;
import com.bitlegion.server.accounts.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/documents")
@CrossOrigin
public class DocumentController {

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private DocumentRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private AccountRepository userRepository;

    @GetMapping("/all")
    public @ResponseBody Iterable<Document> getListFiles() {
        return fileRepository.findAll();
    }

    @PostMapping("/upload/{folderID}")
    public ResponseEntity<Document> uploadFile(HttpServletRequest request, @PathVariable Integer folderID,
            @RequestBody HashMap<String, String> reqBody) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Account account = token.getAccount();
            Folder folder = folderRepository.findById(folderID).get();
            Document document = new Document();
            String name = reqBody.get("name");
            Long storageID = Long.valueOf((reqBody.get("storageID")));
            Double size = Double.valueOf(reqBody.get("size"));
            document.setName(name);
            document.setStorageID(storageID);
            document.setFolder(folder);
            document.setSize(size);
            documentRepository.save(document);
            folder.addDocument(document);
            folder.setLastEdited(new Date());
            userRepository.save(account);
            folderRepository.save(folder);
            return ResponseEntity.status(HttpStatus.OK).body(document);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{fileID}")
    public ResponseEntity<Document> uploadFile(HttpServletRequest request, @PathVariable Integer fileID) {
        try {
            tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Optional<Document> maybeDocument = documentRepository.findById(fileID);
            if (maybeDocument.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                Document document = maybeDocument.get();
                Folder folder = document.getFolder();
                folder.removeDocument(document);
                documentRepository.delete(document);
                folderRepository.save(folder);
                return ResponseEntity.status(HttpStatus.OK).body(document);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
