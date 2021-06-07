package com.bitlegion.server.discussions;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface DiscussionRepository extends CrudRepository<Discussion, Integer> {

    public Optional<Discussion> findByNameIgnoreCase(String name);

    public Collection<Discussion> findAllByNameContainingIgnoreCase(String name);

}
