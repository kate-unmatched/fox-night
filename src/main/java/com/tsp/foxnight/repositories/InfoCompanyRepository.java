package com.tsp.foxnight.repositories;

import com.tsp.foxnight.entity.InfoCompany;
import com.tsp.foxnight.entity.RestAudit;
import com.tsp.foxnight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoCompanyRepository extends JpaRepository<InfoCompany, Long> {

    default Class<User> getClazz() {
        return User.class;
    }

}
