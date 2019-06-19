package com.project.beans;

/**
 * @author Linoy & Matan Bean class of CustomerCoupon
 */
public class CustomerCoupon {

	/**
	 * Data Members
	 */
	private long customerId;
	private long couponId;

	/**
	 * @Empty CTOR
	 */
	public CustomerCoupon() {
	}

	/**
	 * @Full CTOR
	 */
	public CustomerCoupon(long customerId, long couponId) {
		setCustomerId(customerId);
		setCouponId(couponId);
	}

	/**
	 * @return CustomerId of CustomerCoupon.
	 */
	public long getCustomerId() {
		return customerId;
	}

	/**
	 * @param Set
	 *            CustomerId of CustomerCoupon.
	 */
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return CouponId of CustomerCoupon.
	 */
	public long getCouponId() {
		return couponId;
	}

	/**
	 * @param Set
	 *            CouponId of CustomerCoupon.
	 */
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	/**
	 * print the CustomerCoupon object
	 */
	@Override
	public String toString() {
		return "CustomerCoupon---> Customer Id:" + customerId + ", Coupon Id:" + couponId;
	}
}
