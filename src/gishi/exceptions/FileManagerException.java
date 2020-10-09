package gishi.exceptions;

@SuppressWarnings("serial")
public class FileManagerException extends RuntimeException{

	private String message;
	
	public FileManagerException() {
		this.message = "";
	}
	public FileManagerException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}
}
