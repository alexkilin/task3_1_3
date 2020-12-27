package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminRestController {

	public final UserService service;
    public final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminRestController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

@RequestMapping(value = "/saveUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public ResponseEntity<Object> createUser(@RequestBody User user) {

    if (user.getPassword() != "") {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    service.addUser(user);
    return ResponseEntity.ok(user);

}

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        if (user.getPassword() != "") {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(service.getUserById(user.getId()).getPassword());
        }
        service.updateUser(user);
        User updatedUser = service.getUserById(user.getId());

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping (value = "{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
              User user = service.getUserById(id);
              return ResponseEntity.ok(user);
        }


    @GetMapping (value = "/getCurrentUser")
    public ResponseEntity<User> getCurrentUser() {
        User currentUser = service.getUserByUserName (((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername());
        return ResponseEntity.ok(currentUser);
    }


	@RequestMapping (value = "/getUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <List<User>> getAll() {
        List<User> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <List<User>> deleteUser(@PathVariable("id") Long id) {
        service.deleteUserById(id);
        List<User> users = service.getAllUsers();
        return ResponseEntity.ok (users);

    }


}
