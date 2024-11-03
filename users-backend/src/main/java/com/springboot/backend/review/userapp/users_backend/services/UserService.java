package com.springboot.backend.review.userapp.users_backend.services;

import java.util.List;
import java.util.Optional;

import com.springboot.backend.review.userapp.users_backend.entities.User;

public interface UserService {

    List<User> findByAll();

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);


}