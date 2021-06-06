package com.bitlegion.server.uploads;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {

     public Optional<Tag> findByName(String name);

     public Collection<Folder> findAllByFolders(Folder folder);
}
