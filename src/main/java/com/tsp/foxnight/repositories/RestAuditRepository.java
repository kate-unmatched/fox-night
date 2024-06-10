package com.tsp.foxnight.repositories;

import com.tsp.foxnight.entity.RestAudit;
import com.tsp.foxnight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestAuditRepository extends JpaRepository<RestAudit, Long> {

    default Class<User> getClazz() {
        return User.class;
    }

}
