package com.project.exceptions;
/**
 * @LoginException if The login to the system failed :(
 */
public class LoginException extends Exception{
	
	public LoginException (String msg) {
		super(msg);
	}
}
