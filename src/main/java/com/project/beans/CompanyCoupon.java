package com.project.beans;

/**
 * @author Linoy & Matan 
 * Bean class of CompanyCoupon
 */
public class CompanyCoupon {

	/**
	 * Data Members
	 */
	private long companyId;
	private long couponId;

	/**
	 * @Empty CTOR
	 */
	public CompanyCoupon() {
	}

	/**
	 * @Full CTOR
	 */
	public CompanyCoupon(long companyId, long couponId) {
		setCompanyId(companyId);
		setCouponId(couponId);
	}

	/**
	 * @return companyId of CompanyCoupon.
	 */
	public long getCompanyId() {
		return companyId;
	}

	/**
	 * @param Set
	 *            companyId of CompanyCoupon.
	 */
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return couponId of CompanyCoupon.
	 */
	public long getCouponId() {
		return couponId;
	}

	/**
	 * @param Set
	 *            couponId of CompanyCoupon.
	 */
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	/**
	 * print the CompanyCoupon object
	 */
	@Override
	public String toString() {
		return "CompanyCoupon---> Company Id:" + companyId + ", Coupon Id:" + couponId;
	}
}
