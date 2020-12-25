package com.example.demo.controller;

import com.example.demo.dto.ServiceResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    // как используется?????
    HttpHeaders headers = new HttpHeaders();
    if (user.getPassword() != "") {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    service.addUser(user);
    ServiceResponse<User> response = new ServiceResponse<User>("success", user);
    return new ResponseEntity<Object>(response, headers, HttpStatus.OK);

}

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> updateUser(@RequestBody User user) {

        // как используется?????
        HttpHeaders headers = new HttpHeaders();

        if (user.getPassword() != "") {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(service.getUserById(user.getId()).getPassword());
        }
        service.updateUser(user);
        User updatedUser = service.getUserById(user.getId());

        ServiceResponse<User> response = new ServiceResponse<User>("success", updatedUser);
        return new ResponseEntity<Object>(response, headers, HttpStatus.OK);
    }

    @GetMapping (value = "{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
              User user = service.getUserById(id);
              System.out.println(user);
              return ResponseEntity.ok(user);
        }


    @GetMapping (value = "/getCurrentUser")
    public ResponseEntity<User> getCurrentUser() {
        User currentUser = service.getUserByUserName (((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername());
        return ResponseEntity.ok(currentUser);
    }


	@RequestMapping (value = "/getUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> getAll() {
        List<User> users = service.getAllUsers();
        ServiceResponse<List<User>> response = new ServiceResponse<>("success", users);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        service.deleteUserById(id);
        List<User> users = service.getAllUsers();
        ServiceResponse<List<User>> response = new ServiceResponse<>("success", users);
        return new ResponseEntity<Object>(response, HttpStatus.OK);

    }


}
