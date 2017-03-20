package com.voltor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Tick")
@Table(name = "tick", schema = "public")
public class TickEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "value_ua")
	private Double valueUA;
	
	@Column(name = "value_usa")
	private Double valueUSA;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getValueUA() {
		return valueUA;
	}

	public void setValueUA(Double valueUA) {
		this.valueUA = valueUA;
	}

	public Double getValueUSA() {
		return valueUSA;
	}

	public void setValueUSA(Double valueUSA) {
		this.valueUSA = valueUSA;
	}
	
	public void minusUSA(Double valueUSA) {
		this.valueUSA   = this.valueUSA - valueUSA;
	}
	
	public void plusUA(Double valueUA) {
		this.valueUA   = this.valueUA + valueUA;
	}
	
}
