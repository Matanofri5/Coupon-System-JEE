package com.project.beans;

public class User {

	private String name;
	private String pass;
	private String type;

	public User() {
	}
	

	public User(String name, String pass, String type) {
		this.name = name;
		this.pass = pass;
		this.type = type;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return pass;
	}

	public void setPassword(String password) {
		this.pass = password;
	}

	public String getClientType() {
		return type;
	}

	public void setClientType(String clientType) {
		this.type = clientType;
	}
	
	
}
