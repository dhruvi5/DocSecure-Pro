package com.airtel.crud.security.converter;

import com.airtel.crud.security.token.UserToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Component
public class UserAuthenticationConverter implements AuthenticationConverter {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationConverter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication convert(HttpServletRequest request) {
        logger.info("Convert method invoked for request: {}", request.getRequestURI());

        try (BufferedReader reader = request.getReader()) {
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            logger.info("Cached request body: {}", requestBody);

            Map<String, String> credentials = objectMapper.readValue(requestBody.toString(), Map.class);
            String username = credentials.get("username");
            String password = credentials.get("password");
            logger.info("Extracted credentials - Username: {}, Password: {}", username, password);

            return new UserToken(username, password);
        } catch (IOException e) {
            logger.error("Failed to parse cached request body", e);
            return null; // Handle error gracefully
        }
    }
}
