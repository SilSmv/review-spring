package com.springboot.backend.review.userapp.usersBackend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.review.userapp.usersBackend.entities.User;
import com.springboot.backend.review.userapp.usersBackend.models.UserRequest;
import com.springboot.backend.review.userapp.usersBackend.services.UserService;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;



@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/users")
public class UserController {
@Autowired
private UserService service;



    @GetMapping
    public List<User> list() {
        return this.service.findByAll();
    }
    @GetMapping("/page/{page}")
    public Page<User> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page,4);
        return this.service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<User> userOptional =  service.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        //404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "El usuario no se encontro por el id " + id));
    }
    

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.ok().body(this.service.save(user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user,BindingResult result,@PathVariable Long id) {
        if(result.hasErrors()){
            return validation(result);
        }

        Optional<User> userOptional = this.service.update(user, id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }


        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id ){
        Optional <User> userOptional = this.service.findById(id);
        if(userOptional.isPresent()){
            this.service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo "+ error.getField() + " "+ error.getDefaultMessage());
        } );
        return ResponseEntity.badRequest().body(errors);
    }
    
    
}
