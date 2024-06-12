package com.tsp.foxnight.services;

import com.google.common.base.Objects;
import com.tsp.foxnight.auth.UserDetailsService;
import com.tsp.foxnight.dto.BirthdayDTO;
import com.tsp.foxnight.dto.UserAllDTO;
import com.tsp.foxnight.dto.UserBriefDTO;
import com.tsp.foxnight.dto.UserDTO;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.entity.UserRole;
import com.tsp.foxnight.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<BirthdayDTO> getBirthdays(){
        List<User> users = userRepository.getBirthdayUsers();
        List<BirthdayDTO> birthdays = new ArrayList<>();
        users.forEach(x -> birthdays.add(new BirthdayDTO()
                .setId(x.getId())
                .setName(x.getName())
                .setBirthday(x.getBirthday())
                .setPhoto(x.getPhoto()))
        );
        return birthdays;
    }

    public List<UserAllDTO> getAllUsers() {
        List<UserAllDTO> userShort = new ArrayList<>();
        List<User> users = userRepository.findAll()
                .stream()
                .filter(x -> x.getIsActive() == Boolean.TRUE)
                .filter(x -> x.getRole() != UserRole.ADMIN)
                .sorted(Comparator.comparing(User::getName))
                .toList();
        users.stream().forEach(x -> userShort.add(new UserAllDTO()
                .setId(x.getId())
                .setName(x.getName())
                .setPhoto(x.getPhoto()))
        );
        return userShort;
    }
    public User createUser(UserDTO user) {
        E289.thr(userRepository.findByLoginEqualsIgnoreCase(user.getLogin()).isEmpty(), user.getLogin());
        E167.thr(!Objects.equal(userDetailsService.getRole(), UserRole.EMPLOYEE));

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

    public User updateUser(Long userId, UserDTO userDTO) {
        if (userDetailsService.getRole().equals(UserRole.EMPLOYEE) ) {
            E456.thr(java.util.Objects.equals(userId, userDetailsService.getIdNow()));
        }
        User user = userRepository.findById(userId).orElseThrow(() -> E612.thr(userId));

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
        E167.thr(!Objects.equal(userDetailsService.getRole(), UserRole.EMPLOYEE));
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
