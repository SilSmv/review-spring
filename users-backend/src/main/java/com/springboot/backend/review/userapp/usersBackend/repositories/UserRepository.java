package com.springboot.backend.review.userapp.usersBackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.backend.review.userapp.usersBackend.entities.User;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    Page<User> findAll(Pageable pageable);
    


    Optional<User> findByUsername(String name);
  

}
