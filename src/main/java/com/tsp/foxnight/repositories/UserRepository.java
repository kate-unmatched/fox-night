package com.tsp.foxnight.repositories;

import com.tsp.foxnight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {

    default Class<User> getClazz() {
        return User.class;
    }

    Optional<User> findByLoginEqualsIgnoreCase(String login);

    Optional<User> findByEmailEquals(String email);
}
