package com.project.main;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Linoy & Matan.
 *
 */
public class DateUtils {


	/**
	 * This method return current date .
	 * @return date
	 */
	public static Date getCurrentDate() {
		LocalDate localDate = LocalDate.now();
		Date date = java.sql.Date.valueOf(localDate);
		return date;
	}

	/**
	 * This method get the expiration date and return the end date .
	 * @return date
	 */
	public static Date getByMounth() {

		LocalDate localDate = LocalDate.now();
		localDate = localDate.plusMonths(1);
		Date date = java.sql.Date.valueOf(localDate);

		return date;
	}
	
	/**
	 * This method get the Local Date and return date before 2 months.
	 * @return date
	 */
	public static Date getByTwoMountsAgo() {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.minusMonths(2);
		Date date = java.sql.Date.valueOf(localDate);
		
		return date;
	}
	
	/**
	 * This method get the Local Date and return date plus 2 weeks from today.
	 * @return date
	 */
	public static Date getByTwoWeeks() {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.plusWeeks(2);
		Date date = java.sql.Date.valueOf(localDate);
		
		return date;
	}
	
	/**
	 * This method get the Local Date and return date plus one week from today.
	 * @return date
	 */
	public static Date getByWeek() {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.plusWeeks(1);
		Date date = java.sql.Date.valueOf(localDate);
		
		return date;
	}

	/**
	 * This method return the driver of the derby JDBC
	 * 
	 */
	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	/**
	 * This method return the DB local host URL and the port
	 * 
	 */
	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JBDB;create=true";
	}

}
