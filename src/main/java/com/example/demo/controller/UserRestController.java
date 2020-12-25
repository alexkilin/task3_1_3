package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/user")
@RestController
public class UserRestController {

    public final UserService service;
    public final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/getCurrentUser")
    public ResponseEntity<User> getCurrentUser() {

        User currentUser = service.getUserByUserName(((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername());

        return ResponseEntity.ok(currentUser);
    }

}






