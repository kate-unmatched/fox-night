package com.tsp.foxnight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@NoRepositoryBean
public interface AbstractRepository<T> extends JpaRepository<T, Long>, QueryByExampleExecutor<T> {
}
