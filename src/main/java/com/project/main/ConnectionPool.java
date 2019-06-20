package com.project.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.project.exceptions.ErrorConnectingDriverException;



/**
 * @author Linoy & Matan
 * Singleton class Connection-Pool
 * cannot add more connection than 10 (MAX),
 * they need to wait, until 1 or more closed the connection.
 *
 */
public class ConnectionPool {


	private static ConnectionPool instance;
	private static int maxConnections = 10;
	private BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(maxConnections);
	
	/**
	 * Private CTOR (Singleton)
	 */
	private ConnectionPool() throws Exception {
		try {
			Class.forName(DataBase.getDriverData());
		} catch (Exception e) {
			throw new ErrorConnectingDriverException("Something faulty with the connection to Driver" + e.getMessage());
		}
		Connection connection;
		try {
			connection = DriverManager.getConnection(DataBase.getDBUrl());
		} catch (SQLException e) {
			throw new ErrorConnectingDriverException("Something faulty with the connection to DB URL");
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ErrorConnectingDriverException("the connection is not closed ");
		}
		while (this.connections.size() < maxConnections) {
			try {
				connection = DriverManager.getConnection(DataBase.getDBUrl());
			} catch (SQLException e) {
				throw new ErrorConnectingDriverException("Something faulty with the connection to Driver");
			}
			this.connections.offer(connection);
		}
	}

	/**
	 * @getInstance method - SINGLETON
	 * @return instance
	 */
	public static ConnectionPool getInstance() throws Exception {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}

	/**
	 * getConnection method Send a connection to the client. Before
	 * sending back a connection it set the autoCommit to true
	 */
	public synchronized Connection getConnection() throws Exception {
		try {
			while (connections.isEmpty()) {
				wait();
				System.out.println("connection pool is empty");
			}
			Connection connection = connections.poll();
			connection.setAutoCommit(true);
			return connection;
		} catch (Exception e) {
			throw new Exception("connection failed" + e.getMessage());
		}
	}

	/**
	 * Methods return connection to Connection pool
	 */
	public synchronized void returnConnection(Connection con) throws Exception {
		try {
			Connection connection = DriverManager.getConnection(DataBase.getDBUrl());
			connections.offer(connection);
			notifyAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
			throw new Exception("mmm.. return connection faild :(");
		}
	}

	/**
	 * Close all Connections
	 */
	public synchronized void closeAllConnections(Connection connection) throws Exception {
		Connection connection2;
		while (this.connections.peek() != null) {
			connection2 = this.connections.poll();
			try {
				connection2.close();
				instance = null;
			} catch (Exception e) {
				throw new Exception("Connections: Close All Connection: Error!");
			}
		}
	}
}
