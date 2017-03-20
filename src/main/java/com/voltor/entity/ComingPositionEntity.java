package com.voltor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "ComingPosition")
@Table(name = "coming_position", schema = "public")
public class ComingPositionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false )
	private ProductEntity productEntity ;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "count")
	private Integer count;
	
	@Column(name = "sum")
	private Double sum;

	@ManyToOne
	@JoinColumn(name = "coming_id", nullable = false )
	private ComingEntity comingEntity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public ComingEntity getComingEntity() {
		return comingEntity;
	}

	public void setComingEntity(ComingEntity comingEntity) {
		this.comingEntity = comingEntity;
	}
	
}
