package com.tsp.foxnight.repositories;

import com.tsp.foxnight.entity.Link;
import com.tsp.foxnight.entity.RestAudit;
import com.tsp.foxnight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

    default Class<User> getClazz() {
        return User.class;
    }

}
