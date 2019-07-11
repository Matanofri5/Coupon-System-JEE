package com.project.service;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	
	private CouponSystem couponSystem;
	
//	private CustomerFacade getFacade() {
//		CustomerFacade customerFacade = (CustomerFacade) request.getSession(false).getAttribute("facade");
//		return customerFacade;
//	}
	
	private CustomerFacade getFacade() throws LoginException, Exception {
		CustomerFacade customerFacade = (CustomerFacade)couponSystem.login("Matan", "123", ClientType.CUSTOMER);
		return customerFacade;
	}
	
	// PURCHASE Coupon
	@POST
	@Path("purchaseCoupon/{couponId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response purchaseCoupon(@PathParam("couponId")long couponId) throws LoginException, Exception {
		
		CustomerFacade customerFacade = getFacade();
		
		try {
			customerFacade.purchaseCoupon(couponId);
			String res = "CUSTOMER SUCCEDD TO PURCHASE COUPON " + couponId;
			String reString = new Gson().toJson(res);
			return Response.status(Response.Status.OK).entity(reString).build();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	// GET All Purchased Coupons by id
	@GET
	@Path("getAllPurchasedCoupons/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPurchasedCoupons(@PathParam("customerId") long customerId) throws LoginException, Exception{
		CustomerFacade customerFacade = getFacade();
		try {
			Set<Coupon> coupons = customerFacade.getAllpurchasedCoupons(customerId);
			return new Gson().toJson(coupons);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// GET All Customer Coupon
	@GET
	@Path("getAllCustomerCouponByObj")
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
	
	// GET Coupon by Type
	@GET
	@Path("getCouponbyType/{couponType}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCouponbyType(@PathParam("couponType") CouponType couponType ) throws LoginException, Exception{
		CustomerFacade customerFacade = getFacade();
		try {
			List<Coupon>coupons = customerFacade.getCouponbyType(couponType);
			return new Gson().toJson(coupons);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// GET Coupon by price
	@GET
	@Path("getCouponbyPrice/{couponPrice}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCouponbyPrice(@PathParam("couponPrice") double price ) throws LoginException, Exception{
		CustomerFacade customerFacade = getFacade();
		try {
			List<Coupon>coupons = customerFacade.getCouponByPrice(price);
			return new Gson().toJson(coupons);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	

}
