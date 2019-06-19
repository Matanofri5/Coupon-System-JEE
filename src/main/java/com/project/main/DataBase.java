package com.project.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DataBase {


	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JBDB;create=true";
	}
	
	private static ConnectionPool pool;
		
	public DataBase() throws Exception{
		pool = ConnectionPool.getInstance();
	}
	
	
	/**
	 * @dropTables this method delete all the tables.
	 * @throws Exception
	 */
	public static void dropTables() throws Exception{
		String sql;
		
		try {
			pool = ConnectionPool.getInstance();
		} catch (Exception e) {
			System.out.println("connection failed");
		}
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			System.out.println("connection failed");
		}
		
		
		sql = "DROP TABLE CUSTOMERCOUPON";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
//			System.out.println("Droped CustomerCoupon table");
		} catch (Exception e) {
			System.out.println("Failed to drop CustomerCoupon table + " + e.getMessage());
		}
		
		
		sql = "DROP TABLE COMPANYCOUPON";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
//			System.out.println("Droped CompanyCoupon table");
		} catch (Exception e) {
			System.out.println("Failed to drop CompanyCoupon table + " + e.getMessage());
		}
		
		
		sql = "DROP TABLE COMPANY";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
//			System.out.println("Droped Company table");
		} catch (Exception e) {
			System.out.println("Failed to drop Company table + " + e.getMessage());
		}
		
		
		sql = "DROP TABLE CUSTOMER";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
//			System.out.println("Droped Customer table");
		} catch (Exception e) {
			System.out.println("Failed to drop Customer table + " + e.getMessage());
		}
		
		
		sql = "DROP TABLE COUPON";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
//			System.out.println("Droped Coupon table");
		} catch (Exception e) {
			System.out.println("Failed to drop Coupon table + " + e.getMessage());
		}
		finally {
			connection.close();
			try {
				pool.returnConnection(connection);
			} catch (Exception e2) {
				System.out.println("connection failed");
			}
		}
		
		
		
	}
	
	/**
	 * @createTables this method create all the tables.
	 * @throws Exception
	 */
	public static void createTables() throws SQLException {
		String sql;
		
		try {
			pool = ConnectionPool.getInstance();
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		Connection connection = null;;
		try {
			connection = pool.getConnection();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		
		// *****************Creating Company table******************
		
			sql = "create table Company ("
					+ "id bigint not null primary key generated always as identity(start with 1, increment by 1), "
					+ "companyName varchar(50) not null, " + "password varchar(50) not null, "
					+ "email varchar(50) not null)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
//			System.out.println("create Company table success");
		} catch (Exception e3) {
			System.out.println("create company didn't succeed" + e3.getMessage());
		}

		// *****************Creating Customer table******************

			sql = "create table Customer ("
					+ "id bigint not null primary key generated always as identity(start with 1, increment by 1), "
					+ "customerName varchar(50) not null, " + "password varchar(50) not null)";
			try {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.executeUpdate();
//				System.out.println("create Customer table success");
			} catch (Exception e4) {
				System.out.println("create customer didn't succeed" + e4.getMessage());
			}

		// *****************Creating Coupon table*********************

			sql = "create table Coupon ("
					+ "id bigint not null primary key generated always as identity(start with 1, increment by 1), "
					+ "title varchar(50) not null, " + "startDate Date not null, " + "endDate Date not null, "
					+ "amount int not null, " + "message varchar(50) not null, " + "price double not null, "
					+ "image varchar(50) not null, " + "type varchar(20) not null)";
			try {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.executeUpdate();
//				System.out.println("create Coupon table success");
			} catch (Exception e4) {
				System.out.println("create coupon didn't succeed" + e4.getMessage());
			}

		// ***************Creating join CustomerCoupon table**********

			sql = "create table CustomerCoupon (" + "customerId bigint not null references Customer(id), "
					+ "couponId bigint not null references Coupon(id), " + "primary key(customerId, couponId))";
			try {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.executeUpdate();
//				System.out.println("create CustomerCoupon table success");
			} catch (Exception e5) {
				System.out.println("create customercoupon didn't succeed" + e5.getMessage());
			}

		// ****************Creating join CompanyCoupon table**********

			sql = "create table CompanyCoupon (" + "companyId bigint not null references Company(id), "
					+ "couponId bigint not null references Coupon(id), " + "primary key(companyId, couponId))";
			try {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.executeUpdate();
//				System.out.println("create CompanyCoupon table success");
			} catch (Exception e6) {
				System.out.println("create companycoupon didn't succeed" + e6.getMessage());
			}
			finally {
				connection.close();
				try {
					pool.returnConnection(connection);
				} catch (Exception e) {
					System.out.println("connection failed");
				}
			}
	}
}
