package com.airtel.crud.corsconfig;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashedGen {

    public static void main(String[] args) {
        String rawPassword = "1234"; // Replace with the actual password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
