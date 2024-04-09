package com.tsp.foxnight.services;

import com.tsp.foxnight.dto.UserOfficerDTO;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.repositories.UserRepository;
import com.tsp.foxnight.utils.Errors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public User createUser(UserOfficerDTO user) {
        if (userRepository.findByLoginEqualsIgnoreCase(user.getLogin()).isPresent())
            Errors.E289.thr(user.getLogin());

        User newUser = new User()
                .setName(user.getName())
                .setLogin(user.getLogin())
                .setEmail(user.getEmail())
                .setIsActive(user.getIsActive())
                .setTelegram(user.getTelegram())
                .setBirthday(user.getBirthday())
                .setStartWork(user.getStartWork())
                .setCity(user.getCity())
                .setPhoneNumber(user.getPhoneNumber());

        return userRepository.save(newUser);
    }
}
