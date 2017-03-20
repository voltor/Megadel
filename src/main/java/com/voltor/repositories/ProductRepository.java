package com.voltor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voltor.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
	ProductEntity findOneById(Long id);
	
	@Query("SELECT count(p)>0 FROM Product p WHERE p.code = :code AND p.id != :id")
	boolean existCode( @Param("code") String code, @Param("id") Long id );
	
	@Query("SELECT p FROM Product p WHERE p.count > 0")
	List<ProductEntity> getForSelling();
	
	@Query("SELECT count(p)>0 FROM Product p WHERE p.shtrihCode = :shtrihCode AND p.id != :id")
	boolean existShtrihCode( @Param("shtrihCode") String shtrihCode, @Param("id") Long id );
	
	@Query("SELECT p.count FROM Product p WHERE p.id = :id")
	Integer getCount( @Param("id") Long id );
	
	@Modifying
	@Query("UPDATE Product p SET p.count = p.count + :count WHERE p.id = :id")
	void addCount(@Param("count") Integer count, @Param("id") Long id);
	@Modifying
	@Query("UPDATE Product p SET p.comingPrice = :comingPrice WHERE p.id = :id")
	void setPrice(@Param("comingPrice") Double comingPrice, @Param("id") Long id);

	@Query("SELECT count(p)>0 FROM Product p WHERE p.name = :name AND p.id != :id")
	boolean existName( @Param("name") String name, @Param("id") Long id);
}
