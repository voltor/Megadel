package com.voltor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.voltor.bean.PriceType;

@Entity(name = "Seller")
@Table(name = "seller", schema = "public")
public class SellerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "can_give_a_loan")
	private boolean canGiveALoan;
	
	@Column(name = "price_type")
	private PriceType priceType;
	
	@ManyToOne
	@JoinColumn(name = "tick", nullable = false )
	private TickEntity tick;

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
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TickEntity getTick() {
		return tick;
	}

	public void setTick(TickEntity tick) {
		this.tick = tick;
	}

	public boolean isCanGiveALoan() {
		return canGiveALoan;
	}

	public void setCanGiveALoan(boolean canGiveALoan) {
		this.canGiveALoan = canGiveALoan;
	}

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}
}
