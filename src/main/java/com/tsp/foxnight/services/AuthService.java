package com.tsp.foxnight.services;

import com.tsp.foxnight.auth.UserDetailsService;
import com.tsp.foxnight.dto.AuthDTO;
import com.tsp.foxnight.entity.UserRole;
import com.tsp.foxnight.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final String secretKey = "vQ6E4w9X2fQ+s4vj1ldYfEdxUUb4e8RoYjKovXBfZmE=";

    public Map<String, String> login(AuthDTO body, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                body.getLogin(), body.getPassword(), Collections.emptyList());
        Authentication authenticate = authenticationProvider.authenticate(token);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);

        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

        String accessToken = generateToken(authenticate.getName(), 172800000L); // 2 days
        String refreshToken = generateToken(authenticate.getName(), 86400000L); // 1 day

        Map<String, String> tokens = new HashMap<>(){
            {
                put("accessToken", accessToken);
                put("refreshToken", refreshToken);
            }
        };
        return tokens;
    }

    public Map<String, Object> refreshToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalStateException("No session found, please log in first.");
        }

        SecurityContext context = (SecurityContext) session.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        if (context == null || context.getAuthentication() == null) {
            throw new IllegalStateException("No authentication found, please log in first.");
        }

        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) context.getAuthentication();

        UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(
                currentAuth.getPrincipal(), currentAuth.getCredentials(), currentAuth.getAuthorities());

        Authentication authenticate = authenticationProvider.authenticate(newToken);

        context.setAuthentication(authenticate);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

        String accessToken = generateToken(authenticate.getName(), 172800000L); // 2 days
        String refreshToken = generateToken(authenticate.getName(), 86400000L); // 1 day
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        UserRole role = userRepository.findByLogin(login).getRole();

        Map<String, Object> tokens = new HashMap<>(){
            {
                put("accessToken", accessToken);
                put("refreshToken", refreshToken);
                put("role", role.name().toLowerCase());
                put("id", userDetailsService.getIdNow());
            }
        };
        return tokens;
    }

    public Boolean logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return true;
    }

    private String generateToken(String subject, long expirationTime) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
