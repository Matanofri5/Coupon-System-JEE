package com.project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.beans.Customer;
import com.project.exceptions.LoginException;
import com.project.facade.CustomerFacade;
import com.project.main.ClientType;
import com.project.main.CouponSystem;

@Path("customer")
public class CustomerService {
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
//	private CustomerFacade getFacade() {
//		CustomerFacade customerFacade = (CustomerFacade) request.getSession(false).getAttribute("facade");
//		return customerFacade;
//	}
	
	private CustomerFacade getFacade() throws LoginException, Exception {
		CustomerFacade customerFacade = (CustomerFacade)CouponSystem.login("Linoy", "456", ClientType.CUSTOMER);
		return customerFacade;
	}
	
//	//purchase Coupon
//	@POST
//	@Path("purchaseCoupon")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String purchaseCoupon(@QueryParam("couponId")long couponId) {
//		CustomerFacade customerFacade = getFacade();
//		Coupon coupon = new Coupon();
//		try {
//			Customer customer = new Customer();
//			customerFacade.purchaseCoupon(customer, couponId);
//		}
//		catch (Exception e) {
//			return "failed to purchase coupon "+e;
//		}
//		return "coupon purchase "+coupon.getTitle();
//		
//	}
	
//	//get All Purchased Coupons
//	@GET
//	@Path("getAllPurchasedCoupons")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Set<Coupon> getAllPurchasedCoupons(@QueryParam("customerId") long customerId){
//		Set<Coupon> coupons =new HashSet<>();
//		CustomerFacade customerFacade = getFacade();
//		try {
//			coupons = customerFacade.getAllpurchasedCoupons(customerId);
//		}
//		catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return coupons;
//	}
	
	//get All Customer Coupon
	@GET
	@Path("getAllCustomerCouponByObj")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCustomerCoupon(Customer customer) throws LoginException, Exception{
		CustomerFacade customerFacade = getFacade();
		try {
			List<Coupon> coupons =customerFacade.getAllCustomerCoupon(customer);
			return new Gson().toJson(coupons);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
//	
//	//get Coupon by Type
//	@GET
//	@Path("getCouponbyType")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Set<Coupon> getCouponbyType(@QueryParam("couponType") CouponType couponType ){
//		Set<Coupon> couponsByType =new HashSet<>();
//		CustomerFacade customerFacade = getFacade();
//		try {
//			couponsByType = customerFacade.getCouponbyType(customer, couponType);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return couponsByType;
//	}
//	
//	//get Coupon by price
//	@GET
//	@Path("getCouponbyPrice")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Set<Coupon> getCouponbyPrice(@QueryParam("couponPrice") double price ){
//		Set<Coupon> couponsByPrice =new HashSet<>();
//		CustomerFacade customerFacade = getFacade();
//		try {
//			couponsByPrice = customerFacade.getCouponByPrice(customer, price);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return couponsByPrice;
//	}
	

}
