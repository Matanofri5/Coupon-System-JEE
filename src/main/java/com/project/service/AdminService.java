package com.project.service;

import java.util.Set;
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
import com.project.beans.Customer;
import com.project.exceptions.CompanyAlreadyExistsException;
import com.project.exceptions.CustomerAlreadyExistsException;
import com.project.exceptions.RemoveCompanyException;
import com.project.facade.AdminFacade;

@Path("/admin")
public class AdminService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	private AdminFacade getFacade() {
			AdminFacade adminFacade = (AdminFacade) request.getSession(false).getAttribute("facade");
			return adminFacade;
			}
	
	
	// Create a new company in the db
	@POST
	@Path("/createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createCompany (Company company) throws Exception {
			AdminFacade adminFacade = getFacade();
//			String failmsg = "Failed to create company "+ company.getCompanyName()+" , please change the company name";
			try {
				adminFacade.createCompany(company);
				return new Gson().toJson(company);
//				return "Succeed to create company " + company.getCompanyName();
			} catch (CompanyAlreadyExistsException e) {
				System.out.println(e.getMessage());
			}
			return null;
	}
	
	
		// REMOVE a Company
		@DELETE
		@Path("/removeCompany")
		@Produces(MediaType.APPLICATION_JSON)
		public void removeCompany (@QueryParam("companyId") long id) throws Exception {
//			String failmsg = "Failed to remove company: this id doesn't exist, please enter another company id";
			AdminFacade adminFacade = getFacade();
			Company company = null;
			try {
				company = adminFacade.getCompany(id);
				if(company != null) {
					adminFacade.removeCompany(company);
//					return "Succeed to remove company " + company.getCompanyName() + " by id " + id;
				}
			} catch (RemoveCompanyException e) {
				e.printStackTrace();
			}
//			return failmsg;
		}
	
		
		// UPDATE a company
		@PUT
		@Path("/updateCompany")
		@Produces(MediaType.APPLICATION_JSON)
		public void updateComany (@QueryParam("companyId") long id, @QueryParam("password") String password,
				@QueryParam("email") String email) {
			AdminFacade adminFacade = getFacade();

			try {
				Company company = adminFacade.getCompany(id);
				if (company != null) {
					adminFacade.updateCompany(company, password, email);
//					return "Succeed to update company " + company.getCompanyName();
				}
			} catch (Exception e) {
					System.out.println(e.getMessage());
			}
//			return "Failed to update company";
		}
		
		// GET company by id
		@GET
		@Path("/getCompany")
		@Produces(MediaType.APPLICATION_JSON)
		public String getCompany (@QueryParam("companyId") long id) {
			
			AdminFacade adminFacade = getFacade();
			try {
				Company company = adminFacade.getCompany(id);
				if (company != null) {
//					System.out.println(company.getCompanyName() + ", id = " + company.getId());
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
		@Path("/getAllCompanies")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllCompanies () {
			
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
		@Path("/createCustomer")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String createCustomer (Customer customer) throws Exception {
			
			AdminFacade adminFacade = getFacade();
			try {
				adminFacade.createCustomer(customer);
				return new Gson().toJson(customer);
			} catch (CustomerAlreadyExistsException e) {
				System.out.println("This customer already exists !");
			}
			return null;
		}
		
		
		// REMOVE a customer
		@DELETE
		@Path("/removeCustomer")
		@Produces(MediaType.APPLICATION_JSON)
		public void removeCustomer (@QueryParam("customerId") long id) {
			
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
		
		@PUT
		@Path("/updateCustomer")
		@Produces(MediaType.APPLICATION_JSON)
		public void updateCustomer(@QueryParam("customerId") long id, @QueryParam("password") String password) {
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
		
		@GET
		@Path("/getCustomer")
		@Produces(MediaType.APPLICATION_JSON)
		public String getCustomer(@QueryParam("customerId") long id) {
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
		
		@GET
		@Path("/getAllCustomers")
		@Produces(MediaType.APPLICATION_JSON)
		public String getAllCustomers() {
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
}
