package com.airtel.crud.rest;

import com.airtel.crud.dto.CustomUser;
import com.airtel.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public CustomUser addUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        return userService.saveUser(username, password, role);
    }
}
