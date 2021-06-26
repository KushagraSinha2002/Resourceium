package com.bitlegion.server.uploads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.Token;
import com.bitlegion.server.accounts.TokenChecker;
import com.bitlegion.server.general.Sleeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

class SortByDateOfUpload implements Comparator<Document> {

    @Override
    public int compare(Document a, Document b) {
        return b.getDateOfUpload().compareTo(a.getDateOfUpload());
    }
}

@Service
@RequestMapping(path = "/folders")
@CrossOrigin
public class FolderController {

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private Sleeper sleeper;

    @GetMapping("/user-folders")
    public ResponseEntity<Iterable<Folder>> getListFolders(HttpServletRequest request) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Collection<Folder> folders = folderRepository.findByAccountOrderByLastEditedDesc(token.getAccount());
            sleeper.pause(2000);
            return ResponseEntity.status(HttpStatus.OK).body(folders);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user-favorite-folders")
    public ResponseEntity<Iterable<Folder>> getListFavoriteFolders(HttpServletRequest request) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Iterable<Folder> folders = folderRepository.findAllByFavoritesAccount(token.getAccount());
            sleeper.pause(2000);
            return ResponseEntity.status(HttpStatus.OK).body(folders);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/by-tags")
    public ResponseEntity<Iterable<Folder>> getListFoldersByTags(@RequestParam String tags) {
        sleeper.pause(3000);
        if (tags.isEmpty()) {
            Iterable<Folder> folders = folderRepository.findAll();
            sleeper.pause(2000);
            return ResponseEntity.status(HttpStatus.OK).body(folders);
        }
        ArrayList<Folder> folders = new ArrayList<Folder>();
        String[] requestedTags = tags.split(",");
        for (String tag : requestedTags) {
            Iterable<Folder> tagFolders = folderRepository.findAllByTagsNameContainingIgnoreCase(tag);
            folders.addAll((Collection<? extends Folder>) tagFolders);
        }
        return ResponseEntity.status(HttpStatus.OK).body(folders);
    }

    @GetMapping("/folder/{folderID}")
    public ResponseEntity<Folder> getFolderDetails(@PathVariable Integer folderID) {
        Optional<Folder> maybeFolder = folderRepository.findById(folderID);
        if (maybeFolder.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Folder folder = maybeFolder.get();
            Collection<Document> documents = folder.getDocuments();
            ArrayList<Document> newDocuments = new ArrayList<>(documents);
            Collections.sort(newDocuments, new SortByDateOfUpload());
            folder.setDocuments(newDocuments);
            return ResponseEntity.status(HttpStatus.OK).body(folder);
        }
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<?> createFolder(HttpServletRequest request,
            @RequestBody HashMap<String, String> reqBody) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            String title = reqBody.get("title");
            String description = reqBody.get("description");
            if (!folderRepository.findByAccountAndTitle(token.getAccount(), title).isEmpty()) {
                return ResponseEntity.badRequest().body("A folder with this name already exists.");
            }
            Folder folder = new Folder();
            folder.setTitle(title);
            folder.setAccount(token.getAccount());
            if (!description.isEmpty()) {
                folder.setDescription(description);
            }
            folderRepository.save(folder);
            return ResponseEntity.status(HttpStatus.OK).body(folder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{folderID}")
    public @ResponseBody ResponseEntity<?> deleteFolder(HttpServletRequest request, @PathVariable Integer folderID) {
        try {
            tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Optional<Folder> maybeFolder = folderRepository.findById(folderID);
            if (maybeFolder.isEmpty()) {
                return ResponseEntity.badRequest().body("The requested folder does not exist.");
            }
            Folder folder = maybeFolder.get();
            Iterable<Document> documents = documentRepository.findByFolder(folder);
            // delete all associated documents
            documentRepository.deleteAll(documents);
            folderRepository.delete(folder);
            return ResponseEntity.status(HttpStatus.OK).body("Folder deleted successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/rename/{folderID}")
    public @ResponseBody ResponseEntity<?> renameFolder(HttpServletRequest request, @PathVariable Integer folderID,
            @RequestBody HashMap<String, String> reqBody) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Optional<Folder> maybeFolder = folderRepository.findById(folderID);
            if (maybeFolder.isEmpty()) {
                return ResponseEntity.badRequest().body("The requested folder does not exist.");
            } else {
                String title = reqBody.get("title");
                if (!folderRepository.findByAccountAndTitle(token.getAccount(), title).isEmpty()) {
                    return ResponseEntity.badRequest().body("A folder with this name already exists.");
                }
                Folder folder = maybeFolder.get();
                folder.setTitle(title);
                folderRepository.save(folder);
                return ResponseEntity.status(HttpStatus.OK).body("Folder renamed successfully!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
