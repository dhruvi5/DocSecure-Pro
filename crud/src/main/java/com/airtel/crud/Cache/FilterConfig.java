//package com.airtel.crud.Cache;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.DelegatingFilterProxy;
//
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<DelegatingFilterProxy> requestCachingFilter() {
//        FilterRegistrationBean<DelegatingFilterProxy> registrationBean = new FilterRegistrationBean<>();
//        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("requestCachingFilter");
//        registrationBean.setFilter(delegatingFilterProxy);
//        registrationBean.addUrlPatterns("/api/login"); // Adjust URL pattern as needed
//
//        return registrationBean;
//    }
//}
