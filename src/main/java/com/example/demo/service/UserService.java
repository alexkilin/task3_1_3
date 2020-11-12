package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;

public interface UserService {
    boolean addUser(com.example.demo.model.User user);
    void deleteUserById(Long id);
    List<com.example.demo.model.User> getAllUsers();
    void updateUser(com.example.demo.model.User user);
    User getUserByUserName(String username);
    User getUserById(Long id);
}
