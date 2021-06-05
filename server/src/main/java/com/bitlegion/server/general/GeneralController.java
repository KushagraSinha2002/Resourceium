package com.bitlegion.server.general;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.accounts.AccountRepository;
import com.bitlegion.server.accounts.TokenChecker;
import com.bitlegion.server.uploads.Document;
import com.bitlegion.server.uploads.DocumentRepository;
import com.bitlegion.server.uploads.FolderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/core")
@CrossOrigin
public class GeneralController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private TokenChecker tokenChecker;

    @Autowired
    private Sleeper sleeper;

    @GetMapping(path = "/stats/app")
    public @ResponseBody HashMap<String, Long> getAppStats() {
        HashMap<String, Long> map = new HashMap<>();
        // 500 MB
        map.put("size", (long) 524288000); // bytes
        map.put("files", (long) 200);
        map.put("discussions", (long) 40);
        map.put("folders", (long) 20);
        return map;
    }

    @GetMapping(path = "/stats")
    public @ResponseBody HashMap<String, Long> getStats() {
        HashMap<String, Long> map = new HashMap<>();
        map.put("accountsCount", accountRepository.count());
        map.put("filesCount", documentRepository.count());
        map.put("foldersCount", folderRepository.count());
        return map;
    }

    @GetMapping(path = "/stats/dashboard")
    public @ResponseBody ResponseEntity<HashMap<String, Long>> getDashboardStats(HttpServletRequest request) {
        try {
            Account account = tokenChecker.checkAndReturnTokenOrRaiseException(request).getAccount();
            HashMap<String, Long> map = new HashMap<>();
            List<Document> documents = documentRepository.findAllByFolderAccount(account);
            double size = documents.stream().mapToDouble(o -> o.getSize()).sum();
            map.put("size", (long) size);
            map.put("files", (long) documents.size());
            map.put("discussions", (long) account.getDiscussions().size());
            map.put("folders", (long) folderRepository.findAllByAccount(account).size());
            sleeper.pause(1500);
            return ResponseEntity.ok().body(map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // This controller will be used to obtain exactly the number of files uploaded
    // for the last 7 days (including the current day). If there are less than 7
    // days worth of data in the database, the corresponding entry must be 0. The
    // data MUST be returned in the following format:

    // {
    // '2021-04-2': <number of documents uploaded that day>,
    // '2021-04-2': <number of documents uploaded that day>,
    // '2021-04-2': <number of documents uploaded that day>,
    // }
    @GetMapping(path = "/dashboard/graph/1")
    public @ResponseBody ResponseEntity<LinkedHashMap<String, Long>> dashboardGraphOne() {
        LinkedHashMap<String, Long> map = new LinkedHashMap<>();
        ZonedDateTime now = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
        for (int i = 0; i <= 6; i++) {
            Date date = Date.from(now.toLocalDate().minusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            String str = simpleDateFormat.format(date);
            map.put(str, (long) documentRepository.findAllByDateOfUpload(date).size());
        }
        return ResponseEntity.ok().body(map);
    }

    // This controller will be used to obtain the at-most top 5 discussions (or
    // could be less) that the user has participated in. The data MUST be
    // returned in the following format:

    // {
    // 'discussion_name': <number_of_posts_for_that_discussion>,
    // 'discussion_name_2': <number_of_posts_for_that_discussion>,
    // 'discussion_name_3': <number_of_posts_for_that_discussion>,
    // }

    // The data must also be sorted according to the values. The associated sorter
    // is already implemented and result stored in `sortedMap`.
    // TODO: COMPLETE IMPLEMENTATION
    @GetMapping(path = "/dashboard/graph/2")
    public @ResponseBody ResponseEntity<LinkedHashMap<String, Long>> dashboardGraphTwo(HttpServletRequest request) {
        try {
            // use this variable to perform database operations
            Account account = tokenChecker.checkAndReturnTokenOrRaiseException(request).getAccount();
            HashMap<String, Long> map = new HashMap<>();

            // map.put("discussion1", (long) 34);
            // map.put("discussion2", (long) 45);
            // map.put("discussion3", (long) 56);
            // map.put("discussion4", (long) 16);
            // map.put("discussion5", (long) 90);
            LinkedHashMap<String, Long> sortedMap = sortMap(map);
            return ResponseEntity.ok().body(sortedMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // This controller will be used to obtain at-most the 5 most used tags of the
    // current user . The data MUST be returned in the following format:

    // {
    // '<color of tag>': <number of folders on which this tag was used>,
    // '<color of tag>': <number of folders on which this tag was used>,
    // '<color of tag>': <number of folders on which this tag was used>,
    // }

    // The data must also be sorted according to the values.
    // TODO: COMPLETE IMPLEMENTATION
    @GetMapping(path = "/dashboard/graph/3")
    public @ResponseBody ResponseEntity<LinkedHashMap<String, Long>> dashboardGraphThree(HttpServletRequest request) {
        try {
            // use this variable to perform database operations
            Account account = tokenChecker.checkAndReturnTokenOrRaiseException(request).getAccount();
            HashMap<String, Long> map = new HashMap<>();
            map.put("orange", (long) 34);
            map.put("red", (long) 45);
            map.put("physics", (long) 56);
            map.put("workout", (long) 16);
            LinkedHashMap<String, Long> sortedMap = sortMap(map);
            return ResponseEntity.ok().body(sortedMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // This controller will be used to obtain at-most the top 10 user. The more
    // documents a user has uploaded, the lower is their rank. The data MUST be
    // returned in the following format:

    // {
    // 'username_1': <total number of documents uploaded>,
    // 'username_2': <total number of documents uploaded>,
    // 'username_3': <total number of documents uploaded>,
    // }
    // TODO: COMPLETE IMPLEMENTATION
    @GetMapping(path = "/dashboard/graph/4")
    public @ResponseBody ResponseEntity<LinkedHashMap<String, Long>> dashboardGraphFour() {
        HashMap<String, Long> map = new HashMap<>();
        // this is the format in which the date is returned in
        map.put("username1", (long) 20);
        map.put("username2", (long) 0);
        map.put("username3", (long) 76);
        map.put("username4", (long) 90);
        map.put("username5", (long) 25);
        map.put("username6", (long) 12);
        map.put("username7", (long) 56);
        LinkedHashMap<String, Long> sortedMap = sortMap(map);
        return ResponseEntity.ok().body(sortedMap);
    }

    private LinkedHashMap<String, Long> sortMap(HashMap<String, Long> unsorted) {
        return unsorted.entrySet().stream().sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
