package com.bitlegion.server.uploads;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface FileRepository extends CrudRepository<Document, Integer> {
    @Query("SELECT a FROM Document a where a.slug = :slug")
    public Optional<Document> findBySlug(@Param("slug") String slug);

    @Query("SELECT a FROM Document a where a.slug = :slug AND a.account.id = :userId")
    public Optional<Document> findBySlugAndUserID(@Param("slug") String slug, @Param("userId") Integer userId);
}
