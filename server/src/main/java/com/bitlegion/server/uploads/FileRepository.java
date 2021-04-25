package com.bitlegion.server.uploads;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface FileRepository extends CrudRepository<File, Integer> {
    @Query("SELECT a FROM File a where a.slug = :slug")
    public Optional<File> findBySlug(@Param("slug") String slug);

    @Query("SELECT a FROM File a where a.slug = :slug AND a.account.id = :userId")
    public Optional<File> findBySlugAndUserID(@Param("slug") String slug, @Param("userId") Integer userId);
}
