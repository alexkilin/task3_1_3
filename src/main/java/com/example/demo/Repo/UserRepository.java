package com.example.demo.Repo;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository  <User, Long> {
        User findByUsername(String userName);
}