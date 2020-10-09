package gishi.exceptions;

@SuppressWarnings("serial")
public class AuthenticationException extends Exception{
	
	private String message;
	
	public AuthenticationException() {
		this.message = "";
	}
	public AuthenticationException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}
}
