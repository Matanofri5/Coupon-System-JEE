package com.project.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import com.project.beans.User;
import com.project.facade.CouponClientFacade;
import com.project.main.ClientType;
import com.project.main.CouponSystem;


@Path("LoginService")
public class LoginService {


    @Context
    private HttpServletRequest request;
    @Context
    private HttpServletResponse response;

    CouponSystem couponSystem = null;

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String jsonOfUser) throws Exception  {
    	
        Gson gson = new Gson();
        User userFromJson = gson.fromJson(jsonOfUser, User.class);

        // Getting system instance(For debug)
        couponSystem = CouponSystem.getInstance();

        // getting the data from the register form as JSON and parsing to Pojo
        String type = userFromJson.getClientType();
        ClientType clientType = ClientType.valueOf(type);

        // Login to the system (Checking throw DB)
        CouponClientFacade facade = couponSystem.login(userFromJson.getName(), userFromJson.getPassword(), clientType);

        // Checking whether there is a open session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // killing the session if exist
        }
        session = request.getSession(true); // create a new session for a new client



        if (facade != null) {
        	System.out.println("Successfully login to "+ clientType);
            // updating the session with the login facade
            session.setAttribute("facade", facade);

            // response.addCookie(cookie);
            Cookie cookie = new Cookie("Set-Cookie" , "JSESSIONID="+request.getSession().getId()+";path=/RestCouponSystem/; HttpOnly; domain=/localhost; secure=false;" );
            cookie.setComment(type);
            String goodResponse = new Gson().toJson(cookie);
            
            switch (clientType) {
                case ADMIN:
                	System.out.println(type + " JSESSIONID =    " +request.getSession().getId());
                    return Response.status(Response.Status.OK).entity(goodResponse).build();
                    
                case COMPANY:
                	System.out.println(type + " JSESSIONID =    " +request.getSession().getId());
                    return Response.status(Response.Status.OK).entity(goodResponse).build();

                case CUSTOMER:
                	System.out.println(type + " JSESSIONID =    " +request.getSession().getId());
                    return Response.status(Response.Status.OK).entity(goodResponse).build();

            }
        }
        String responseToJson = "Failed to auth, please try again. ";
        String badResponse = new Gson().toJson(responseToJson);
        return Response.status(Response.Status.UNAUTHORIZED).entity(badResponse).build();
   }
}
