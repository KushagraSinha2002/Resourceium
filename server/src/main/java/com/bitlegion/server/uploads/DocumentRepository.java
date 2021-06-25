package com.bitlegion.server.uploads;

import java.util.Date;
import java.util.Optional;

import com.bitlegion.server.accounts.Account;

import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document, Integer> {
    public Optional<Document> findByStorageID(Long storageID);

    public Iterable<Document> findAllByDateOfUploadBetween(Date start, Date end);

    public Iterable<Document> findAllByFolderAccount(Account account);

    public Iterable<Document> findByFolder(Folder folder);
}
