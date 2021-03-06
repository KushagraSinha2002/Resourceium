package com.bitlegion.server.uploads;

import java.util.Collection;
import java.util.List;

import com.bitlegion.server.accounts.Account;

import org.springframework.data.repository.CrudRepository;

public interface FolderRepository extends CrudRepository<Folder, Integer> {
    public Collection<Folder> findAllByAccount(Account account);

    public Integer countByAccount(Account account);

    public Iterable<Folder> findByAccount(Account account);

    public List<Folder> findByAccountAndTitle(Account account, String title);

    public Iterable<Folder> findAllByFavoritesAccount(Account account);

    public List<Folder> findByAccountOrderByLastEditedDesc(Account account);

    public Iterable<Folder> findAllByTagsNameContainingIgnoreCase(String name);

}
