package com.project.exceptions;
/**
 * @ErrorConnectingDriverException if The connection to driver failed :(
 */
public class ErrorConnectingDriverException extends Exception{

	  public ErrorConnectingDriverException (String msg) {
	    	super(msg);
	    }
}
