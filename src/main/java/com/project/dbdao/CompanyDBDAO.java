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

import com.project.beans.Company;
import com.project.beans.CompanyCoupon;
import com.project.beans.Coupon;
import com.project.dao.CompanyDAO;
import com.project.exceptions.LoginException;
import com.project.exceptions.RemoveCouponException;
import com.project.main.ConnectionPool;



/**
 * @Author - Linoy & Matan
 * @Description: In this class we have to implement all the method in CompanyDao
 *               every method getting connection to DB and close when finished,
 *               and run an SQL Query by prepareStatement
 */
public class CompanyDBDAO implements CompanyDAO{


	/**
	 * Data Members
	 */
	private ConnectionPool connectionPool;

	/**
	 * @throws Exception
	 * @Empty CTOR
	 */
	public CompanyDBDAO() {
			try {
				this.connectionPool = ConnectionPool.getInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * @insert this method Receives data about a new company, And creates it in a
	 *         table of companies.
	 * @param company
	 *            object
	 * @throws Exception
	 */
	@Override
	public void insertCompany(Company company) throws Exception {
		Connection connection  = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		String sql = "INSERT INTO Company (companyName,password,email)  VALUES(?,?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setString(1, company.getCompanyName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());

			pstmt.executeUpdate();
//			 System.out.println("Company insert success :D " + company.toString());
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
			System.err.println("Company insert failed :(");
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
	 * this method adds a coupon to company table and to companyCoupon table,
	 * but! - A different name. If the name exists - throws exception.
	 * @param coupon
	 * @throws Exception
	 */
	@Override
	public void companyCreateCoupon(Company company, Coupon coupon) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		try {
			String sql = "INSERT INTO CompanyCoupon (companyId,couponId)  VALUES(?,?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, company.getId());
			pstmt.setLong(2, coupon.getId());

			pstmt.executeUpdate();
			pstmt.close();

			// System.out.println("Company create new coupon :D ");
		} catch (SQLException e) {
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
	 * This method remove company by companyId from company table, and remove the
	 * same company that Related to coupon and customer, In all tables.
	 * @param company
	 * @throws Exception
	 */
	@Override
	public void removeCompany(Company company) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		try {
			String sql = "DELETE FROM Company WHERE id= ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, company.getId());
			pstmt.executeUpdate();
			pstmt.close();
			// System.out.println("Company " + company.getId() + " remove success :D ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
				System.out.println(e1.getMessage());
				throw new Exception("Database error");
			}
			e.printStackTrace();
			System.err.println("Company remove failed :( ");
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
	 * @removeCouponFromCompany this method delete coupon of Company by DELETE query from two tables.
	 * @param long couponId
	 * @throws Exception
	 */
	@Override
	public void removeCouponFromCompany(long couponId) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		try {

			// query to delete coupon from companyCoupon table
			String sql1 = "DELETE FROM CompanyCoupon WHERE couponId=?";
			PreparedStatement pstmt = connection.prepareStatement(sql1);
			pstmt.setLong(1, couponId);
			pstmt.executeUpdate();
			pstmt.close();

				// query to delete coupon from customerCoupon table
				String sql2 = "DELETE FROM CustomerCoupon WHERE couponId=?";
				PreparedStatement pstmt2 = connection.prepareStatement(sql2);
				pstmt2.setLong(1, couponId);
				pstmt2.executeUpdate();
				pstmt2.close();
			
					// query to delete coupon from Coupon table
					String sql3 = "DELETE FROM Coupon WHERE id=?";
					PreparedStatement pstmt3 = connection.prepareStatement(sql3);
					pstmt3.setLong(1, couponId);
					pstmt3.executeUpdate();
					pstmt3.close();

			// System.out.println("you deleted coupon from company successfully");
		} catch (Exception e) {
			throw new RemoveCouponException("failed to remove coupon from company");
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
	 * @update this method update 1 object of company, from companies table.
	 * @param company object
	 * @throws Exception
	 */
	@Override
	public void updateCompany(Company company) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		try {
			String sql = "UPDATE Company SET password=?, email=? WHERE companyName=?";
			PreparedStatement pstm = connection.prepareStatement(sql);

			pstm.setString(1, company.getPassword());
			pstm.setString(2, company.getEmail());
			pstm.setString(3, company.getCompanyName());
			pstm.executeUpdate();
			pstm.close();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new Exception("Company update failed :( ");
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
	 * @get1 this method get and print 1 object of company by company id, from companies table.
	 * @param long id
	 * @return company object
	 * @throws Exception
	 */
	@Override
	public Company getCompany(long id) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		Company company = new Company();
		try (Statement stm = connection.createStatement()) {
			String sql = "SELECT * FROM Company WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				company.setId(rs.getLong(1));
				company.setCompanyName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));
			}

		} catch (SQLException e) {
			System.err.println("Get company failed :(");
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
          return company;
	}

	/**
	 * @getAll this method get all and print objects of company, from companies table.
	 * @return list of company object
	 * @throws Exception
	 */
	@Override
	public Set<Company> getAllCompanys() throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		Set<Company> set = new HashSet<Company>();
		String sql = "SELECT * FROM Company";

		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long id = rs.getLong(1);
				String companyName = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				set.add(new Company(companyName, password, email));
				// System.out.println("Get all company success :D ");
			}
		} catch (SQLException e) {
			System.err.println(e);
			throw new Exception("Get all company failed :( ");
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
	 * @getAll CompanyCoupon by company id this method get and print objects of coupons by
	 * company id, from CompanyCoupon table.
	 * @param long id
	 * @return list of coupon id
	 * @throws Exception
	 */
	@Override
	public List<Long> getAllCompanyCoupons(long companyId) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		List<Long> coupons = new ArrayList<Long>();
		String sql = "SELECT * FROM CompanyCoupon WHERE COMPANYID=" + companyId;
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long couponId = rs.getLong(2);

				coupons.add(couponId);

			}
		} catch (SQLException e) {
			System.err.println("Get coupons by company failed :( ");
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
	 * @getAll this method get and print list of coupons object by company id, from companyCoupon table.
	 * @param long id
	 * @return list of coupons id
	 * @throws Exception
	 */
	@Override
	public List<Long> getCouponId(long companyId) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		List<Long> couponsId = new ArrayList<>();
		String sql = "SELECT * FROM CompanyCoupon WHERE companyId= " + companyId;
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				long couponId = resultSet.getLong(1);
				couponsId.add(companyId);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
		return couponsId;
	}

	/**
	 * @getAll companyCoupon, this method get and print all objects of companyCoupon
	 * from CompanyCoupon table.
	 * @param companyCoupon object
	 * @return list of companyCoupon object
	 * @throws Exception
	 */
	@Override
	public Set<CompanyCoupon> getAllCompanyCoupon() throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		Set<CompanyCoupon> set = new HashSet<>();
		try {
			Statement stmt = connection.createStatement();
			String sql = "SELECT * FROM CompanyCoupon";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				long companyId = rs.getLong(1);
				long couponId = rs.getLong(2);

				set.add(new CompanyCoupon(companyId, couponId));
				// System.out.println("Get all CompanyCoupon success :D ");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.err.println("Get all CompanyCoupon failed :( ");
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
	 * @get1 this method get and print list of coupons object by company id, from companyCoupon table by companyId.
	 * @param long id
	 * @return list of coupon object
	 * @throws Exception
	 */
	@Override
	public Set<Coupon> getCompanyCoupons(long companyId) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		Set<Coupon> coupons = new HashSet<Coupon>();
		CouponDBDAO coupon = new CouponDBDAO();

		try {
			String sql = "SELECT COUPONID FROM CompanyCoupon WHERE COMPANYID=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, companyId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				coupons.add(coupon.getCoupon(rs.getLong("COUPONID")));

			}
		} catch (SQLException e) {
			System.err.println("Get coupons by company failed :( ");
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
	 * this method login by companyUser check companyName and password
	 * @throws Exception
	 * @return boolean, LoginException
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
			String sql = "SELECT * FROM company WHERE COMPANYNAME=? AND PASSWORD=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				loginSuccess = true;
			}

		} catch (Exception e) {
			throw new LoginException("comapny failed to login");
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
