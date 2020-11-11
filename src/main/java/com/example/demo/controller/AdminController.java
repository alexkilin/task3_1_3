package com.example.demo.controller;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public final UserService service;

    public final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }


    private void setNewRoles (User user, String role){
        Set<Role> roles = new HashSet<>();
        switch (role) {
            case "ROLE_USER": {
                roles.add(new Role(2L, role));
                break;
            }
            case "ROLE_ADMIN": {
                roles.add(new Role(1L, role));
                break;
            }
            case "ROLE_USER_ADMIN": {
                roles.add(new Role(1L, "ROLE_ADMIN"));
                roles.add(new Role(2L, "ROLE_USER"));
                break;
            }
        }
        user.setRoles(roles);
    }

    @GetMapping(value = "/users")
    public String getAll(Model model) {
        List<User> users = service.getAllUsers();

        User currentUser = service.getUserByUserName (((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername());

        Set<Role> roles = currentUser.getRoles();
        StringBuilder rolesDTO = new StringBuilder();
        for (Role role :roles
             ) {
              rolesDTO.append(role.getRole().substring(5) + " ");
        }
        User user = new User();
        model.addAttribute("currentuser", currentUser);
        model.addAttribute("rolesDTO", rolesDTO.toString());
        model.addAttribute("listOfUsers", users);
        model.addAttribute("user", user);
        return "/users/home";
    }


    @PostMapping(value = "/createUser")
    public String createUser(User user, String role) {
        setNewRoles(user, role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUserById(id);
        return "redirect:/admin/users";
    }


    @PostMapping(value = "/update")
    public String updateUser(User user, String roleDTO, String newPassword) {
        setNewRoles(user, roleDTO);
        if (newPassword != "") {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            user.setPassword(service.getUserById(user.getId()).getPassword());
        }
        service.updateUser(user);
        return "redirect:/admin/users";
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "/users/login";
    }


    @GetMapping(value = "/user/{id}")
    public String userInfo(@PathVariable("id") Long id, Model model) {
        UserDetails user = service.getUserById(id);
        model.addAttribute("user", user);
        return "users/userInfo";
    }

}



