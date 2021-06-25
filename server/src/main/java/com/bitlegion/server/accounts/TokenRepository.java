package com.bitlegion.server.accounts;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends CrudRepository<Token, Integer> {

    // @Query("SELECT t FROM Token t WHERE t.string = :string")
    public Optional<Token> findByString(@Param("string") String string);

    public Optional<Token> findByAccount(@Param("account") Account account);
}
