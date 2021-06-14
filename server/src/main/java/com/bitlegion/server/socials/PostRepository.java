package com.bitlegion.server.socials;

import java.util.Optional;

import com.bitlegion.server.discussions.Discussion;
import com.bitlegion.server.uploads.Folder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

    public Integer countByFolder(Folder folder);

    public Page<Post> findAllByDiscussion(Discussion discussion, Pageable pageable);

    public Page<Post> findAllByOrderByCreationTimeAsc(Pageable pageable);

    public Optional<Post> findFirstByDiscussionOrderByCreationTimeDesc(Discussion discussion);

}
