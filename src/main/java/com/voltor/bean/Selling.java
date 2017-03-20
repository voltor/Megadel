package com.voltor.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Selling {
	
	private long id;
	private Date date;
	private Double sum;
	private Double forSeller;
	private Seller seller;
	private User user;
	private Collection<SellingPosition> collection = new ArrayList<>();
	private SellingType type;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Collection<SellingPosition> getCollection() {
		return collection;
	}
	public void setCollection(Collection<SellingPosition> collection) {
		this.collection = collection;
	}
	public Double getForSeller() {
		return forSeller;
	}
	public void setForSeller(Double forSeller) {
		this.forSeller = forSeller;
	}
	public String getName() {
		return this.getSeller().getName();
	}
	public String getUserName() {
		return this.getUser().getFirstName();
	}
	public SellingType getType() {
		return type;
	}
	public void setType(SellingType type) {
		this.type = type;
	}
}
