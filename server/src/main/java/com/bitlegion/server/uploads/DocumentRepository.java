package com.bitlegion.server.uploads;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface DocumentRepository extends CrudRepository<Document, Integer> {
    public Optional<Document> findByStorageID(Long storageID);

    //@Query("SELECT * FROM DOCUMENT a where a.dateOfUpload = :dateOfUpload")
    //public Optional<Document> findbyDateofupload(@Param("dateOfUpload") ArrayList dateOfUpload);

    public Optional<Document> findbyDateofUpload(Long dateOfUpload);

    // @Query("SELECT a FROM Document a where a.storageID = :storageID AND
    // a.account.id =
    // :userId")
    // public Optional<Document> findBySlugAndUserID(@Param("storageID") String
    // storageID,
    // @Param("userId") Integer userId);
}
