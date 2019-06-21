package com.project.service;

import com.project.beans.Company;
import com.project.beans.Coupon;

public class Moshe {
	Company company;
	Coupon coupon;
	
	
	public Moshe(Company company, Coupon coupon) {
		this.company = company;
		this.coupon = coupon;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
	

}
