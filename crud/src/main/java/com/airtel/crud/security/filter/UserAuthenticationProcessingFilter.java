package com.airtel.crud.security.filter;

import com.airtel.crud.security.converter.UserAuthenticationConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationConverter converter = new UserAuthenticationConverter();
private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProcessingFilter.class);

    public UserAuthenticationProcessingFilter(AuthenticationManager manager) {
        super(new AntPathRequestMatcher("/api/login"), manager);
        setAuthenticationManager(manager);

    }

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        Authentication authentication = converter.convert(request);
//        return getAuthenticationManager().authenticate(authentication);
//    }
@Override
public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {
    // Check if converter is initialized
    if (converter == null) {
        throw new AuthenticationServiceException("Converter is not initialized");
    }

    // Convert the request to an Authentication object
    Authentication authentication = converter.convert(request);

    if (authentication == null) {
        throw new AuthenticationServiceException("Authentication object returned by converter is null");
    }

    try {
        // Authenticate using AuthenticationManager
        return getAuthenticationManager().authenticate(authentication);
    } catch (AuthenticationException e) {
        // Handle authentication failure or rethrow to be handled by Spring Security
        throw e;
    }
}

//@Override
//public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//
//        throws AuthenticationException, IOException, ServletException {
//    String username = request.getParameter("username");
//    String password = request.getParameter("password");
//    logger.info("Attempting authentication with username: {}");
//    logger.debug("Password received: {}");
//    return getAuthenticationManager().authenticate(
//            new UsernamePasswordAuthenticationToken(username, password)
//    );
//}

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        String username = request.getParameter("username"); // Adjusted to get username from request parameter if available
        logger.error("Authentication failed for username: {}", username != null ? username : "Unknown", failed);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}