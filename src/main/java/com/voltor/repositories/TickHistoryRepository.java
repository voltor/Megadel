package com.voltor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voltor.bean.TickHistoryType;
import com.voltor.entity.TickEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.entity.UserEntity;

public interface TickHistoryRepository extends JpaRepository<TickHistoryEntity, Integer> {
	TickHistoryEntity findOneById(Long id);
	List<TickHistoryEntity> findAllByTickEntityOrderByDateDesc(TickEntity tickEntity);
	
	@Query("SELECT t FROM TickHistory t WHERE t.userEntity = :user"
			+ " AND t.transferCashEntity = null AND t.type = :type")
	List<TickHistoryEntity> findForUserCash( @Param("user") UserEntity userEntity , @Param("type") TickHistoryType type);
}
