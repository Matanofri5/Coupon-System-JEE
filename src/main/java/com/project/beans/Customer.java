package com.project.beans;

/**
 * @author Linoy & Matan Bean class of customer
 */
public class Customer {

	/**
	 * Data Members
	 */
	private long id;
	private String customerName;
	private String password;

	/**
	 * @Empty CTOR
	 */
	public Customer() {
	}

	/**
	 * @Full CTOR
	 */
	public Customer(long id, String customerName, String password) {
		setCustomerName(customerName);
		setId(id);
		setPassword(password);
	}

	/**
	 * @Full CTOR
	 */
	public Customer(String customerName, String password) {
		setCustomerName(customerName);
		setPassword(password);
	}

	/**
	 * @return id of customer.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param Set
	 *            id of customer.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return customerName of customer.
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param Set
	 *            customerName of customer.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return password of customer.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param Set
	 *            password of customer.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * print the customer object
	 */
	@Override
	public String toString() {
		return "id:" + id + ", customer Name:" + customerName + ", password:" + password;
	}

}
