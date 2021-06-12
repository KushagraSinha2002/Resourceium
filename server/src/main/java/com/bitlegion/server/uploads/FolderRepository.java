package com.bitlegion.server.uploads;

import java.util.Collection;
import java.util.List;

import com.bitlegion.server.accounts.Account;

import org.springframework.data.repository.CrudRepository;

public interface FolderRepository extends CrudRepository<Folder, Integer> {
    public Collection<Folder> findAllByAccount(Account account);

    public Integer countByAccount(Account account);

    public Collection<Folder> findByAccount(Account account);

    public Collection<Folder> findByAccountAndTitle(Account account, String title);

    public List<Folder> findByAccountOrderByLastEditedDesc(Account account);
}
