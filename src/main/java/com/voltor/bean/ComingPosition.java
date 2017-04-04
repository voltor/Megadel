package com.voltor.bean;

public class ComingPosition {

	private long id;
	private Product product;
	private Double price;
	private Integer count;
	private Double sum;
	private Coming coming;
	
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
	public Coming getComing() {
		return coming;
	}
	public void setComing(Coming coming) {
		this.coming = coming;
	}
	public ComingPosition getThis(){
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
		ComingPosition other = (ComingPosition) obj;
        return id == other.id;
    }
	
}
