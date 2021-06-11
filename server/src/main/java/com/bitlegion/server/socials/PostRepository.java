package com.bitlegion.server.socials;

import com.bitlegion.server.uploads.Folder;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {

    public Integer countByFolder(Folder folder);

}
