package com.voltor.bean;

public class SellingPosition {

	private long id;
	private Product product;
	private Double price;
	private Integer count;
	private Double sum;
	private Selling selling;
	private ExchangeRate exchangeRate;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
	
	public String getProductName() {
		return product.getName();
	}
	
	public String getProductCode() {
		return product.getCode();
	}
	
	public String getProductShtrihCode() {
		return product.getShtrihCode();
	}
	
	public void calculateSum() {
		 this.sum = this.price*this.count;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Selling getSelling() {
		return selling;
	}
	public void setSelling(Selling selling) {
		this.selling = selling;
	}
	public ExchangeRate getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(ExchangeRate exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public SellingPosition getThis(){
		return this;
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
		SellingPosition other = (SellingPosition) obj;
        return id == other.id;
    }
	
}
