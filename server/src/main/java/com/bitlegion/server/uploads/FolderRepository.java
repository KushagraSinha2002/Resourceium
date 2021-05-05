package com.bitlegion.server.uploads;

import java.util.Collection;

import com.bitlegion.server.accounts.Account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FolderRepository extends CrudRepository<Folder, Integer> {
    @Query("SELECT f FROM Folder f WHERE f.account = :account")
    public Collection<Folder> findByAccount(@Param("account") Account account);
}
