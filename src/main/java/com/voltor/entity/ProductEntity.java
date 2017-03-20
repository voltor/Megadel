package com.voltor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Product")
@Table(name = "products", schema = "public")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "sub_category_id", nullable = false)
	private SubCategoryEntity subCategory;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "price_opt")
	private Double priceOpt;
	
	@Column(name = "price1")
	private Double price1;
	
	@Column(name = "price_opt1")
	private Double priceOpt1;
	
	@Column(name = "coming_price")
	private Double comingPrice;
	
	@Column(name = "shtrih_code")
	private String shtrihCode;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "count")
	private Integer count;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getPriceOpt() {
		return priceOpt;
	}

	public void setPriceOpt(Double priceOpt) {
		this.priceOpt = priceOpt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SubCategoryEntity getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategoryEntity subCategory) {
		this.subCategory = subCategory;
	}

	public Double getPrice1() {
		return price1;
	}

	public void setPrice1(Double price1) {
		this.price1 = price1;
	}

	public Double getPriceOpt1() {
		return priceOpt1;
	}

	public void setPriceOpt1(Double priceOpt1) {
		this.priceOpt1 = priceOpt1;
	}

	public String getShtrihCode() {
		return shtrihCode;
	}

	public void setShtrihCode(String shtrihCode) {
		this.shtrihCode = shtrihCode;
	}

	public Double getComingPrice() {
		return comingPrice;
	}

	public void setComingPrice(Double comingPrice) {
		this.comingPrice = comingPrice;
	}


}
