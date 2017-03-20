package com.voltor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.OrganizationEntity;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer> {
	OrganizationEntity findOneById(Long id);
}
