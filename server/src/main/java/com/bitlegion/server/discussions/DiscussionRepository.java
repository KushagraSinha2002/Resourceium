package com.bitlegion.server.discussions;

import com.bitlegion.server.accounts.Account;

import org.springframework.data.repository.CrudRepository;

public interface DiscussionRepository extends CrudRepository<Account, Integer> {

}
