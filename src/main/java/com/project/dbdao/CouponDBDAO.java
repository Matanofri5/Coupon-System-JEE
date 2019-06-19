package com.project.dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import com.project.beans.Coupon;
import com.project.beans.CouponType;
import com.project.dao.CouponDAO;
import com.project.main.ConnectionPool;

/**
 * @Author - Linoy & Matan
 * @Description: In this class we have to implement all the method in CouponDAO
 * every method getting connection to DB and close when finished, and run an SQL
 * Query by prepareStatement
 */
public class CouponDBDAO implements CouponDAO{

	/**
	 * Data Members
	 */
	private ConnectionPool connectionPool;

	/**
	 * @throws Exception 
	 * @Empty CTOR
	 */
	public CouponDBDAO() {
			try {
				this.connectionPool = ConnectionPool.getInstance();
			} catch (Exception e) {
				System.out.println("connection pool faild :(");
				e.printStackTrace();
			}
		
	}
	
	/**
	 * @insert
	 * this method Receives data about a new Coupon, And creates it in a table of Coupons.
	 *  @param Coupon object
	 *  @throws Exception
	 */
	@Override
	public void insertCoupon(Coupon coupon) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		String sql = "INSERT INTO Coupon (title,startDate,endDate,amount,message,price,image,type)  VALUES(?,?,?,?,?,?,?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, (Date) coupon.getStartDate());
			pstmt.setDate(3, (Date) coupon.getEndDate());
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getMessage());
			pstmt.setDouble(6, coupon.getPrice());
			pstmt.setString(7, coupon.getImage());
			pstmt.setString(8, coupon.getType().name());
			pstmt.executeUpdate();

//			System.out.println("Coupon insert success :D  " + coupon.toString());
		} catch (SQLException e) {
			System.err.println("Coupon insert failed :( ");
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
	 * @remove
	 * this method delete 1 object of Coupon by Coupon id, from Coupons table.
	 *  @param long id
	 *  @throws Exception
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		String sql = "DELETE FROM Coupon WHERE id=?";
		try (PreparedStatement pstm1 = connection.prepareStatement(sql);) {
			connection.setAutoCommit(false);
			pstm1.setLong(1, coupon.getId());
			pstm1.executeUpdate();
			connection.commit();
//			System.out.println("remove Coupon success :D ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.err.println("remove Coupon failed :( ");
				throw new Exception("Database error");
			}
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
	 * @update
	 * this method update 1 object of Coupon, from Coupons table.
	 *  @param Coupon object
	 *  @throws Exception
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		try {
			String sql ="UPDATE Coupon SET TITLE=?, STARTDATE=?, ENDDATE=?, AMOUNT=?, MESSAGE=?, PRICE=?, IMAGE=?, TYPE=? WHERE ID=?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2,(Date) coupon.getStartDate());
			pstmt.setDate(3,(Date) coupon.getEndDate());
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getMessage());
			pstmt.setDouble(6, coupon.getPrice());
			pstmt.setString(7, coupon.getImage());
			pstmt.setString(8, coupon.getType().toString());
			pstmt.setLong(9, coupon.getId());

			
			pstmt.executeUpdate();
			pstmt.close();
//			System.out.println("Coupon " + coupon.getId()+ " updated succesfully");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception("update Coupon failed :( ");
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
	 * @get1
	 * this method get and print 1 object of Coupon by Coupon id, from Coupons table.
	 *  @param long id
	 *  @return Coupon object
	 *  @throws Exception
	 */
	@Override
	public Coupon getCoupon(long id) throws Exception {
		Connection connection = null;
		Coupon coupon = new Coupon();
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}

		try (Statement stm = connection.createStatement()){
			String sql = "SELECT * FROM Coupon WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
			coupon.setId(rs.getLong(1));
			coupon.setTitle(rs.getString(2));
			coupon.setStartDate((Date) rs.getDate(3));
			coupon.setEndDate((Date) rs.getDate(4));
			coupon.setAmount(rs.getInt(5));
			coupon.setMessage(rs.getString(6));
			coupon.setPrice(rs.getDouble(7));
			coupon.setImage(rs.getString(8));
			CouponType type = CouponType.valueOf(rs.getString(9));
			coupon.setType(type);
			}
		} catch (SQLException e) {
			System.err.println("Get coupon failed :(");
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
		return coupon;
	}

	/**
	 * @getAll
	 * this method get all and print objects of coupons, from coupon table.
	 *  @return coupon list object
	 *  @throws Exception
	 */
	@Override
	public Set<Coupon> getAllCoupons() throws Exception {
		Connection connection = null;
		Coupon coupon;
		Set<Coupon> coupons = new HashSet<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		java.sql.Statement stm = null;
		try {
			stm = connection.createStatement();
			String sql = "SELECT * FROM Coupon";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				coupon = new Coupon();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setEndDate((Date) rs.getDate(3));
				coupon.setStartDate((Date) rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setMessage(rs.getString(6));
				coupon.setPrice(rs.getDouble(7));
				coupon.setImage(rs.getString(8));
				CouponType type = CouponType.valueOf(rs.getString(9));
				coupon.setType(type);
				coupons.add(coupon);
			}
		}catch (Exception e) {
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
	return coupons;
	}
	
	/**
	 * @getAll
	 * this method get all and print objects of coupons, from coupon table, only by Type
	 * @param CouponType
	 *  @return coupon list object
	 *  @throws Exception
	 */
	@Override
	public Set<Coupon> getAllCouponsByType(CouponType couponType) throws Exception {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
		} catch (Exception e) {
			throw new Exception("connection pool faild :(");
		}
		Set<Coupon> CouponByType = new HashSet<Coupon>();
		
			try {
				String sql = "SELECT * FROM coupon WHERE type=?";
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, couponType.toString());
				ResultSet rs = pstmt.executeQuery();
				Coupon coupon = null;
				while (rs.next()) {
					coupon = new Coupon();
					coupon.setId(rs.getLong(1));
					coupon.setTitle(rs.getString(2));
					coupon.setStartDate(rs.getDate(3));
					coupon.setEndDate(rs.getDate(4));
					coupon.setAmount(rs.getInt(5));
					coupon.setMessage(rs.getString(6));
					coupon.setPrice(rs.getDouble(7));
					coupon.setImage(rs.getString(8));
					coupon.setType(CouponType.valueOf(rs.getString(9)));
					
					CouponByType.add(coupon);
				}
				pstmt.close();
				}catch (Exception e) {
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
			return CouponByType;
			}
}
