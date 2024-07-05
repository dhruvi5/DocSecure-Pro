package com.airtel.crud.security.provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.airtel.crud.dto.CustomUser;
import com.airtel.crud.repo.UserRepo;
import com.airtel.crud.security.token.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);

    private final PasswordEncoder encoder;
    private final UserRepo repo;

    @Autowired
    public UserAuthenticationProvider(PasswordEncoder encoder, UserRepo repo) {
        this.encoder = encoder;
        this.repo = repo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        final Optional<CustomUser> user = repo.findById(Long.valueOf((String) authentication.getPrincipal()));

        String username = authentication.getPrincipal().toString();
        String password = (String) authentication.getCredentials();

        logger.debug("Authenticating user: {}", username);

        Optional<CustomUser> user = Optional.ofNullable(repo.findByUsername(username));
//        logger.debug("Attempting authentication for username: {}", user);
//        if (!user.isPresent()) {
//            logger.debug("User not found: {}", user);
//
//            return authentication;
//        }
//        if (!encoder.matches((String) authentication.getCredentials(), user.get().getPassword())) {
//            throw new BadCredentialsException("Invalid credentials");
//        }
//        authentication.setAuthenticated(Boolean.TRUE);
//        return authentication;
        if (!user.isPresent()) {
            logger.debug("User not found: {}", username);
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!encoder.matches((String) authentication.getCredentials(), user.get().getPassword())) {
            logger.debug("Invalid credentials for user: {}", username);
            throw new BadCredentialsException("Invalid username or password");
        }
//
        authentication.setAuthenticated(Boolean.TRUE);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserToken.class.isAssignableFrom(authentication);
    }
}
