package com.example.demo.service;

import com.example.demo.Repo.UserRepository;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean addUser(User user) {
        userRepository.save(user);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    public void updateUser (User user) {
        userRepository.save(user);
    }

    public User getUserByUserName(String userName)
    {
        return userRepository.findByUsername(userName);
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}



