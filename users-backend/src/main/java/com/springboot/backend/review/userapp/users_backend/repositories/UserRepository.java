package com.springboot.backend.review.userapp.users_backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.backend.review.userapp.users_backend.entities.User;
import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    Page<User> findAll(Pageable pageable);

  

}
