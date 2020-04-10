package com.vsii.training.controller;

import com.vsii.training.message.request.SignUpForm;
import com.vsii.training.message.request.UserForm;
import com.vsii.training.message.response.ResponseMessage;
import com.vsii.training.model.User;
import com.vsii.training.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class UserRestAPI {

    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getListAllUser(){
        List<User> users = (List<User>) userService.findAll();

        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        userService.save(user);

        return new ResponseEntity<>(new ResponseMessage("User created successfully!"), HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserForm userForm, @PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if(user == null) {
            return new ResponseEntity<>("Can't Find User By Id" + id, HttpStatus.BAD_REQUEST);
        }

        try {
            user.get().setName(userForm.getName());

            userService.save(user.get());

            return new ResponseEntity<>(new ResponseMessage("Update successfully"), HttpStatus.OK);
        } catch (Exception e ) {
            throw new RuntimeException("Fail!");
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id ){
        Optional<User> user = userService.findById(id);

        if(!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);

        return new ResponseEntity<>(new ResponseMessage("Delete successfully"), HttpStatus.OK);
    }
}
