package com.airtel.crud.security.converter;

    import com.airtel.crud.Cache.RequestCacheService;
    import com.airtel.crud.security.token.UserToken;
    import com.airtel.crud.service.UserService;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import jakarta.servlet.http.HttpServletRequest;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.web.authentication.AuthenticationConverter;

    import java.io.IOException;
    import java.util.Map;


public class UserAuthenticationConverter implements AuthenticationConverter {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationConverter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService userService;
    @Autowired
    private RequestCacheService requestCacheService;

//    @Override
//    public Authentication convert(HttpServletRequest request) {
//        logger.info("Convert method invoked for request: {}", request.getRequestURI());
//        String cachedBody = null;
//        logger.info("Cached request body: {}", cachedBody);
//
//        cachedBody = requestCacheService.getCachedRequestBody(request);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, String> credentials = objectMapper.readValue(cachedBody, Map.class);
//            String username = credentials.get("username");
//            String password = credentials.get("password");
//            logger.info("Extracted credentials - Username: {}, Password: {}", username, password);
//
//            return new UserToken(username, password);
//
//        } catch (IOException e) {
////            e.printStackTrace();
//            logger.error("Failed to parse cached request body", e);
//
//            return null; // Handle error gracefully
//        }
//    }

    @Autowired
    public UserAuthenticationConverter() {
        this.requestCacheService = requestCacheService;
    }
    @Override
    public Authentication convert(HttpServletRequest request) {
        logger.info("Convert method invoked for request: {}", request.getRequestURI());

        String cachedBody = requestCacheService.getCachedRequestBody(request);
        if (requestCacheService == null) {
            logger.warn("No cached request body found for URI: {}", request.getRequestURI());
            return null;
        }

        logger.info("Cached request body: {}", cachedBody);

        try {
            Map<String, String> credentials = objectMapper.readValue(cachedBody, Map.class);
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
