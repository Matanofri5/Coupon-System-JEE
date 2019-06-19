package com.project.exceptions;
/**
 * @CompanyAlreadyExistsException if The name of the company already exists
 */
public class CompanyAlreadyExistsException extends Exception{
	
	public CompanyAlreadyExistsException (String msg) {
		super(msg);
	}
}
