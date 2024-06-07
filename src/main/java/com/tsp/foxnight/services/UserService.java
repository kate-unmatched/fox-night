package com.tsp.foxnight.services;

import com.tsp.foxnight.auth.UserDetailsService;
import com.tsp.foxnight.dto.UserDTO;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.tsp.foxnight.utils.Errors.E289;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    public User createUser(UserDTO user) {
        E289.thr(userRepository.findByLoginEqualsIgnoreCase(user.getLogin()).isEmpty(), user.getLogin());

        String cleanPassword = generateRandomString();

        User newUser = new User()
                .setName(user.getName())
                .setLogin(user.getLogin())
                .setEmail(user.getEmail())
                .setTelegram(user.getTelegram())
                .setBirthday(user.getBirthday())
                .setStartWork(user.getStartWork())
                .setCity(user.getCity())
                .setPhoneNumber(user.getPhoneNumber())
                .setPhoto(user.getPhoto())
                .setIsActive(true)
                .setRole(user.getRole())
                .setPassword(userDetailsService.getEncryptedPassword(cleanPassword));

        return userRepository.save(newUser);
    }

    public static String generateRandomString() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom RANDOM = new SecureRandom();

        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
