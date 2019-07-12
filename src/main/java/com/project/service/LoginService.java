//package com.project.service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import com.project.facade.CouponClientFacade;
//import com.project.main.ClientType;
//import com.project.main.CouponSystem;
//
//@Path("loginPage")
//public class LoginService {
//	@Context
//	private HttpServletRequest request;
//	@Context
//	private HttpServletResponse response;
//	
//	private static CouponSystem system;
//	
//	@POST
//	@Path("login")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response login(String login) {
//		
//		try {
//			system = CouponSystem.getInstance();
//		} catch (Exception e) {
//			System.out.println("Failed to connect to db, Failed to load system");
//			System.exit(1);
//		}
//		
//		HttpSession session = request.getSession(false);
//
//		if (session != null) {
//
//			session.invalidate(); // killing the session if exist
//		}
//
//		session = request.getSession(true); // create a new session for a new
//											// client
//		System.out.println(session.getId() +" * "+ session.getMaxInactiveInterval());
//		// getting the data from the Login HTML form
//		String name = request.getParameter("name");
//		String password = request.getParameter("pass");
//		String clientType1 = request.getParameter("type");
//		ClientType clientType = ClientType.valueOf(clientType1); // convert String to
//															// ENUM
//
//		try {
//			CouponClientFacade facade = system.login(name, password, clientType);
//			System.out.println("loginServlet: request = " + request); // for
//																		// debug
//			System.out.println("loginServlet: response = " + response); // for
//																		// debug
//
//			if (facade != null) {
//				// updating the session with the login facade
//				session.setAttribute("facade", facade);
//				// dispatcher to the right Page according to the Client Type
//				switch (clientType) {
//				case ADMIN:
//					request.getRequestDispatcher("admin.html").forward(request, response);
//					break;
//
//				case COMPANY:
//					// updating the session with the logged in company
//					// Company company =
//					// ((CompanyFacade)facade).getLoginCompany();
//					// session.setAttribute("company", company);
//					request.getRequestDispatcher("company.html").forward(request, response);
//					break;
//
//				case CUSTOMER:
//					// updating the session with the logged in customer
//					// Customer customer =
//					// ((CustomerFacade)facade).getLoginCustomer();
//					// session.setAttribute("customer", customer);
//					request.getRequestDispatcher("customer.html").forward(request, response);
//					break;
//
//				default:
//					break;
//				}
//			}
//
//			else {
//				// return to the Login HTML form if the user name or password
//				// are incorrect
//				// response.getWriter().print("The UserName or the Password are
//				// incorrect! please try again");
//				response.sendRedirect("login.html");
//			}
//
//		}
//
//		// LoginException
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	}
//
