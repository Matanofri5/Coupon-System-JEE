package com.project.main;

import java.sql.Connection;

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
			if (name=="admin" && password=="1234") {
			couponClientFacade = new AdminFacade();
			}
			break;
		case COMPANY:
			if (clientType == ClientType.COMPANY) {
				CompanyDAO company = new CompanyDBDAO();
				boolean loginSuccess=company.login(name, password);
				if (loginSuccess) {
					couponClientFacade = new CompanyFacade();
				}
			}
			break;
		case CUSTOMER:
			if (clientType == ClientType.CUSTOMER) {
				CustomerDAO customer = new CustomerDBDAO();
				boolean loginsuccess=customer.login(name, password);
				if (loginsuccess) {
					couponClientFacade = new CustomerFacade();
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
