package com.tsp.foxnight.config;

import com.tsp.foxnight.auth.AuthEntryPoint;
import com.tsp.foxnight.auth.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import java.time.Duration;

import static org.springframework.session.jdbc.JdbcIndexedSessionRepository.DEFAULT_TABLE_NAME;

@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPoint authenticationEntryPoint;

    private final Integer maxSessionsCount = 5;

    private final Duration maxInactiveInterval = Duration.ofMinutes(480);

    @Override
    @SneakyThrows
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "app/swagger-ui.html",
                "/app/swagger-ui.html",
                "/webjars/**",
                "/actuator/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/login",
                        "/auth/login",
                        "/logout",
                        "/auth/logout",
                        "/nginx/auth-upload/byte",
                        "/nginx/auth-download",
                        "/ched/auth-download",
                        "/gochs/monitoring/auth-protocol",
                        "/gochs/ip-address/createIpAddressLog").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(maxSessionsCount)
                .maxSessionsPreventsLogin(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userDetailsService.setPasswordEncoder(bCryptPasswordEncoder);
        return bCryptPasswordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionConfig sessionConfig(JdbcIndexedSessionRepository repository) {
        repository.setDefaultMaxInactiveInterval(
                Math.toIntExact(maxInactiveInterval.getSeconds()));
        repository.setTableName("go_chs.".concat(DEFAULT_TABLE_NAME));
        return new SessionConfig(repository, new SessionRegistryImpl(), maxSessionsCount);
    }
}

