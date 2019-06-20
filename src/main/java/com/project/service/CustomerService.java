//package com.project.service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//
//import com.project.beans.Coupon;
//import com.project.beans.CouponType;
//import com.project.facade.CustomerFacade;
//
//@Path("/customer")
//public class CustomerService {
//	
//	@Context
//	private HttpServletRequest request;
//	@Context
//	private HttpServletResponse response;
//	
//	private CustomerFacade getFacade() {
//		CustomerFacade customerFacade = (CustomerFacade) request.getSession(false).getAttribute("facade");
//		return customerFacade;
//	}
//	
//	//purchase Coupon
//	@POST
//	@Path("/purchaseCoupon")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String purchaseCoupon(@QueryParam("couponId")long couponId) {
//		CustomerFacade customerFacade = getFacade();
//		Coupon coupon = new Coupon();
//		try {
//			customerFacade.purchaseCoupon(customer, couponId);
//		}
//		catch (Exception e) {
//			return "failed to purchase coupon "+e;
//		}
//		return "coupon purchase "+coupon.getTitle();
//		
//	}
//	
//	//get All Purchased Coupons
//	@GET
//	@Path("/getAllPurchasedCoupons")
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
//	
//	//get All Customer Coupon
//	@GET
//	@Path("/getAllCustomerCoupon")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Set<Coupon> getAllCustomerCoupon(@QueryParam("customerId") long id){
//		Set<Coupon> coupons =new HashSet<>();
//		CustomerFacade customerFacade = getFacade();
//		try {
//			coupons = customerFacade.getAllCustomerCoupon(customer);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return coupons;
//	}
//	
//	//get Coupon by Type
//	@GET
//	@Path("/getCouponbyType")
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
//	@Path("/getCouponbyPrice")
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
//	
//
//}
