package com.voltor.bean;

import com.voltor.entity.TickEntity;

public class User {
	
	private long id;

	private String firstName;
	private String lastName;
	private String phone;
	private String email; 
	private String authName;
	private String authPassword;
	private Role role;
	private TickEntity tick;

	public User(String authName, String authPassword, Role role) {
		super();
		this.authName = authName;
		this.authPassword = authPassword;
		this.role = role;
	}

	public User(String authName, String authPassword) {
		super();
		this.authName = authName;
		this.authPassword = authPassword;
	}
	
	public User() {
		super();
	}
	
	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthPassword() {
		return authPassword;
	}

	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	public User getThis() {
		return this;
	}

	public TickEntity getTick() {
		return tick;
	}

	public void setTick(TickEntity tick) {
		this.tick = tick;
	}
}
