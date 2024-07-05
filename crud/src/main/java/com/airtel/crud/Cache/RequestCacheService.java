package com.airtel.crud.Cache;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestCacheService {
    private final Map<String, String> requestCache = new ConcurrentHashMap<>();

    public void cacheRequestBody(HttpServletRequest request, String body) {
        String key = request.getRequestURI(); // Use URI as cache key (can be customized)
        requestCache.put(key, body);
    }

    public String getCachedRequestBody(HttpServletRequest request) {
        String key = request.getRequestURI(); // Use URI as cache key (must match cache key used for storing)
        return requestCache.getOrDefault(key, "");
    }
}
