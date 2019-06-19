package com.project.exceptions;

import com.project.beans.Company;

/**
 * @RemoveCompanyException If deleting a company failed :(
 */
public class RemoveCompanyException extends Exception{

	private Company company;

	public RemoveCompanyException(Company company) {
		this.company = company;
	}

	public String getMessage() {
		return "Faild to remove " + this.company.getCompanyName() + "company";
	}
}
