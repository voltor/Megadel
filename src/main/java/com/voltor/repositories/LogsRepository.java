package com.voltor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.LogsEntity;

public interface LogsRepository extends JpaRepository<LogsEntity, Integer> {
}
