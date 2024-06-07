package com.tsp.foxnight.auth;

import com.google.common.base.Preconditions;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.tsp.foxnight.utils.TextConstant.*;


@Service
@RequiredArgsConstructor
public class UserDetailsService extends AbstractUserDetailsAuthenticationProvider implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        System.err.println(ADDITIONAL_AUTHENTICATION_CHECKS);
    }

    @Override
    @Transactional
    public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails userDetails = loadUserByUsername(username);
        String password = String.valueOf(authentication.getCredentials());
        boolean matched = passwordEncoder.matches(password, userDetails.getPassword());
        Preconditions.checkState(matched, INCORRECT_PASSWORD);
        return userDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = getActiveUser(login);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), Collections.emptyList());
    }

    public User getActiveUser(String login) {
        User user = findByLogin(login);
        Preconditions.checkState(BooleanUtils.toBoolean(user.getIsActive()), ACCOUNT_IS_NOT_ACTIVE);
        return user;
    }

    public User findByLogin(String login) {
        return userRepository.findByLoginEqualsIgnoreCase(login)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_DEFINED));
    }

    public String getEncryptedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
