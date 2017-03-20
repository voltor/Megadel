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


@Entity(name = "Coming")
@Table(name = "coming", schema = "public")
public class ComingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "date")
	private Date date;
	
	@Column(name = "sum")
	private Double sum;
	
	@ManyToOne
	@JoinColumn(name = "provider_id", nullable = false )
	private ProviderEntity providerEntity;

	@ManyToOne (fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false )
	private UserEntity userEntity;
	
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public ProviderEntity getProviderEntity() {
		return providerEntity;
	}
	public void setProviderEntity(ProviderEntity providerEntity) {
		this.providerEntity = providerEntity;
	}
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
}
