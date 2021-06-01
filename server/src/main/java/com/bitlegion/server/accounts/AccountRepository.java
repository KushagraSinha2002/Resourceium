package com.bitlegion.server.accounts;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    public Optional<Account> findByEmail(String email);

    public Optional<Account> findByUsername(String username);

}
