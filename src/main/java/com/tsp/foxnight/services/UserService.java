package com.tsp.foxnight.services;

import com.google.common.base.Objects;
import com.tsp.foxnight.auth.UserDetailsService;
import com.tsp.foxnight.dto.*;
import com.tsp.foxnight.entity.RestType;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.entity.UserRole;
import com.tsp.foxnight.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.tsp.foxnight.utils.Errors.*;

@Service

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final RestAuditService restAuditService;

    @SneakyThrows
    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> E612.thr(userId));
        E314.thr(user.getIsActive(), user.getLogin());
        restAuditService.saveAuditRequest(RestType.GET, Collections.singletonMap("userId", userId));
        return user;
    }
    @SneakyThrows
    public List<BirthdayDTO> getBirthdays(){
        List<User> users = userRepository.getBirthdayUsers();
        List<BirthdayDTO> birthdays = new ArrayList<>();
        users.forEach(x -> birthdays.add(new BirthdayDTO()
                .setId(x.getId())
                .setName(x.getName())
                .setBirthday(x.getBirthday())
                .setPhoto(x.getPhoto()))
        );
        restAuditService.saveAuditRequest(RestType.GET, Collections.singletonMap("birthday", "all"));
        return birthdays;
    }
    @SneakyThrows
    public List<UserAllDTO> getAllUsers() {
        List<UserAllDTO> userShort = new ArrayList<>();
        List<User> users = userRepository.findAll()
                .stream()
                .filter(x -> x.getIsActive() == Boolean.TRUE)
                .filter(x -> x.getRole() != UserRole.ADMIN)
                .sorted(Comparator.comparing(User::getName))
                .toList();
        users.forEach(x -> userShort.add(new UserAllDTO()
                .setId(x.getId())
                .setName(x.getName())
                .setPhoto(x.getPhoto()))
        );
        restAuditService.saveAuditRequest(RestType.GET, Collections.singletonMap("users", "all"));
        return userShort;
    }
    @SneakyThrows
    public User createUser(UserCreateDTO user, MultipartFile file) {
        E289.thr(userRepository.findByLoginEqualsIgnoreCase(user.getLogin()).isEmpty(), user.getLogin());
        E167.thr(!Objects.equal(userDetailsService.getRoleNow(), UserRole.EMPLOYEE));

        String cleanPassword = generateRandomString();
        user.setPassword(cleanPassword);

        User newUser = new User()
                .setName(user.getName())
                .setLogin(user.getLogin())
                .setEmail(user.getEmail())
                .setTelegram(user.getTelegram())
                .setBirthday(user.getBirthday())
                .setStartWork(user.getStartWork())
                .setCity(user.getCity())
                .setPhoneNumber(user.getPhoneNumber())
                .setIsActive(true)
                .setRole(user.getRole())
                .setPassword(userDetailsService.getEncryptedPassword(cleanPassword));

        if (file != null && !file.isEmpty()) {
            String fileName = "photo_" + user.getLogin() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("C:\\employee_pictures", fileName);
            Files.write(filePath, file.getBytes());
            newUser.setPhoto(filePath.toString());
        }

        restAuditService.saveAuditRequest(RestType.POST, user);

        return userRepository.save(newUser);
    }

    public User updateUser(Long userId, UserUpdateDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> E612.thr(userId));

        if (userDetailsService.getRoleNow().equals(UserRole.EMPLOYEE) ) {
            E456.thr(java.util.Objects.equals(userId, userDetailsService.getIdNow()));
            if (userDTO.getBirthday() != null) user.setBirthday(userDTO.getBirthday());
            if (userDTO.getTelegram() != null) user.setTelegram(userDTO.getTelegram());
            if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
            if (userDTO.getPhoneNumber() != null) user.setPhoneNumber(userDTO.getPhoneNumber());
            restAuditService.saveAuditRequest(RestType.PATCH, user);

            return userRepository.save(user);
        }

        if (userDTO.getName() != null) user.setName(userDTO.getName());
        if (userDTO.getBirthday() != null) user.setBirthday(userDTO.getBirthday());
        if (userDTO.getStartWork() != null) user.setStartWork(userDTO.getStartWork());
        if (userDTO.getTelegram() != null) user.setTelegram(userDTO.getTelegram());
        if (userDTO.getCity() != null) user.setCity(userDTO.getCity());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getPhoneNumber() != null) user.setPhoneNumber(userDTO.getPhoneNumber());

        restAuditService.saveAuditRequest(RestType.PATCH, user);

        return userRepository.save(user);
    }
    @SneakyThrows
    public User updateUser(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> E612.thr(userId));

        if (file != null && !file.isEmpty()) {
            String fileName = "photo_" + user.getLogin() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("C:\\employee_pictures", fileName);
            Files.write(filePath, file.getBytes());
            user.setPhoto(filePath.toString());
        }

        restAuditService.saveAuditRequest(RestType.PATCH, user);

        return userRepository.save(user);
    }
    @SneakyThrows
    public Boolean deleteUser(Long userId) {
        E167.thr(!Objects.equal(userDetailsService.getRoleNow(), UserRole.EMPLOYEE));
        User user = userRepository.findById(userId).orElseThrow(() -> E612.thr(userId));
        user.setIsActive(false);
        userRepository.save(user);
        restAuditService.saveAuditRequest(RestType.DELETE, Collections.singletonMap("userId", userId));
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
