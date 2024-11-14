package com.springboot.backend.review.userapp.usersBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.review.userapp.usersBackend.entities.User;
import com.springboot.backend.review.userapp.usersBackend.models.UserRequest;

public interface UserService {

    List<User> findByAll();
    
    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> update (UserRequest user, Long id);

    Long deleteById(Long id);


}
