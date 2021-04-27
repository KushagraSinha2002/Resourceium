package com.bitlegion.server.uploads;

import java.util.List;
import java.util.Optional;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

@CrossOrigin
@Controller
@RequestMapping(path = "/uploads")
public class FilesController {

    @Autowired
    private FilesStorageService storageService;

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
            try {
                String filename = storageService.smartSave(file, user.getId());
                Upload fileModel = new Upload();
                fileModel.setName(file.getOriginalFilename());
                fileModel.setSlug(filename);
                fileModel.setAccount(user);
                user.getFiles().add(fileModel);
                fileRepository.save(fileModel);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        userRepository.save(user);
        message = "Uploaded " + files.size() + " files successfully!";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Upload> getListFiles() {
        return fileRepository.findAll();
    }

    @GetMapping("/download/{userId}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Integer userId, @PathVariable String filename) {
        Account userModel = userRepository.findById(userId).get();
        Upload fileModel = fileRepository.findBySlugAndUserID(filename, userModel.getId()).get();
        Resource file = storageService.load(fileModel.getFileLocation());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getName() + "\"")
                .body(file);
    }
}
