package com.project.exceptions;
/**
 * @CustomerAlreadyExistsException if The name of the customer already exists
 */
public class CustomerAlreadyExistsException extends Exception{

	public CustomerAlreadyExistsException (String msg) {
		super(msg);
	}
}
