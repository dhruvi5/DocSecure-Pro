//package com.airtel.crud.corsconfig;//package com.airtel.crud.corsconfig;
//
//import com.airtel.crud.security.filter.UserAuthenticationProcessingFilter;
//import com.airtel.crud.security.provider.UserAuthenticationProvider;
//import com.airtel.crud.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.util.Collections;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private UserAuthenticationProvider authenticationProvider;
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder bean
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        UserAuthenticationProcessingFilter authenticationFilter = new UserAuthenticationProcessingFilter(authenticationManagerBean());
//        http
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(basic -> basic.disable())
//                .formLogin(form -> form.disable())
//                .authorizeRequests().requestMatchers("/api/login").permitAll() // Example: allow public endpoints
//                .dispatcherTypeMatchers(HttpMethod.valueOf("/api/emp_2")).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return new ProviderManager(Collections.singletonList(authenticationProvider));
//    }
//
//    @Bean
//    public CustomUserDetailsService userDetailsService() {
//        return customUserDetailsService;
//    }
//}
package com.airtel.crud.corsconfig;

import com.airtel.crud.Cache.RequestCachingFilter;
import com.airtel.crud.security.filter.UserAuthenticationProcessingFilter;
import com.airtel.crud.security.provider.UserAuthenticationProvider;
import com.airtel.crud.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserAuthenticationProvider authenticationProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder bean

    @Autowired
    private RequestCachingFilter requestCachingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        UserAuthenticationProcessingFilter authenticationFilter = new UserAuthenticationProcessingFilter(authenticationManagerBean());
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                .authorizeRequests()
                .requestMatchers("/api/login").permitAll() // Example: allow public endpoints
                .dispatcherTypeMatchers(HttpMethod.valueOf("/api/emp_2")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(requestCachingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public CustomUserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
