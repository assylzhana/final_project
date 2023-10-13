package com.bitlab.final_project.config;


import com.bitlab.final_project.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();// BCrypt or SHA-1
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService())
                .passwordEncoder(passwordEncoder());

        http.csrf(AbstractHttpConfigurer::disable);

        http.formLogin(flc -> flc.loginProcessingUrl("/login")
                .usernameParameter("user_email")
                .passwordParameter("user_password")
                .loginPage("/login")
                .defaultSuccessUrl("/panel")
                .failureUrl("/login?error"));

        http.logout(logout -> logout.logoutUrl("/sign-out")
                .logoutSuccessUrl("/"));

        return http.build();
    }
}
