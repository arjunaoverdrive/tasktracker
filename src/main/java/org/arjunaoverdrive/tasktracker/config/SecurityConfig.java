package org.arjunaoverdrive.tasktracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final static String [] OPEN_RESOURCES = {
            "/v3/api-docs/**",
            "/webjars/**",
            "/api/v1/users"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    private ServerHttpSecurity buildDefaultHttpSecurity(ServerHttpSecurity http){
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange((auth) -> auth.pathMatchers(OPEN_RESOURCES).permitAll())
                .authorizeExchange((auth) -> auth.anyExchange().authenticated())
                .httpBasic(Customizer.withDefaults());
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService,
                                                               PasswordEncoder passwordEncoder){
        var reactiveAuthenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);

        reactiveAuthenticationManager.setPasswordEncoder(passwordEncoder);
        return reactiveAuthenticationManager;
    }

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http, ReactiveAuthenticationManager authenticationManager){
        return buildDefaultHttpSecurity(http)
                .authenticationManager(authenticationManager)
                .build();
    }


}
