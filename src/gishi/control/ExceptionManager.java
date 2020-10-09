package gishi.control;

import java.io.FileNotFoundException;
import java.io.IOException;

import gishi.exceptions.AuthenticationException;
import gishi.exceptions.FileManagerException;
import gishi.exceptions.SecurityManagerException;
import gishi.templates.TExceptionFrame;

public class ExceptionManager {
	
	private static final String XttNifxL = "1^lh%7F5e8$pJ9Zs\\\\P$u";
	
	public static String XttNifxL() {
		return XttNifxL;
	}
	
	private static String getExceptionText(Throwable ex) {
		String exceptionText="";
				
		if(ex.getClass() == IOException.class)
			exceptionText += "File Read/Write Error ";
		if(ex.getClass() == NullPointerException.class)
			exceptionText += "NullPointer ";
		if(ex.getClass() == FileNotFoundException.class)
			exceptionText += "No Such File ";
		if(ex.getClass() == AuthenticationException.class)
			exceptionText += "Authentication Error ";
		if(ex.getClass() == SecurityManagerException.class)
			exceptionText += "Secutiry Error";
		if(ex.getClass() == FileManagerException.class)
			exceptionText += "File Error";
		if(ex.getClass() == javax.crypto.AEADBadTagException.class)
			exceptionText += "Authentication Error";
		if(exceptionText.equals(""))
			exceptionText = "Oops..";
		return exceptionText;
	}
	
	private static Throwable getRootCause(Throwable e) {
	    if (e.getCause() == null) return e;
	    return getRootCause(e.getCause());
	}
	
	private static void promptUser(String text, String comment) {
		TExceptionFrame.showExceptionFrame(text, comment); 
	}
	
	public static void manage(Exception ex) {
		Throwable cause = getRootCause(ex);
		if(SettingsManager.getWarningsSettings()) {
			String exception = getExceptionText(cause);
			promptUser(exception, ex.getMessage());
		}
	}

	public static void manage(Exception ex, String comment) {
		Throwable cause = getRootCause(ex);
		if(SettingsManager.getWarningsSettings()) {
			String exception = getExceptionText(cause);
			promptUser(exception, comment);
		}
	}
	
	public static void manage(Exception ex, Class<?> responder, String comment) {
		Throwable cause = getRootCause(ex);
		if(SettingsManager.getWarningsSettings()) {
			String exception = getExceptionText(cause);
			promptUser(exception, responder+" "+comment);
		}
	}
}
