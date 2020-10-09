package gishi.exceptions;

@SuppressWarnings("serial")
public class PasswordManagerException extends RuntimeException{

	private String message;
	
	public PasswordManagerException() {
		
	}
	public PasswordManagerException(String message) {
		this.message = message;
		
	}
	public String getMessage() {
		return this.message;
	}
}
