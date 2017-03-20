package com.voltor.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Coming {
	
	private long id;
	private Date date;
	private Double sum;
	private Provider provider;
	private User user;
	private Collection<ComingPosition> collection = new ArrayList<>();
	
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
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Collection<ComingPosition> getCollection() {
		return collection;
	}
	public void setCollection(Collection<ComingPosition> collection) {
		this.collection = collection;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirmName() {
		return this.getProvider().getFirmName();
	}
	public String getName() {
		return this.getProvider().getName();
	}
	public String getUserName() {
		return this.getUser().getFirstName();
	}
	
}
