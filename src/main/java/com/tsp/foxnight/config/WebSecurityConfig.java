package com.tsp.foxnight.config;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.apache.commons.lang3.CharEncoding.UTF_8;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // ...
        http.cors().and().csrf().disable();
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.setCharacterEncoding(UTF_8);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().println("""
                    {
                        "result": false,
                        "code": 401,
                        "message": "Необходимо авторизоваться"
                    }
                    """);
        });
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/auth", "/swagger-ui/*",
                                "/api-docs", "/api-docs/custom", "/api-docs/swagger-config", "/version").permitAll()
                        .anyRequest().authenticated()
                );//.formLogin(Customizer.withDefaults());
//                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/auth", "/version");
    }
}
