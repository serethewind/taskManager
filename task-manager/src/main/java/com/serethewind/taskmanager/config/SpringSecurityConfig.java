package com.serethewind.taskmanager.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity //to activate method level security for role based authorization
@AllArgsConstructor
public class SpringSecurityConfig {
    //inject the userDetailsService Interface to access the loadByUser method.
    private UserDetailsService userDetailsService;

    //create a bean of the authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.POST, "/api/v1/tasks/users").permitAll();
//            authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
//            authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
//            authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
//            authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN", "USER");
//            authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll(); //to publicly give access.
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }
    //The below served for in-memory authentication. No longer needed, as we are making use of database authentication
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails noah = User.builder().username("noah").password(passwordEncoder().encode("password")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(noah, admin);
//    }
}
