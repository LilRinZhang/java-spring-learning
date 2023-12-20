package com.example.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/maintenance/product/**").hasRole("MANAGER")
                .requestMatchers("/maintenance/product/**").hasAnyRole("MANAGER", "EMPLOYEE")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?failure")
                .defaultSuccessUrl("/maintenance/product/display-list");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails lilrin = User.builder().username("lilrin").password("{noop}123456").roles("MANAGER").build();
        UserDetails employee = User.builder().username("employee").password("{noop}123456").roles("EMPLOYEE").build();
        return new InMemoryUserDetailsManager(lilrin, employee);
    }
}
