package com.project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import com.project.facade.CustomerFacade;

@Path("/customer")
public class CustomerService {
	
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	private CustomerFacade getFacade() {
		CustomerFacade customerFacade = (CustomerFacade) request.getSession(false).getAttribute("facade");
		return customerFacade;
	}
	

}
