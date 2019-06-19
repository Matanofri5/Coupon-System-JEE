package com.project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.facade.CompanyFacade;


@Path("/company")
public class CompanyService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	public CompanyFacade getFacade() {
			CompanyFacade companyFacade = (CompanyFacade) request.getSession(false).getAttribute("facade");
			return companyFacade;
	}
	
	@POST
	@Path("/createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createCoupon (Company company, Coupon coupon) {
			CompanyFacade companyFacade = getFacade();
			try {
				companyFacade.createCoupon(company, coupon);
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
}
}
