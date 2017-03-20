package com.voltor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voltor.entity.TickEntity;

public interface TickRepository extends JpaRepository<TickEntity, Integer> {
	TickEntity findOneById(Long id);
	@Modifying
	@Query("UPDATE Tick p SET p.valueUSA = p.valueUSA + :valueUSA WHERE p.id = :id")
	void addValueUSA(@Param("valueUSA") Double valueUSA, @Param("id") Long id);
	@Modifying
	@Query("UPDATE Tick p SET p.valueUA = p.valueUA + :valueUA WHERE p.id = :id")
	void addValueUA(@Param("valueUA") Double valueUA, @Param("id") Long id);
}
