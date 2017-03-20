package com.voltor.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.voltor.bean.SellingType;


@Entity(name = "Selling")
@Table(name = "selling", schema = "public")
public class SellingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "date")
	private Date date;
	
	@Column(name = "sum")
	private Double sum;
	
	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false )
	private SellerEntity sellerEntity;

	@ManyToOne (fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false )
	private UserEntity userEntity;
	
	@ManyToOne
	@JoinColumn(name = "transfer_cash_id")
	private TransferCashEntity transferCashEntity;
	
	@Column(name = "type")
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

	public SellerEntity getSellerEntity() {
		return sellerEntity;
	}

	public void setSellerEntity(SellerEntity sellerEntity) {
		this.sellerEntity = sellerEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public TransferCashEntity getTransferCashEntity() {
		return transferCashEntity;
	}

	public void setTransferCashEntity(TransferCashEntity transferCashEntity) {
		this.transferCashEntity = transferCashEntity;
	}

	public SellingType getType() {
		return type;
	}

	public void setType(SellingType type) {
		this.type = type;
	}
	
}
