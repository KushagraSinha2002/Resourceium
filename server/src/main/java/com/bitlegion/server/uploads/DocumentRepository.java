package com.bitlegion.server.uploads;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface DocumentRepository extends CrudRepository<Document, Integer> {
    public Optional<Document> findByStorageID(Long storageID);

    public Optional<Document> findByDateOfUpload(Long dateOfUpload);

}
