package com.project.beans;

import java.sql.Date;


/**
 * @author Linoy & Matan Bean class of coupon
 */
public class Coupon {
	/**
	 * Data Members
	 */
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private String message;
	private double price;
	private String image;
	private CouponType type;

	/**
	 * @Empty CTOR
	 */
	public Coupon() {
	}

	/**
	 * @Full CTOR
	 */
	public Coupon(long id, String title, java.util.Date startDate, java.util.Date endDate, int amount, String message,
			double price, String image, CouponType couponType) {
		super();
		setId(id);
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setMessage(message);
		setPrice(price);
		setImage(image);
		setType(couponType);
	}
	
	
	/**
	 * @Full CTOR
	 */
	public Coupon(String title, java.util.Date startDate, java.util.Date endDate, int amount, String message,
			double price, String image, CouponType couponType) {
		super();
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setMessage(message);
		setPrice(price);
		setImage(image);
		setType(couponType);
	}

	/**
	 * @return id of coupon.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param Set
	 *            id of coupon.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return title of coupon.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param Set
	 *            title of coupon.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return startDate of coupon.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param Set
	 *            startDate of coupon.
	 */
	public void setStartDate(java.util.Date newStartDate) {
		startDate = (Date) newStartDate;
	}

	/**
	 * @return endDate of coupon.
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param Set
	 *            endDate of coupon.
	 */
	public void setEndDate(java.util.Date newEndDate) {
		this.endDate = (Date) newEndDate;
	}

	/**
	 * @return amount of coupon.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param Set
	 *            amount of coupon.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return message of coupon.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param Set
	 *            message of coupon.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return price of coupon.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param Set
	 *            price of coupon.
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return image of coupon.
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param Set
	 *            image of coupon.
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return type of coupon.
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * @param Set
	 *            type of coupon.
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * print the coupon object
	 */
	@Override
	public String toString() {
		return "Coupon--->  Id:" + id + ", Title:" + title + ", Start Date:" + startDate + ", End Date:" + endDate
				+ ", Amount:" + amount + ", Message:" + message + ", Price:" + price + ", Image:" + image + ", Type:"
				+ type;
	}
}
