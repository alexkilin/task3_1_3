package com.example.demo.controller;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;


@Controller
public class UserController {

    @Autowired
    public UserService service;

    @GetMapping(value = "/user")
    public String userInfo(Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = service.getUserById(1L);
        model.addAttribute("user", currentUser);

        Set<Role> roles = currentUser.getRoles();
        StringBuilder rolesDTO = new StringBuilder();
        for (Role role :roles
        ) {
            rolesDTO.append(role.getRole().substring(5) + " ");
        }
        model.addAttribute("rolesDTO", rolesDTO.toString());

        return "users/userInfo";
    }
}