package com.bitlegion.server.uploads;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Document, Integer> {
    public Optional<Document> findByStorageID(Long storageID);

    public List<Document> findAllByDateOfUpload(Date dateOfUpload);

    public List<Document> findByFolder(Folder folder);
}
