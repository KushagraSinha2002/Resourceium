package com.bitlegion.server.socials;

import java.util.List;

import com.bitlegion.server.discussions.Discussion;
import com.bitlegion.server.uploads.Folder;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

    public Integer countByFolder(Folder folder);

    public List<Post> findAllByDiscussion(Discussion discussion, Pageable pageable);

}
