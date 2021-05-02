package com.bitlegion.server.socials;

import com.bitlegion.server.accounts.Account;

import org.springframework.data.repository.CrudRepository;

public interface FollowRepository extends CrudRepository<Account, Integer> {

}