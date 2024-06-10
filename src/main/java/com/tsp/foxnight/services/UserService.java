package com.tsp.foxnight.services;

import com.tsp.foxnight.auth.UserDetailsService;
import com.tsp.foxnight.dto.UserBriefDTO;
import com.tsp.foxnight.dto.UserDTO;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static com.tsp.foxnight.utils.Errors.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> E612.thr(userId));
        E314.thr(user.getIsActive(), user.getLogin());
        return user;
    }
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

    public User updateUser(Long userId, UserBriefDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (userDTO.getName() != null) user.setName(userDTO.getName());
        if (userDTO.getBirthday() != null) user.setBirthday(userDTO.getBirthday());
        if (userDTO.getStartWork() != null) user.setStartWork(userDTO.getStartWork());
        if (userDTO.getTelegram() != null) user.setTelegram(userDTO.getTelegram());
        if (userDTO.getCity() != null) user.setCity(userDTO.getCity());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getPhoneNumber() != null) user.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getLogin() != null) user.setLogin(userDTO.getLogin());
        if (userDTO.getRole() != null) user.setRole(userDTO.getRole());
        if (userDTO.getPhoto() != null) user.setPhoto(userDTO.getPhoto());

        return userRepository.save(user);
    }

    public Boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> E612.thr(userId));
        user.setIsActive(false);
        userRepository.save(user);
        return true;
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
