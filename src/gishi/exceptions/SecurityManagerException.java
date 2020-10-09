package gishi.exceptions;

import gishi.control.ExceptionManager;

@SuppressWarnings("serial")
public class SecurityManagerException extends RuntimeException{
	
	private String message;
	
	public SecurityManagerException() {
		
		ExceptionManager.manage(this);
	}
	public SecurityManagerException(String string) {
		this.message = string;
	}
	
	public String getMessage() {
		return this.message;
	}
}
