package com.bitlegion.server.uploads;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface FileRepository extends CrudRepository<Document, Integer> {
    @Query("SELECT a FROM Document a where a.url = :url")
    public Optional<Document> findBySlug(@Param("url") String url);

    // @Query("SELECT a FROM Document a where a.url = :url AND a.account.id =
    // :userId")
    // public Optional<Document> findBySlugAndUserID(@Param("url") String url,
    // @Param("userId") Integer userId);
}
