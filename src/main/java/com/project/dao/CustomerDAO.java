package com.project.dao;

import java.util.List;
import java.util.Set;
import com.project.beans.Coupon;
import com.project.beans.Customer;

/**
 * @Author - Linoy & Matan
 * @Description:
 * interface class, has all the functions that Customer use in: Customer table
 */
public interface CustomerDAO {

	public void insertCustomer(Customer Customer) throws Exception;

	public void removeCustomer(Customer customer) throws Exception;
	
	public void removeCouponFromCustomerCoupon(long couponId) throws Exception;

	public void updateCustomer(Customer Customer) throws Exception;

	public Customer getCustomer(long id) throws Exception;

	public Set<Customer> getAllCustomer() throws Exception;
	
	public Set<Coupon> getAllCustomerCoupons(long customerId) throws Exception;
	
	public List<Long> getCustomerCoupons(long customerId) throws Exception;
		
	public void customerPurchaseCoupon (Coupon coupon, Customer customer) throws Exception;
	
	public boolean login (String name, String password) throws Exception;
}
