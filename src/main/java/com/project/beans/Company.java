package com.project.beans;

/**
 * @author Linoy & Matan Bean class of company
 */
public class Company {
	/**
	 * Data Members
	 */
	private long id;
	private String companyName;
	private String password;
	private String email;

	/**
	 * @Empty CTOR
	 */
	public Company() {
	}

//	/**
//	 * @Full CTOR
//	 */
	public Company(long id, String companyName, String password, String email) {
		setId(id);
		setCompanyName(companyName);
		setPassword(password);
		setEmail(email);
	}

	/**
	 * @Full CTOR
	 */
	public Company(String companyName, String password, String email) {
		setCompanyName(companyName);
		setPassword(password);
		setEmail(email);
	}

	/**
	 * @return id of company.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param Set
	 *            id of company.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return companyName of company.
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param Set
	 *            companyName of company.
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return password of company.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param Set
	 *            password of company.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return email of company.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param Set
	 *            email of company.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * print the company object
	 */
	@Override
	public String toString() {
		return "id:" + id + ", company name:" + companyName + ", password:" + password + ", email:" + email;
	}
}
