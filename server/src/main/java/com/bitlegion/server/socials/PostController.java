package com.bitlegion.server.socials;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.Token;
import com.bitlegion.server.accounts.TokenChecker;
import com.bitlegion.server.discussions.Discussion;
import com.bitlegion.server.discussions.DiscussionRepository;
import com.bitlegion.server.uploads.Folder;
import com.bitlegion.server.uploads.FolderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@Controller
@RequestMapping(path = "/post")
@CrossOrigin
public class PostController {

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FolderRepository folderRepository;

    /* Get post count of this folder */
    @GetMapping(path = "/{folderID}")
    public ResponseEntity<Integer> getPostsCount(@PathVariable Integer folderID) {
        Optional<Folder> maybeFolder = folderRepository.findById(folderID);
        if (maybeFolder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Folder folder = maybeFolder.get();
        Integer posts = postRepository.countByFolder(folder);
        return ResponseEntity.status(HttpStatus.CREATED).body(posts);
    }

    /* Get sorted posts of this discussion, with limits */
    @GetMapping(path = "/discussion/{discussionID}")
    public ResponseEntity<Page<Post>> getPostsByDiscussion(@PathVariable Integer discussionID,
            @RequestParam(defaultValue = "0") Integer page) {
        Optional<Discussion> maybeDiscussion = discussionRepository.findById(discussionID);
        if (maybeDiscussion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Discussion discussion = maybeDiscussion.get();
        // we always want 10 posts at a time
        Pageable pageable = PageRequest.of(page, 10, Sort.by("creationTime").descending());
        Page<Post> posts = postRepository.findAllByDiscussion(discussion, pageable);
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping("/create/{folderID}/{discussionID}")
    public @ResponseBody ResponseEntity<Post> createPost(HttpServletRequest request, @PathVariable Integer folderID,
            @PathVariable Integer discussionID) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Optional<Folder> maybeFolder = folderRepository.findById(folderID);
            Optional<Discussion> maybeDiscussion = discussionRepository.findById(discussionID);
            if (maybeFolder.isEmpty() || maybeDiscussion.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Folder folder = maybeFolder.get();
            Discussion discussion = maybeDiscussion.get();
            Account sharedBy = token.getAccount();
            Post post = new Post();
            post.setFolder(folder);
            post.setDiscussion(discussion);
            post.setSharedBy(sharedBy);
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.OK).body(post);
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getStackTrace()[0].toString());
            return ResponseEntity.badRequest().build();
        }
    }
}
