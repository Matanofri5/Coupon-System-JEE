package com.project.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.facade.CompanyFacade;


@Path("company")
public class CompanyService {
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	

	public CompanyFacade getFacade() {
		CompanyFacade companyFacade = (CompanyFacade) request.getSession(false).getAttribute("facade");
		return companyFacade;
	}

	// create Coupon
	@POST
	@Path("createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createCoupon(Company company, Coupon coupon) {
		CompanyFacade companyFacade = getFacade();
		try {
			companyFacade.createCoupon(company, coupon);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// remove Coupon By Id
	@DELETE
	@Path("removeCouponById")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeCouponById(@QueryParam("/couponId") long id) {
		CompanyFacade companyFacade = getFacade();
		try {
			companyFacade.getCouponById(id);
			return "Succeded to remove a coupon:  id = " + id;
		} catch (Exception e) {
			return "Failed to remove a coupon: " + e.getMessage();
		}
	}
//
//	// update Coupon
//	@PUT
//	@Path("updateCoupon")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void updateCoupon(@QueryParam("couponId") long id, @QueryParam("endDate") Date newEndDate,
//			@QueryParam("price") double newPrice) {
//		CompanyFacade companyFacade = getFacade();
//		try {
//			 Coupon coupon = companyFacade.getCouponById(id);
////			Company company = companyFacade.getCouponById(id);
//			
//			 if(coupon!=null) {
////			if (company != null) {
//				companyFacade.updateCoupon(coupon, newEndDate, newPrice);
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//	// get Company
//	@GET
//	@Path("getCompany")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getCompany(@QueryParam("companyId") long id) {
//		CompanyFacade companyFacade = getFacade();
//		try {
//			Company company = companyFacade.getCompany(id);
//			if (company != null) {
//				return new Gson().toJson(company);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.err.println("Failed to get company by id " + id + ", please enter another company id");
//		return null;
//	}
//
//	// get Coupon By Id
//	@GET
//	@Path("getCouponById")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getCouponById(@QueryParam("couponId") long id) {
//		CompanyFacade companyFacade = getFacade();
//		try {
//			// Coupon coupon = companyFacade.getCouponById(id);
//			Company company = companyFacade.getCouponById(id);
//			// if (coupon != null) {
//			if (company != null) {
//				return new Gson().toJson(company);
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.err.println("Failed to get coupon by id " + id + ", please enter another coupon id");
//		return null;
//	}
//	
//	// get Coupon By Id FROM MICHAL AND AB
////	@GET
////	@Path("/getCouponById")
////	@Produces(MediaType.APPLICATION_JSON)
////	public String getCouponById(@QueryParam("couponId") long id) {
////		CompanyFacade companyFacade = getFacade();
////		try {
////			Coupon coupon = companyFacade.getCouponById(id);
////			if (coupon!=null) {
////				return new Gson().toJson(coupon);
////			}
////			else {
////				return null;
////			}
////		}
////		catch (Exception e) {
////			System.err.println("get coupon by id failed " + e.getMessage());
////			return null;
////		}
////	}
//	
//
//	// get All Company Coupons
//	@GET
//	@Path("getAllCompanyCoupons")
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAllCompanyCoupons(@QueryParam("companyId") long companyId) {
//		CompanyFacade companyFacade = getFacade();
//		try {
//			List<Long> companies = companyFacade.getAllCompanyCoupons(companyId);
//			return new Gson().toJson(companies);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.err.println("this company id " + companyId + ", dont have coupons");
//		return null;
//	}
//
//	// get Coupon by Type
//	@GET
//	@Path("getCouponbyType")
//	@Produces(MediaType.APPLICATION_JSON)
//	// must Company company :((
//	public String getCouponbyType(@QueryParam("companyId") Company company,
//			@QueryParam("couponType") CouponType couponType) {
//		CompanyFacade companyFacade = getFacade();
//		try {
//			List<Coupon> coupons = companyFacade.getCouponbyType(company, couponType);
//			return new Gson().toJson(coupons);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.err.println("this company id " + company + ", dont have this coupons type");
//		return null;
//	}
//
//	// get Coupon By Price
//	@GET
//	@Path("getCouponByPrice")
//	@Produces(MediaType.APPLICATION_JSON)
//	// must Company company :((
//	public String getCouponByPrice(@QueryParam("companyId") Company company, @QueryParam("price") double price) {
//		CompanyFacade companyFacade = getFacade();
//		try {
//			List<Coupon> coupons = companyFacade.getCouponByPrice(company, price);
//			return new Gson().toJson(coupons);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.err.println("this company id " + company + ", dont have coupons by this price");
//		return null;
//	}
//
//	// get Coupon By Date
//	@GET
//	@Path("getCouponByDate")
//	@Produces(MediaType.APPLICATION_JSON)
//	// must Company company :((
//	public String getCouponByDate(@QueryParam("companyId") Company company, @QueryParam("endDate") Date endDate) {
//		CompanyFacade companyFacade = getFacade();
//		try {
//			List<Coupon> coupons = companyFacade.getCouponByDate(company, endDate);
//			return new Gson().toJson(coupons);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.err.println("this company id " + company + ", dont have coupons by thid end date");
//		return null;
//	}
}
