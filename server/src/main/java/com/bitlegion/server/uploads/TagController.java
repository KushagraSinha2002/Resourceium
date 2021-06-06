package com.bitlegion.server.uploads;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.TokenChecker;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(path = "/tags")
@CrossOrigin
public class TagController {

  @Autowired
  private TokenChecker tokenChecker;

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private FolderRepository folderRepository;

  @GetMapping("/folder/{folderID}")
  public ResponseEntity<Set<Tag>> getFolderTagDetails(@PathVariable Integer folderID) {
    Optional<Folder> maybeFolder = folderRepository.findById(folderID);
    if (maybeFolder.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      Folder folder = maybeFolder.get();
      Set<Tag> tags = folder.getTags();
      return ResponseEntity.status(HttpStatus.OK).body(tags);
    }
  }

  @PostMapping("/create/{folderID}")
  public @ResponseBody ResponseEntity<?> addTag(HttpServletRequest request, @PathVariable Integer folderID,
      @RequestBody HashMap<String, String> reqBody) {
    try {
      tokenChecker.checkAndReturnTokenOrRaiseException(request);
      String name = reqBody.get("name");
      String colour = reqBody.get("colour");
      Optional<Folder> maybeFolder = folderRepository.findById(folderID);
      if (maybeFolder.isEmpty()) {
        return ResponseEntity.badRequest().body("The requested folder does not exist.");
      }
      Folder folder = maybeFolder.get();
      Optional<Tag> maybeTag = tagRepository.findByName(name);
      Tag tag;
      if (maybeTag.isEmpty()) {
        tag = new Tag();
        tag.setName(name);
        tag.setColour(colour);
        tagRepository.save(tag);
      } else {
        tag = maybeTag.get();
      }
      folder.addTag(tag);
      folderRepository.save(folder);
      return ResponseEntity.status(HttpStatus.OK).body(tag);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }
}
