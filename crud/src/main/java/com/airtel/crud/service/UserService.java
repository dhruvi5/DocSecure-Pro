package com.airtel.crud.service;

import com.airtel.crud.dto.CustomUser;
import com.airtel.crud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


    public CustomUser saveUser(String username, String rawPassword, String role) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        CustomUser user = new CustomUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role);
        return userRepo.save(user);
    }
}
