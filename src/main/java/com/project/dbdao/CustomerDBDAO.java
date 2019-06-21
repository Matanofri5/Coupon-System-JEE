package com.project.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.project.beans.Coupon;
import com.project.beans.Customer;
import com.project.dao.CustomerDAO;
import com.project.exceptions.LoginException;
import com.project.exceptions.RemoveCouponException;
import com.project.main.ConnectionPool;



/**
 * @Author - Linoy & Matan
 * @Description: In this class we have to implement all the method in
 *               CustomerDAO every method getting connection to DB and close
 *               when finished, and run an SQL Query by prepareStatement
 */
public class CustomerDBDAO implements CustomerDAO {
	/**
	 * Data Members
	 */
	private ConnectionPool connectionPool;

	/**
	 * @throws Exception
	 * @Empty CTOR
	 */
	public CustomerDBDAO() throws Exception {
		try {
			this.connectionPool = ConnectionPool.getInstance();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
	}

	/**
	 * @insert this method Receives data about a new Customer, And creates it in a
	 *         table of Customers.
	 * @param Customer
	 *            object
	 * @throws Exception
	 */
	@Override
	public void insertCustomer(Customer customer) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		String sql = "INSERT INTO Customer (customerName,password)  VALUES(?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setString(1, customer.getCustomerName());
			pstmt.setString(2, customer.getPassword());

			pstmt.executeUpdate();
			// System.out.println("Customer insert success :D " + customer.toString());
		} catch (SQLException e) {
			System.err.println("Customer insert failed :( ");
			throw new Exception(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
	}

	/**
	 * @remove this method delete 1 object of Customer by Customer id, from
	 *         Customers table.
	 * @param long
	 *            id
	 * @throws Exception
	 */
	@Override
	public void removeCustomer(Customer customer) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}

		try {
			String sql = "DELETE FROM Customer WHERE id= ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, customer.getId());
			pstmt.executeUpdate();
			pstmt.close();
			// System.out.println("Customer " + customer.getId() + " remove success :D ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
				throw new Exception("Database error");
			}
			e.printStackTrace();
			System.err.println("Customer remove failed :( ");
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
	}

	/**
	 * This method remove CustomerCoupon by couponId from CustomerCoupon table
	 * @param couponId
	 * @throws Exception
	 */
	@Override
	public void removeCouponFromCustomerCoupon(long couponId) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		try {

			// query to delete coupon from customerCoupon table
			String sql2 = "DELETE FROM CustomerCoupon WHERE couponId=?";
			PreparedStatement pstmt2 = connection.prepareStatement(sql2);
			pstmt2.setLong(1, couponId);
			pstmt2.executeUpdate();
			pstmt2.close();

			// System.out.println("you deleted coupon from customer successfully");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoveCouponException("failed to remove coupon from customer");
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
	}

	/**
	 * @update this method update 1 object of Customer, from Customers table.
	 * @param Customer
	 *            object
	 * @throws Exception
	 */
	@Override
	public void updateCustomer(Customer customer) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		try {
			String sql = "UPDATE Customer SET password=? WHERE customerName=?";
			PreparedStatement pstm = connection.prepareStatement(sql);

			pstm.setString(1, customer.getPassword());
			pstm.setString(2, customer.getCustomerName());
			pstm.executeUpdate();
			pstm.close();
			// System.out.println("updated customer id " + customer.getId() + "
			// successfully");

		} catch (SQLException e) {
			System.err.println("update Customer failed :( ");
			throw new Exception(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
	}

	/**
	 * @get1 this method get and print 1 object of Customer by Customer id, from
	 *       Customers table.
	 * @param long
	 *            id
	 * @return Customer object
	 * @throws Exception
	 */
	@Override
	public Customer getCustomer(long id) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		Customer customer = new Customer();
		try (Statement stm = connection.createStatement()) {
			String sql = "SELECT * FROM Customer WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				customer.setId(rs.getLong(1));
				customer.setCustomerName(rs.getString(2));
				customer.setPassword(rs.getString(3));
				// System.out.println("Get customer success :D ");
			}
		} catch (SQLException e) {
			System.err.println("Get customer failed :(");
			throw new Exception(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
		return customer;
	}

	/**
	 * @getAll this method get all and print objects of Customers, from Customer
	 *         table.
	 * @return Customer list object
	 * @throws Exception
	 */
	@Override
	public Set<Customer> getAllCustomer() throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		Set<Customer> set = new HashSet<>();
		String sql = "SELECT * FROM Customer";
		try (Statement stm = connection.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				long id = rs.getLong(1);
				String customerName = rs.getString(2);
				String password = rs.getString(3);
				set.add(new Customer(customerName, password));
			}
		} catch (SQLException e) {
			System.err.println("Get all customer failed :( ");
			System.err.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
		return set;
	}

	/**
	 * @customerPurchaseCoupon this method purchase a coupon by specific customer.
	 * @throws Exception
	 */
	@Override
	public void customerPurchaseCoupon(Coupon coupon, Customer customer) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		try {
			String sql = "INSERT INTO CustomerCoupon (customerId,couponId)  VALUES(?,?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, customer.getId());
			pstmt.setLong(2, coupon.getId());

			pstmt.executeUpdate();
			pstmt.close();

			// System.out.println("Customer purchase coupon :D ");
		} catch (SQLException e) {
			System.err.println("customer failed to purchase coupon :( ");
			System.err.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
	}

	/**
	 * @getAllCustomerCoupons this method get all objects of coupon by customerId, from CustomerCoupon
	 *         table.
	 * @return coupon list object
	 * @throws Exception
	 */
	@Override
	public Set<Coupon> getAllCustomerCoupons(long customerId) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		Set<Coupon> coupons = new HashSet<Coupon>();
		CouponDBDAO coupon = new CouponDBDAO();

		try {
			String sql = "SELECT COUPONID FROM CustomerCoupon WHERE CUSTOMERID=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				coupons.add(coupon.getCoupon(rs.getLong("COUPONID")));

				// System.out.println("Get coupons by customer success :D ");
			}
		} catch (SQLException e) {
			System.err.println("Get coupons by customer failed :( ");
			throw new Exception(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
		return coupons;
	}
	
	/**
	 * @getCustomerCoupons this method get all objects of CustomerCoupon by customerId, from CustomerCoupon
	 *         table.
	 * @return coupon list long
	 * @throws Exception
	 */
	@Override
	public List<Long> getCustomerCoupons(long customerId) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		List<Long> coupons = new ArrayList<Long>();
		String sql = "SELECT * FROM CustomerCoupon WHERE CUSTOMERID=" + customerId;
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long couponId = rs.getLong(2);

				coupons.add(couponId);

			}
		} catch (SQLException e) {
			System.err.println("Get coupons by customer failed :( ");
			throw new Exception(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
		return coupons;
	}

	/**
	 * this method login by customerUser check customerName and password
	 * @throws Exception, LoginException
	 * @return boolean
	 */
	@Override
	public boolean login(String name, String password) throws Exception, LoginException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}

		boolean loginSuccess = false;

		try {
			String sql = "SELECT * FROM customer WHERE CUSTOMERNAME=? AND PASSWORD=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				loginSuccess = true;
			}

		} catch (Exception e) {
			throw new LoginException("customer failed to login");
		} finally {
			try {
				connection.close();
			} catch (SQLException e2) {
				 System.out.println(e2.getMessage());
			}
			try {
				connectionPool.returnConnection(connection);
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
		return loginSuccess;
	}
}
