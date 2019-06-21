package com.project.main;

import java.util.HashSet;
import java.util.Set;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.beans.Customer;
import com.project.facade.AdminFacade;
import com.project.facade.CompanyFacade;
import com.project.facade.CustomerFacade;



public class TheTest {


	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to CouponSystem");

		//Connecting to couponSystem singelton and start dailyTask to delete expired coupons !
		CouponSystem.getInstance();
		
		DataBase.dropTables();
		DataBase.createTables();
		
		
		String name;
		String password;
		ClientType clientType = ClientType.ADMIN;
		Set<Company> companies = new HashSet<Company>();
		Set<Customer> customers = new HashSet<Customer>();
		
		try {
			System.out.println("You have "+ConnectionPool.getInstance().getConnection().getTransactionIsolation()+" available connections");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Companies //
		Company a1 = new Company("Hp", "aa11", "hp@gmail.com");
		Company a2 = new Company("Lg", "bb22", "lg@gmail.com");
		Company a3 = new Company("Sony", "cc33", "sony@gmail.com");
		Company a4 = new Company("CocaCola", "dd44", "cocacola@gamil.com");
		Company a5 = new Company("Samsung", "ee55", "samsung@gamil.com");
		
		// Customers //
		Customer b1 = new Customer("Matan", "123");
		Customer b2 = new Customer("Linoy", "456");
		Customer b3 = new Customer("Dvir", "678");
		Customer b4 = new Customer("Ehud", "910");
		Customer b5 = new Customer("Or", "658");
		
		// Coupons //
		Coupon c1 = new Coupon("Teva pharm", DateUtils.getCurrentDate(), DateUtils.getByMounth(), 55, "Teva", 33.5, "website", CouponType.HEALTH);
		Coupon c2 = new Coupon("Chairs", DateUtils.getCurrentDate(), DateUtils.getByTwoWeeks(), 66, "Hagor", 12.5, "website", CouponType.CAMPING);
		Coupon c3 = new Coupon("Pizza", DateUtils.getCurrentDate(), DateUtils.getByWeek(), 77, "PizzaHut", 41.2, "website", CouponType.FOOD);
		Coupon c4 = new Coupon("Roller coaster", DateUtils.getCurrentDate(), DateUtils.getByWeek(), 99, "LunaPark", 32.4, "website", CouponType.TRAVELING);
		Coupon c5 = new Coupon("Teva pharm", DateUtils.getCurrentDate(), DateUtils.getByTwoWeeks(), 0, "Teva", 4444.7, "website", CouponType.HEALTH);
		Coupon c6 = new Coupon("Sushi", DateUtils.getCurrentDate(), DateUtils.getByTwoMountsAgo(), 55, "roll", 55/5, "website", CouponType.FOOD);

		//***Admin test***//
		
		// Bad login //
//		name = "admin";
//		password = "12354";
//		System.out.println("Checking bad login as Admin: ");
//		try {
//		CouponSystem.login(name, password, clientType);
//		} catch (Exception e) {
//			throw new Exception("Failed to login as Admin, name or password are wrong !");
//		}
		
		// Good login //
		name = "admin";
		password = "1234";
		try {
			CouponSystem.login(name, password, clientType);
			System.out.println("*******************Logged as Admin*******************\n");
			
			AdminFacade adminFacade = new AdminFacade();

						
			// Admin create companies //
			adminFacade.createCompany(a1);
			adminFacade.createCompany(a2);
			adminFacade.createCompany(a3);
			adminFacade.createCompany(a4);
			adminFacade.createCompany(a5);
			
			 System.out.println("**********Companies by Admin**********\n");

			// Get all new companies //
			System.out.println("Show all companies");
			companies = adminFacade.getAllCompanys();
			System.out.println(companies + "\n");
			
			// Admin update company //
			adminFacade.updateCompany(a1, "123abc", "blabla");
			System.out.println("Company " + a1.getCompanyName() + " updated !");
			
			// Get company after update //
			adminFacade.getCompany(1);
			System.out.println("This company was updated --> " + a1 + " \n");
			
			// Get all companies after update //
			System.out.println("Show all companies after update and delete");
			companies = adminFacade.getAllCompanys();
			System.out.println(companies + " \n\n");
			
			 System.out.println("**********Customers by Admin**********\n");
	
			
			// Admin create customers //
			adminFacade.createCustomer(b1);
			adminFacade.createCustomer(b2);
			adminFacade.createCustomer(b3);
			adminFacade.createCustomer(b4);
			adminFacade.createCustomer(b5);
			
			// Get all customers //
			System.out.println("Show all customers");
			customers = adminFacade.getAllCustomers();
			System.out.println(customers + "\n");
			
			// Admin update customer //
			adminFacade.updateCustomer(b1, "123456789");
			System.out.println("Customer " + b1.getCustomerName() + " updated !");
			
			// Get customer after update //
			adminFacade.getCustomer(1);
			System.out.println("This customer was updated --> " + b1 + " \n");	
			
			// Get all customer after update and delete //
			System.out.println("Show all customer after update and delete");
			customers = adminFacade.getAllCustomers();
			System.out.println(customers + "\n");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		
		
		
		
		
		//***Company test***//

		// Bad login //
//		name = "Souny";
//		password = "cc33";
//		clientType = ClientType.COMPANY;
//		System.out.println("Checking bad login as Company: ");
//		try {
//			CouponSystem.login(name, password, clientType);
//		} catch (Exception e) {
//			throw new Exception("Failed to login as Company, name or password are wrong !");
//		}

		// Good login //
		name = "Sony";
		password = "cc33";
		clientType = ClientType.COMPANY;

		try {
			CouponSystem.login(name, password, clientType);
			System.out.println("*******************Logged as Company*******************\n");
			
			CompanyFacade companyFacade = new CompanyFacade();
			

			// Company creates coupon and add them to join table//
			companyFacade.createCoupon(a1, c1);
			companyFacade.createCoupon(a1, c2);
			companyFacade.createCoupon(a2, c3);
			companyFacade.createCoupon(a3, c4);
			companyFacade.createCoupon(a5, c5);
			companyFacade.createCoupon(a5, c6);
			System.out.println("Company " + a1.getCompanyName() + " added new coupon : " + c1.getTitle());
			System.out.println("Company " + a1.getCompanyName() + " added new coupon : " + c2.getTitle());
			System.out.println("Company " + a2.getCompanyName() + " added new coupon : " + c3.getTitle());
			System.out.println("Company " + a3.getCompanyName() + " added new coupon : " + c4.getTitle());
			System.out.println("Company " + a5.getCompanyName() + " added new coupon : " + c5.getTitle());
			System.out.println("Company " + a5.getCompanyName() + " added new coupon : " + c6.getTitle() + "\n");

			
			// Company trying to create coupon with the same title //
			System.out.println("Trying to create coupon who has the same Title");
			companyFacade.createCoupon(a1, c5);System.out.println("\n");
			
			// Company update coupon by endDate and double price //
			companyFacade.updateCoupon(c1, DateUtils.getByWeek(), 66.3);
			System.out.println("company " +a1.getCompanyName() + " updated coupon " + c1.getId());
			System.out.println(c1 + "\n");
			
			// Get specific company //
			companyFacade.getCompany(1);
			System.out.println("Get specific company");
			System.out.println(a1 + "\n");
			
			// Get all coupons by specific company //
			System.out.println("Get all purchesd coupons of company "+ a1.getCompanyName());
			System.out.println(companyFacade.getAllCompanyCoupon(a1) + "\n");
			
			// Get coupons of company by couponType //
			System.out.println("Get coupons of company " + a1.getCompanyName() + " by couponType");
			System.out.println(companyFacade.getCouponbyType(a1, CouponType.HEALTH) + "\n");
			
			// Get coupons of company by specific date //
			System.out.println("Get coupons of company " + a1.getCompanyName() + " by specific date");
			System.out.println(companyFacade.getCouponByDate(a1, DateUtils.getByWeek()) + "\n");
			
			// Get coupons of company by specific price //
			System.out.println("Get coupons of company " + a1.getCompanyName() + " by specific price");
			System.out.println(companyFacade.getCouponByPrice(a1, 24.6) + "\n");
			

		} catch (Exception e) {
			throw new Exception("Failed to login as Company, name or password are wrong !");
		}
		
			
			
			
			
		
			//***Customer test***//
			
			// Bad login //
//			name = "Linoy";
//			password = "45677";
//			clientType = ClientType.CUSTOMER;
//			System.out.println("Checking bad login as Customer: ");
//			try {
//			CouponSystem.login(name, password, clientType);
//			} catch (Exception e) {
//				throw new Exception("Failed to login as Customer, name or password are wrong !");
//			}

			// Good login //
			name = "Linoy";
			password = "456";
			clientType = ClientType.CUSTOMER;
			try {
				CouponSystem.login(name, password, clientType);
				System.out.println("*******************Logged as Customer*******************\n");
			
				CustomerFacade customerFacade = new CustomerFacade();
				
				// Customer purchase coupon and add them to join table //
				customerFacade.purchaseCoupon(b1, 1);
				customerFacade.purchaseCoupon(b1, 2);
				customerFacade.purchaseCoupon(b2, 3);
				customerFacade.purchaseCoupon(b3, 4);
				System.out.println("Customer " + b1.getCustomerName() + " purchased new coupon : " + a1.getId());
				System.out.println("Customer " + b1.getCustomerName() + " purchased new coupon : " + a2.getId());
				System.out.println("Customer " + b2.getCustomerName() + " purchased new coupon : " + a3.getId());
				System.out.println("Customer " + b3.getCustomerName() + " purchased new coupon : " + a4.getId() + "\n");

				// Customer trying to purchase coupon who already purchased //
				System.out.println("Trying to purchase coupon who already purchased");
				customerFacade.purchaseCoupon(b1, 1);
				
				// Customer trying to purchase out of stock coupon //
				System.out.println("\nTrying to purchase out of stock coupon");
				customerFacade.purchaseCoupon(b1, 5);
				
				// Customer trying to purchase expiredDate coupon //
				System.out.println("\nTrying to purchase expiredDate coupon");
				customerFacade.purchaseCoupon(b5, 6);
				
				// Get specific customer //
				System.out.println("\n" + customerFacade.getAllCustomerCoupon(b1) + "\n");
				
				// Get customer by couponType //
				System.out.println("Check if customer " + b1.getCustomerName() + " has camping couponType :");
				System.out.println(customerFacade.getCouponbyType(b1, CouponType.CAMPING) + "\n");

				// Get customer by specific price //
				System.out.println("Check if customer " + b2.getCustomerName() + " has coupon by specific price :");
				System.out.println(customerFacade.getCouponByPrice(b2, 55.2));
				System.out.println("\n\n");
				
			} catch (Exception e) {
				throw new Exception("Failed to login as Customer, name or password are wrong !");
			}
				
				
				
				//  Login as Admin to delete company and customer //
				
				
				name = "admin";
				password = "1234";
				clientType =ClientType.ADMIN;
				try {
					CouponSystem.login(name, password, clientType);
					System.out.println("*******************Logged as Admin*******************\n");
					
					AdminFacade admin = new AdminFacade();
					
					// Admin delete company //
					// When delete company, also delete all coupons related to this company, and all coupons that customer purchased //
					admin.removeCompany(a1);
					System.out.println("Admin removed company " + a1.getCompanyName() + "!");
					System.out.println(admin.getAllCompanys() + "\n");
					
					// Admin delete customer //
					// When delete customer, also delete all the coupons that this customer purchased //
					admin.removeCustomer(b2);
					System.out.println("Admin removed customer " + b2.getCustomerName() + "!");
					System.out.println(admin.getAllCustomers() + "\n");

				}catch (Exception e) {
					throw new Exception("Failed to login as Admin, name or password are wrong !");
				}
		
		
				//  Login as Company to delete coupon //
				
				
				name = "Sony";
				password = "cc33";
				clientType = ClientType.COMPANY;
				try {
					CouponSystem.login(name, password, clientType);
					System.out.println("*******************Logged as Company*******************\n");
					
					CompanyFacade company = new CompanyFacade();
					
					// Company delete coupon //
					// Company delete coupon, also delete all coupons that customer purchase that related to this id //
					company.removeCouponById(4);
					System.out.println("Company delete coupon " + c4.getTitle());
				

				}catch (Exception e) {
					throw new Exception("Failed to login as company, name or password are wrong !");
				}
		
	
	}

}
