package com.bitlegion.server.socials;

import java.util.Collection;
import java.util.Optional;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.uploads.Folder;

import org.springframework.data.repository.CrudRepository;

public interface FavoriteRepository extends CrudRepository<Favorite, Integer> {

    public Optional<Favorite> findByFolderAndAccount(Folder folder, Account account);

    public Integer countByFolder(Folder folder);

}
