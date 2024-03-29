package com.project.service;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.Customer;
import com.project.exceptions.CompanyAlreadyExistsException;
import com.project.exceptions.CustomerAlreadyExistsException;
import com.project.exceptions.LoginException;
import com.project.exceptions.RemoveCompanyException;
import com.project.facade.AdminFacade;
import com.project.main.ClientType;
import com.project.main.CouponSystem;

@Path("admin")
public class AdminService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	private static CouponSystem couponSystem;
	
	private AdminFacade getFacade() {
			AdminFacade adminFacade = (AdminFacade) request.getSession(false).getAttribute("facade");
			return adminFacade;
			}
	
//	private AdminFacade getFacade() throws LoginException, Exception {
//		AdminFacade adminFacade = null;
//		adminFacade = (AdminFacade)couponSystem.login("admin", "1234", ClientType.ADMIN);
//		return adminFacade;	
//		
//	}
	
	
		// Create a new company in the db
		@POST
		@Path("createCompany")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response createCompany (String company) throws Exception {
				
			Gson gson  = new Gson(); 
			Company companyFromJson = gson.fromJson(company, Company.class);
				
			AdminFacade adminFacade = getFacade();
			System.out.println(companyFromJson);
			try {					
				adminFacade.createCompany(companyFromJson);
				String res = "SUCCEDD TO CREATE NEW COMPANY " + companyFromJson;
				String reString = new Gson().toJson(res);
				return Response.status(Response.Status.OK).entity(reString).build();
			} catch (CompanyAlreadyExistsException e) {			
				System.out.println(e.getMessage());
			}
			return null;
			}
	
	
		// REMOVE a Company
		@DELETE
		@Path("removeCompany/{companyId}")
		@Produces(MediaType.APPLICATION_JSON)
		public void removeCompany (@PathParam("companyId") long id) throws Exception {
			AdminFacade adminFacade = getFacade();
			Company company = null;
			try {
				company = adminFacade.getCompany(id);
				if(company != null) {
					adminFacade.removeCompany(company);
					System.out.println("Succeed to remove company " + company.getCompanyName());
				}
			} catch (RemoveCompanyException e) {
				e.printStackTrace();
			}
		}
	
		
		// UPDATE a company
		@POST
		@Path("updateCompany")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public void updateCompany (@QueryParam("companyId") long id, @QueryParam("password") String password,
				@QueryParam("email") String email) throws LoginException, Exception {
			AdminFacade adminFacade = getFacade();

			try {
				Company company = adminFacade.getCompany(id);
				if (company != null) {
					adminFacade.updateCompany(company, password, email);
				}
			} catch (Exception e) {
					System.out.println(e.getMessage());
			}
		}
		
		// GET company by id
		@GET
		@Path("getCompany/{companyId}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getCompany (@PathParam("companyId") long id) throws LoginException, Exception {
			
			AdminFacade adminFacade = getFacade();
			try {
				Company company = adminFacade.getCompany(id);
				if (company != null) {
					return new Gson().toJson(company);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.err.println("Failed to get company by id " + id + ", please enter another company id");
			return null;
		}
	
		// GET all companies
		@GET
		@Path("getAllCompanies")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllCompanies () throws LoginException, Exception {
			
			AdminFacade adminFacade = getFacade();

			try {
				Set<Company> companies = adminFacade.getAllCompanys();
				return new Gson().toJson(companies);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		
		// CREATE a new customer in the db
		@POST
		@Path("createCustomer")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response createCustomer (String customer) throws Exception {
			
			AdminFacade adminFacade = getFacade();
			Gson gson = new Gson();
			Customer customerFromJson = gson.fromJson(customer, Customer.class);
			System.out.println(customerFromJson);
			try {
				adminFacade.createCustomer(customerFromJson);
				String res = "SUCCEDD TO CREATE NEW CUSTOMER " + customerFromJson;
				String reString = new Gson().toJson(res);
				return Response.status(Response.Status.OK).entity(reString).build();
				} catch (CustomerAlreadyExistsException e) {
				System.out.println("This customer already exists !");
			}
			return null;
		}
		
		
		// REMOVE a customer
		@DELETE
		@Path("removeCustomer/{customerId}")
		@Produces(MediaType.APPLICATION_JSON)
		public void removeCustomer (@PathParam("customerId") long id) throws LoginException, Exception {
			
			AdminFacade adminFacade = getFacade();
			Customer customer = null;
			
			try {
				customer = adminFacade.getCustomer(id);
				if (customer != null) {
					adminFacade.removeCustomer(customer);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		// UPDATE a customer
		@POST
		@Path("updateCustomer")
		@Produces(MediaType.APPLICATION_JSON)
		public void updateCustomer(@QueryParam("customerId") long id, @QueryParam("password") String password) throws LoginException, Exception {
			AdminFacade adminFacade = getFacade();
			try {
				Customer customer = adminFacade.getCustomer(id);
				if (customer!=null) {
					adminFacade.updateCustomer(customer, password);
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		// GET customer by id
		@GET
		@Path("getCustomer/{customerId}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getCustomer(@PathParam("customerId") long id) throws LoginException, Exception {
			AdminFacade adminFacade = getFacade();
			try {
				Customer customer = adminFacade.getCustomer(id);
				if(customer!= null) {
					return new Gson().toJson(customer);
				}
			}
			catch (Exception e) {
			System.out.println(e.getMessage());
			}
			System.err.println("Failed to get customer by id " + id + ", please enter another customer id");
			return null;
		}
		
		// GET all customers
		@GET
		@Path("getAllCustomers")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllCustomers() throws LoginException, Exception {
			AdminFacade adminFacade = getFacade();
			try {
				Set<Customer>customers = adminFacade.getAllCustomers();
				return new Gson().toJson(customers);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
		
		// GET all coupons from Database
		@GET
		@Path("getAllCouponsFromDB")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllCouponsFromDB() throws LoginException, Exception {
			AdminFacade adminFacade = getFacade();
			try {
				Set<Coupon>coupons = adminFacade.getAllCouponsFromDB();
				return new Gson().toJson(coupons);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return null;
		}
}
