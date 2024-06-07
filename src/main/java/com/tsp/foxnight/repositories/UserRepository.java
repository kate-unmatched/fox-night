package com.tsp.foxnight.repositories;

import com.tsp.foxnight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {

    default Class<User> getClazz() {
        return User.class;
    }

    @Override
    Optional<User> findById(Long id);
    User findByLogin (String login);

    Optional<User> findByLoginEqualsIgnoreCase(String login);
}
