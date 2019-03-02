package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

/**
 * 
 * @version $Id$
 */
public interface UsersRepository extends CrudRepository<User, Long> {

    // find by....
    User findByDni(String dni);

}
