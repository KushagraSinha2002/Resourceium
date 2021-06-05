package com.bitlegion.server.uploads;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {

     public Collection<Tag> findByNameAndColour(String name, String colour);
}
