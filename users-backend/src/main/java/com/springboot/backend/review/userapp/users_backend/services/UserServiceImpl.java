package com.springboot.backend.review.userapp.users_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.review.userapp.users_backend.entities.User;
import com.springboot.backend.review.userapp.users_backend.repositories.UserRepository;

@Service
public class UserServiceImpl  implements UserService{

    private UserRepository repository;
    

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByAll() {
        return (List) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(@NonNull Long id) {
        return this.findById(id);

    }

    @Override
    @Transactional
    public User save(User user) {
        return  this.repository.save(user);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.repository.deleteById(id);

    }


}