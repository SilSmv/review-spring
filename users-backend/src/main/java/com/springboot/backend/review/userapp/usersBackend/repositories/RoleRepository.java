package com.springboot.backend.review.userapp.usersBackend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.review.userapp.usersBackend.entities.Role;

public interface RoleRepository extends CrudRepository <Role, Long>{
    Optional<Role> findByName(String name);

}
