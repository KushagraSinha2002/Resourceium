package com.bitlegion.server.socials;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.Token;
import com.bitlegion.server.accounts.TokenChecker;
import com.bitlegion.server.uploads.Folder;
import com.bitlegion.server.uploads.FolderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/favorite")
@CrossOrigin
public class FavoriteController {

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    /* Get all the favorites of this folder */
    @GetMapping(path = "/{folderID}")
    public ResponseEntity<Integer> getFollowersCount(@PathVariable Integer folderID) {
        Optional<Folder> maybeFolder = folderRepository.findById(folderID);
        if (maybeFolder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Folder folder = maybeFolder.get();
        Integer favorites = favoriteRepository.countByFolder(folder);
        return ResponseEntity.status(HttpStatus.CREATED).body(favorites);
    }

    @PostMapping(path = "/toggle/{folderID}")
    public ResponseEntity<?> toggleFollow(HttpServletRequest request, @PathVariable Integer folderID) {
        try {
            Token token = tokenChecker.checkAndReturnTokenOrRaiseException(request);
            Optional<Folder> maybeFolder = folderRepository.findById(folderID);
            if (maybeFolder.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Folder folder = maybeFolder.get();
            Account account = token.getAccount();
            Optional<Favorite> maybeFavorite = favoriteRepository.findByFolderAndAccount(folder, account);
            if (maybeFavorite.isPresent()) {
                Favorite favorite = maybeFavorite.get();
                favoriteRepository.delete(favorite);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted successfully!");
            }
            Favorite favorite = new Favorite();
            favorite.setAccount(account);
            favorite.setFolder(folder);
            favoriteRepository.save(favorite);
            return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
