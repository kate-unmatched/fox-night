package com.tsp.foxnight.services;

import com.google.common.base.Preconditions;
import com.tsp.foxnight.dto.UserOfficerDTO;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.repositories.UserRepository;
import com.tsp.foxnight.utils.Errors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public User createUser(UserOfficerDTO user) {
        Preconditions.checkState(userRepository.findByLoginEqualsIgnoreCase(user.getLogin()).isEmpty(),
                "Пользователь с таким логином уже есть в системе");
        long leftLimit = 1L;
        long rightLimit = 10L;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        User newUser = new User()
                .setName(user.getName())
                .setLogin(user.getLogin())
                .setEmail(user.getEmail())
                .setIsActive(user.getIsActive())
                .setTelegram(user.getTelegram())
                .setBirthday(user.getBirthday())
                .setStartWork(user.getStartWork())
                .setCity(user.getCity())
                .setPhoneNumber(user.getPhoneNumber())
                .setId(new Random().nextLong())
                .setCreationDate(LocalDateTime.now())
                .setModificationDate(LocalDateTime.now())
                .setRoleId(generatedLong)
                .setPasswordHash("$2a$10$L3kmz08v.qbZssn3Z5abP.N7skjRogz/HSA2qCp0TKY/UDx32jTxi");

        return userRepository.save(newUser);
    }

    public String deleteUser(Long userId) {
        userRepository.findById(userId).ifPresent(x -> userRepository.deleteById(userId));
        return "ok";
    }

    public Optional<User> getSecretUser(){
        return userRepository.findSecret().stream().findFirst();
    }
}
