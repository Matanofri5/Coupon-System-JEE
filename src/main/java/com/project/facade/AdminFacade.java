package com.project.facade;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.project.beans.Company;
import com.project.beans.Coupon;
import com.project.beans.Customer;
import com.project.dao.CompanyDAO;
import com.project.dao.CouponDAO;
import com.project.dao.CustomerDAO;
import com.project.dbdao.CompanyDBDAO;
import com.project.dbdao.CouponDBDAO;
import com.project.dbdao.CustomerDBDAO;
import com.project.exceptions.CompanyAlreadyExistsException;
import com.project.exceptions.CustomerAlreadyExistsException;
import com.project.main.ClientType;

/**
 * @author Linoy & Matan
 * @Description: Admin Facade- Administrator permissions when Login.
 */
public class AdminFacade implements CouponClientFacade {

	/**
	 * Data Members
	 */
	private CouponDAO couponDAO;
	private CompanyDAO companyDAO;
	private CustomerDAO customerDAO;
	private boolean successLogin = true;
	

//	/**
//	 * @throws Exception 
//	 * @Full CTOR
//	 */
//	public AdminFacade(CompanyDAO companyDAO, CustomerDAO customerDAO) throws Exception {
//		this.companyDAO = new CompanyDBDAO();
//		this.customerDAO = new CustomerDBDAO();
//	}
	
	/**
	 * @throws Exception 
	 * @Empty CTOR
	 */
	public AdminFacade() throws Exception {
		this.couponDAO = new CouponDBDAO();
		this.companyDAO = new CompanyDBDAO();
		this.customerDAO = new CustomerDBDAO();
	}

	/**
	 * this method check password and name of admin, if true return admin login.
	 * @param name
	 * @param password
	 * @param Type
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
			return null;
	}

	/**
	 * this method Add a new company, and check if this company Name already exist.
	 * @param company
	 * @throws Exception
	 */
	public void createCompany(Company company) throws Exception {
		try {
			Set<Company> companies = companyDAO.getAllCompanys();
			Iterator<Company> i = companies.iterator();
			while (i.hasNext()) {
				Company current = i.next();
				if (company.getCompanyName().equals(current.getCompanyName())) {
					throw new CompanyAlreadyExistsException("This Company already exist !");
				}
			}
			if (!i.hasNext()) {
				companyDAO.insertCompany(company);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method remove company by companyId from company table, and remove the
	 * same company that Related to coupon and customer, In all tables.
	 * @param company
	 * @throws Exception
	 */
	public void removeCompany(Company company) throws Exception {
		try {
		Set<Coupon> coupons = new HashSet<Coupon>();
		coupons = companyDAO.getCompanyCoupons(company.getId());
		Iterator<Coupon> itreator = coupons.iterator();
		
		while (itreator.hasNext()) {
				Coupon coupon = new Coupon();
				coupon = itreator.next();
				companyDAO.removeCouponFromCompany(coupon.getId());
		}
		companyDAO.removeCompany(company);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method update Company exist in company table, without the company Name.
	 * @param Company
	 * @param password
	 * @param email
	 * @throws Exception
	 */	
	public void updateCompany (Company company, String newPassword, String newEmail) throws Exception {
		company.setPassword(newPassword);
		company.setEmail(newEmail);
		companyDAO.updateCompany(company);
	}

	/**
	 * This method get company by id and return 1 object of company.
	 * @param id
	 * @return company
	 * @throws Exception
	 */
	public Company getCompany(long id) throws Exception {
		return companyDAO.getCompany(id);
	}
	
	/**
	 * This method get all companys return list of all the companys.
	 * @return
	 * @throws Exception
	 */
	public Set<Company> getAllCompanys() throws Exception {
		return companyDAO.getAllCompanys();
	}
	
	/**
	 * this method Add a new customer, and check if this customer Name already exist.
	 * @param customer
	 * @throws Exception
	 */
	public void createCustomer(Customer customer) throws Exception {
		try {
			Set<Customer> customers = customerDAO.getAllCustomer();
			Iterator<Customer> i = customers.iterator();

			while (i.hasNext()) {
				Customer current = i.next();
				if (customer.getCustomerName().equals(current.getCustomerName())) {
					throw new CustomerAlreadyExistsException("This customer already exists !");
				}
			}
			if (!i.hasNext()) {
				customerDAO.insertCustomer(customer);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method remove customer by customerId from customer table, and remove the
	 * same customer that Related to coupon, In customer coupon table.
	 * @param customerId
	 * @param couponId
	 * @throws Exception
	 */
	public void removeCustomer(Customer customer) throws Exception {
		Set<Coupon> coupons2 = new HashSet<Coupon>();
		coupons2 = customerDAO.getAllCustomerCoupons(customer.getId());
		Iterator<Coupon> itreator = coupons2.iterator();
		
		while (itreator.hasNext()) {
				Coupon coupon2 = new Coupon();
				coupon2 = itreator.next();
				customerDAO.removeCouponFromCustomerCoupon(coupon2.getId());
		}
		customerDAO.removeCustomer(customer);
	}

	/**
	 * This method update customer exist in customer table, without the customer Name.
	 * @param customer
	 * @throws Exception
	 */
	public void updateCustomer(Customer customer, String newPassword) throws Exception {
		customer.setPassword(newPassword);
		customerDAO.updateCustomer(customer);
	}

	/**
	 * This method get customer by id and return 1 object of customer.
	 * @param id
	 * @return customer
	 * @throws Exception
	 */
	public Customer getCustomer(long id) throws Exception {
		return customerDAO.getCustomer(id);
	}
	
	/**
	 * This method get all customers return list of all the customers.
	 * @return
	 * @throws Exception
	 */
	public Set<Customer> getAllCustomers() throws Exception {
		return customerDAO.getAllCustomer();
	}
	
	/**
	 * This method get all the coupons on DataBase
	 * @return
	 * @throws Exception
	 */
	
	public Set<Coupon> getAllCouponsFromDB() throws Exception{
		return couponDAO.getAllCoupons();
	}
}
