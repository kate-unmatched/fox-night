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

    User findByLogin(String login);

    Optional<User> findByLoginEqualsIgnoreCase(String login);

    @Query(value = "SELECT * FROM User u WHERE " +
            "DATE_FORMAT(u.birthday, '%m-%d') BETWEEN " +
            "DATE_FORMAT(CURDATE(), '%m-%d') AND DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 7 DAY), '%m-%d') " +
            "AND u.isActive = true " +
            "AND u.role != 'ADMIN'", nativeQuery = true)
    List<User> getBirthdayUsers();
}
