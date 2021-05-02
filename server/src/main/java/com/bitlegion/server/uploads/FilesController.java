package com.bitlegion.server.uploads;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.AccountRepository;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/uploads")
@CrossOrigin
public class FilesController {

    // @Autowired
    // private FilesStorageService storageService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AccountRepository userRepository;

    // This endpoint can be used to append a number of files for the user with ID
    // `userID`
    @PostMapping("/upload/{userID}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam List<MultipartFile> files,
            @PathVariable Integer userID) {

        Optional<Account> userModel = userRepository.findById(userID);
        String message = "";

        if (!userModel.isPresent()) {
            message = "The requested user does not exist";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }

        Account user = userModel.get();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);

            Path filepath = Paths.get("/tmp", file.getOriginalFilename());
            try {
                file.transferTo(filepath);
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
            String URL = System.getenv("STORAGE_SERVER") + "files/upload/";

            try {
                java.io.File fileObj = new java.io.File(filepath.toString());
                try (CloseableHttpClient client = HttpClients.createDefault()) {
                    HttpPost post = new HttpPost(URL);
                    HttpEntity entity = MultipartEntityBuilder.create()
                            .addTextBody("user_id", user.getId().toString(), ContentType.APPLICATION_JSON)
                            .addPart("file", new FileBody(fileObj)).build();
                    post.setEntity(entity);
                    try {
                        CloseableHttpResponse response = client.execute(post);
                        HttpEntity entityResponse = response.getEntity();
                        String result = EntityUtils.toString(entityResponse);
                        JSONParser parser = new JSONParser();
                        JSONObject json = (JSONObject) parser.parse(result);
                        try {
                            Document fileModel = new Document();
                            fileModel.setName(file.getOriginalFilename());
                            fileModel.setUrl((String) json.get("url"));
                            fileRepository.save(fileModel);
                        } catch (Exception e) {
                            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                                    .body(new ResponseMessage(message));
                        }
                    } catch (Exception e) {
                        System.out.println("error: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        }
        userRepository.save(user);
        message = "Uploaded " + files.size() + " files successfully!";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Document> getListFiles() {
        return fileRepository.findAll();
    }

    // @GetMapping("/download")
    // @ResponseBody
    // public ResponseEntity<Resource> getFile(@RequestParam Integer userId,
    // @RequestParam String filename) {
    // Account userModel = userRepository.findById(userId).get();
    // Document fileModel = fileRepository.findBySlugAndUserID(filename,
    // userModel.getId()).get();
    // Resource file = storageService.load(fileModel.getFileLocation());
    // return ResponseEntity.ok()
    // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
    // fileModel.getName() + "\"")
    // .body(file);
    // }
}
