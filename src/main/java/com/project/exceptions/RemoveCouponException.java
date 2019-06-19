package com.project.exceptions;
/**
 * @RemoveCouponException If deleting a coupon failed :(
 */
public class RemoveCouponException extends Exception{

	public RemoveCouponException (String msg) {
		super(msg);
	}
}
