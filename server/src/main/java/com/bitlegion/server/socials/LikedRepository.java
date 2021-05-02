package com.bitlegion.server.socials;

import com.bitlegion.server.accounts.Account;

import org.springframework.data.repository.CrudRepository;

public interface LikedRepository extends CrudRepository<Account, Integer> {

}
