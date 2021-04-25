package com.bitlegion.server.uploads;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface FileRepository extends CrudRepository<File, Integer> {

}
