package com.voltor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "SellingPosition")
@Table(name = "selling_position", schema = "public")
public class SellingPositionEntity {
	
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
	@JoinColumn(name = "selling_id", nullable = false )
	private SellingEntity sellingEntity;
	
	@ManyToOne
	@JoinColumn(name = "exchange_rate_id", nullable = false )
	private ExchangeRateEntity exchangeRateEntity;

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

	public SellingEntity getSellingEntity() {
		return sellingEntity;
	}

	public void setSellingEntity(SellingEntity sellingEntity) {
		this.sellingEntity = sellingEntity;
	}

	public ExchangeRateEntity getExchangeRateEntity() {
		return exchangeRateEntity;
	}

	public void setExchangeRateEntity(ExchangeRateEntity exchangeRateEntity) {
		this.exchangeRateEntity = exchangeRateEntity;
	}
	
}
