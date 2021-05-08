package com.bitlegion.server.uploads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/uploads")
@CrossOrigin
public class DocumentController {

    // @Autowired
    // private FilesStorageService storageService;

    @Autowired
    private DocumentRepository fileRepository;

    @GetMapping("/all")
    public @ResponseBody Iterable<Document> getListFiles() {
        return fileRepository.findAll();
    }
}
