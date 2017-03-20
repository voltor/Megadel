package com.voltor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voltor.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	UserEntity findOneByAuthName(String authName);
	UserEntity findOneById(Long id);
	
	@Query("SELECT count(u)>0 FROM User u WHERE u.authName = :authName AND u.id != :id")
	boolean existAuthName( @Param("authName") String authName, @Param("id") Long id );
	
	void deleteById(Long id);
}
