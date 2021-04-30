package com.bitlegion.server.accounts;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AccountRepository extends CrudRepository<Account, Integer> {
    @Query("SELECT a FROM Account a where a.email = :email")
    public Optional<Account> findByEmail(@Param("email") String email);

    @Query("SELECT a FROM Account a where a.username = :username")
    public Optional<Account> findByUsername(@Param("username") String username);

}
