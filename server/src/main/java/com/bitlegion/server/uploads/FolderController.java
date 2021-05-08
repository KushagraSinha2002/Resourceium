package com.bitlegion.server.uploads;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.AccountRepository;
import com.bitlegion.server.accounts.Token;
import com.bitlegion.server.accounts.TokenChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Service
@RequestMapping(path = "/folders")
@CrossOrigin
public class FolderController {

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private AccountRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/user-folders")
    public ResponseEntity<Iterable<Folder>> getListFolders(HttpServletRequest request) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Collection<Folder> folders = folderRepository.findByAccount(token.getAccount());
            return ResponseEntity.status(HttpStatus.OK).body(folders);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/folder/{folderID}")
    public ResponseEntity<Folder> getFolderDetails(@PathVariable Integer folderID) {
        Optional<Folder> maybeFolder = folderRepository.findById(folderID);
        if (maybeFolder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(maybeFolder.get());
    }

    @PostMapping("/create")
    public ResponseEntity<Folder> createFolder(HttpServletRequest request,
            @RequestBody HashMap<String, String> reqBody) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            String title = reqBody.get("title");
            Folder folder = new Folder();
            folder.setTitle(title);
            folder.setAccount(token.getAccount());
            folderRepository.save(folder);
            return ResponseEntity.status(HttpStatus.OK).body(folder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/upload-file/{folderID}")
    public ResponseEntity<Document> uploadFile(HttpServletRequest request, @RequestParam MultipartFile file,
            @PathVariable Integer folderID) {
        try {

            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Account account = token.getAccount();
            Folder folder = folderRepository.findById(folderID).get();
            Path filepath = Paths.get(System.getProperty("java.io.tmpdir"), file.getOriginalFilename());
            try {
                file.transferTo(filepath);
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
                return ResponseEntity.badRequest().build();
            }
            try {
                String URL = System.getenv("STORAGE_SERVER") + "files/upload/" + account.getId().toString() + "/"
                        + folder.getId();
                File diskFile = new File(filepath.toString());
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost post = new HttpPost(URL);
                HttpEntity entity = MultipartEntityBuilder.create().addPart("file", new FileBody(diskFile)).build();
                post.setEntity(entity);
                CloseableHttpResponse response = client.execute(post);
                HttpEntity entityResponse = response.getEntity();
                String result = EntityUtils.toString(entityResponse);
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(result);
                Document document = new Document();
                document.setName(file.getOriginalFilename());
                document.setStorageID((Long) json.get("storageID"));
                document.setFolder(folder);
                fileRepository.save(document);
                folder.setDocument(document);
                userRepository.save(account);
                folderRepository.save(folder);
                return ResponseEntity.status(HttpStatus.OK).body(document);
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}