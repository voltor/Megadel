package com.voltor.bean;

import com.voltor.services.ExchangeRateService;

public class Product {

	private long id;

	private String name;
	private Double price;
	private Integer count;
	private SubCategory subCategory;
	private Double priceOpt;
	private String code;
	private Double price1;
	private Double priceOpt1;
	private String shtrihCode;
	private PriceType priceType;
	private Double comingPrice;

	public Product() {
		super();
	}

	public Product(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.count = product.getCount();
		this.subCategory = product.getSubCategory();
		this.priceOpt = product.getPriceOpt();
		this.code = product.getCode();
		this.price1 = product.getPrice1();
		this.priceOpt1 = product.getPriceOpt1();
		this.shtrihCode = product.getShtrihCode();
	}

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

	public void setPrice(Double prise) {
		this.price = prise;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
        return id == other.id;
    }

	public Product getThis() {
		return this;
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

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public Category getCategory() {
		return subCategory.getCategory();
	}

	public void setSubCategory(SubCategory subCategory) {
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

	public Double getPriceByType() {
		if(priceType==null){
			return null;
		}
		switch (priceType) {
		case PRICE_1:
			return price == null ? null : ExchangeRateService.getPriseInUA( price );
		case PRICE_2:
			return price1 == null ? null : ExchangeRateService.getPriseInUA( price1 );
		case PRICE_OPT_1:
			return priceOpt == null ? null : ExchangeRateService.getPriseInUA( priceOpt );
		case PRICE_OPT_2:
			return priceOpt1 == null ? null : ExchangeRateService.getPriseInUA( priceOpt1 );
		default:
			return price == null ? null : ExchangeRateService.getPriseInUA( price );
		}
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	public Double getComingPrice() {
		return comingPrice;
	}

	public void setComingPrice(Double comingPrice) {
		this.comingPrice = comingPrice;
	}
}