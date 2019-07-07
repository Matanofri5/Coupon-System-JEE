package com.project.main;

import java.sql.Connection;
import java.util.Iterator;
import java.util.Set;

import com.project.beans.Company;
import com.project.beans.Customer;
import com.project.dao.CompanyDAO;
import com.project.dao.CustomerDAO;
import com.project.dbdao.CompanyDBDAO;
import com.project.dbdao.CustomerDBDAO;
import com.project.exceptions.LoginException;
import com.project.facade.AdminFacade;
import com.project.facade.CompanyFacade;
import com.project.facade.CouponClientFacade;
import com.project.facade.CustomerFacade;




/**
 * @author Linoy & Matan
 * Singleton class coupon system
 * When the system goes up- start the thread that running every 24 hours.
 * When the system goes down- stop the thread.
 * this class knows which facade return by login details.
 */
public class CouponSystem {

	private static CouponSystem instance;
	public DailyTask dailyTask;
	public Thread thread;
	public Connection connection;
	
	private static final int DAY = 1000 * 3600 * 24;
	private static final int SLEEPTIME = 1 * DAY;

	/**
	 * Private CTOR (Singleton)
	 */
	private CouponSystem() throws Exception {
		// Activate the daily Coupons Deletion Demon (Thread)
		dailyTask = new DailyTask(SLEEPTIME);
		thread = new Thread(dailyTask);
		thread.start();

	}

	/**
	 * @getInstance method - SINGLETON
	 * @return instance
	 */
	public static CouponSystem getInstance() throws Exception {
		if (instance == null)
			instance = new CouponSystem();
		return instance;
	}

	/**
	 * this method login enable access to the system	
	 * @param name, password, clientType
	 * @return facade
	 * @throws Exception, LoginException
	 */
	public static CouponClientFacade login(String name, String password, ClientType clientType) throws Exception, LoginException {

		CouponClientFacade couponClientFacade = null;

		switch (clientType) {
		case ADMIN:
			if (name.equals("admin") && password.equals("1234")) {
			AdminFacade adminFacade = new AdminFacade();
			return adminFacade;
			}
			break;
			
		case COMPANY:
			
			CompanyDAO companyDAO = new CompanyDBDAO();

			Set<Company>companies = companyDAO.getAllCompanys();
			Iterator<Company> i = companies.iterator();
			
			while (i.hasNext()) {
				Company current = i.next();
				if (current.getCompanyName().equals(name) && current.getPassword().equals(password)) {
					CompanyFacade companyFacade = new CompanyFacade(current);
					return companyFacade;
				}else if (!i.hasNext()) {
					throw new LoginException("Login Falied! Invalid User or Password!");
				}
			}

			break;
		case CUSTOMER:
			
			CustomerDAO customerDAO = new CustomerDBDAO();
		
			Set<Customer>customers = customerDAO.getAllCustomer();
			Iterator<Customer> c = customers.iterator();
			
			while (c.hasNext()) {
				Customer current2 = c.next();
				if (current2.getCustomerName().equals(name) && current2.getPassword().equals(password)) {
					CustomerFacade customerFacade = new CustomerFacade(current2);
					return customerFacade;
				}else if (!c.hasNext()) {
					throw new LoginException("Login Falied! Invalid User or Password!");
				}
			}
			
			break;
		default:
			throw new LoginException("Login Falied! Invalid User or Password!");
		}
		if (couponClientFacade == null) {
			throw new LoginException("Login Falied! Invalid User or Password!");
		}else {
			return couponClientFacade;
		}
		}
	
	/**
	 * Shutdown the system. close all the connectionPool and thread.
	 **/
	public void shutdown() throws Exception {

		try {
			ConnectionPool connectionPool = ConnectionPool.getInstance();
			try {
			connectionPool.closeAllConnections(connection);
			}catch (Exception e) {
				System.out.println("Failed to get connection");
			}
		} catch (Exception e) {
			throw new Exception("ERROR! Properly Shut Down Application Failed!");
		}
		dailyTask.stopTask();
	}
}
