//package com.airtel.crud.security.filter;
//
//import com.airtel.crud.security.converter.UserAuthenticationConverter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProcessingFilter.class);
//    private final UserAuthenticationConverter authenticationConverter;
//
//    public UserAuthenticationProcessingFilter(AuthenticationManager authenticationManager, UserAuthenticationConverter authenticationConverter) {
//        super.setAuthenticationManager(authenticationManager);
//        this.authenticationConverter = authenticationConverter;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        logger.info("Attempting authentication with request: {}", request.getRequestURI());
//
//        Authentication authentication = authenticationConverter.convert(request);
//        if (authentication == null) {
//            throw new AuthenticationServiceException("Authentication object returned by converter is null");
//        }
//
//        return getAuthenticationManager().authenticate(authentication);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            jakarta.servlet.FilterChain chain, Authentication authResult)
//            throws java.io.IOException, jakarta.servlet.ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                              org.springframework.security.core.AuthenticationException failed)
//            throws java.io.IOException, jakarta.servlet.ServletException {
//        String username = request.getParameter("username");
//        logger.error("Authentication failed for username: {}", username != null ? username : "Unknown", failed);
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//    }
//}
package com.airtel.crud.security.filter;

import com.airtel.crud.security.converter.UserAuthenticationConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProcessingFilter.class);
    private final UserAuthenticationConverter authenticationConverter;

    public UserAuthenticationProcessingFilter(AuthenticationManager authenticationManager, UserAuthenticationConverter authenticationConverter) {
        super.setAuthenticationManager(authenticationManager);
        this.authenticationConverter = authenticationConverter;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            Authentication authentication = attemptAuthentication(request, response);
            if (authentication == null) {
                unsuccessfulAuthentication(request, response, new AuthenticationServiceException("Authentication object returned by converter is null"));
                return;
            }

            successfulAuthentication(request, response, chain, authentication);
        } catch (AuthenticationServiceException failed) {
            unsuccessfulAuthentication(request, response, failed);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Attempting authentication with request: {}", request.getRequestURI());

        Authentication authentication = authenticationConverter.convert(request);
        if (authentication == null) {
            throw new AuthenticationServiceException("Authentication object returned by converter is null");
        }

        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              org.springframework.security.core.AuthenticationException failed)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        logger.error("Authentication failed for username: {}", username != null ? username : "Unknown", failed);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
