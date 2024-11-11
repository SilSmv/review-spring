package com.springboot.backend.review.userapp.usersBackend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.review.userapp.usersBackend.entities.Role;
import com.springboot.backend.review.userapp.usersBackend.entities.User;
import com.springboot.backend.review.userapp.usersBackend.models.IUser;
import com.springboot.backend.review.userapp.usersBackend.models.UserRequest;
import com.springboot.backend.review.userapp.usersBackend.repositories.RoleRepository;
import com.springboot.backend.review.userapp.usersBackend.repositories.UserRepository;

@Service
public class UserServiceImpl  implements UserService{

    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

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
    public Page<User> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(@NonNull Long id) {
        return this.repository.findById(id);

    }

    @Override
    @Transactional
    public User save(User user) {
        user.setRoles(getRoles(user));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  this.repository.save(user);

    }
    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {
        Optional<User> userOptional = this.repository.findById(id);
        if(userOptional.isPresent()){
            User userBd = userOptional.get();
            userBd.setEmail(user.getEmail());
            userBd.setLastname(user.getLastname());
            userBd.setName(user.getName());
            userBd.setUsername(user.getUsername());
            userBd.setPassword(userBd.getPassword());

            List<Role> roles = getRoles(user);
            userBd.setRoles(roles);
            return Optional.of(repository.save(userBd));

            
        }
        return Optional.empty();
   
        
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.repository.deleteById(id);

    }

    private List<Role> getRoles(IUser user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
   
        
        optionalRoleUser.ifPresent(roles :: add);
   
        if(user.isAdmin()){
            Optional<Role> optionalRoleUserAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleUserAdmin.ifPresent(roles :: add);
   
        }
        return roles;
    }






}
