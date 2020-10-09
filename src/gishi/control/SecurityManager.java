package gishi.control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import gishi.exceptions.AuthenticationException;
import gishi.exceptions.SecurityManagerException;
import gishi.util.ByteArray;

public class SecurityManager {
	private static String F5e8$pJ9 = "XttNifxL&wx2jTPm";
	
	private static byte[] gdf0We5sf3029vbas;
	@SuppressWarnings("unused")
	private static String us33freas9oib5t0v;
	
//	private static byte[] ggggggg() {
//		return gdf0We5sf3029vbas;
//	}
	public static void sssssss(byte[] input) {
		gdf0We5sf3029vbas = input;
	}
//	public static String fffffff() {
//		return us33freas9oib5t0v;
//	}
	public static void sssssss(String input) {
		us33freas9oib5t0v = input;
	}
	
	public static boolean authenticate(char[] user, char[] password) throws SecurityManagerException{
		String user_s =String.valueOf(user);
		String pswd_s =String.valueOf(password);
		byte[] base = "".getBytes();
		String line = "";	
		String temp = "";
		try {
			FileManager.initialize(user_s);
			Scanner scanner = new Scanner(FileManager.getAuthenticationFile());	
			if(scanner.hasNextLine()) {
				line = scanner.nextLine();
				base = Base64.getDecoder().decode(line);
				scanner.close();
				temp = SecurityManager.decrypt(base, SecurityManager.generateKey(pswd_s));
			}
		} catch (Exception ex) {
			ExceptionManager.manage(ex);
			return false;
		}
		if(temp.length() > 0)
			if((user_s+pswd_s).equals(temp)) {
				if(SettingsManager.DEBUG)System.out.println("Authentication complete...\n");
				sssssss(SecurityManager.generateKey(pswd_s));
				sssssss(user_s);
				return true;
			}
			else {
				//ExceptionManager.manage(new AuthenticationException(), SecurityManager.class, "Username mismatch");
				if(SettingsManager.DEBUG)ExceptionManager.manage(new AuthenticationException(), "User and Password do not match");
				return false;
			}
		else {
			return false;
		}
	}
	
	public static byte[] generateKey(String input) {
		int keyLenght = 16;
		byte[]byteKey = new byte[keyLenght];
		if(input.length() != 0) {			
			for(int i = 0; i < keyLenght; i++) byteKey[i] = 37;			
			byte[] tempKey = input.getBytes();			
			if(tempKey.length >= keyLenght) {
				for(int i = 0; i < keyLenght; i++) {
					byteKey[i] = tempKey[i];
				}
			}
			else {
				for(int i = 0; i < tempKey.length; i++) {
					byteKey[i] = tempKey[i];
				}
			}
		}
		return byteKey;
	}
	
	public static byte[] generateIV() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] iv = new byte[12];
		secureRandom.nextBytes(iv);
		return iv;
	}
	
	public static String generateToken() {
		byte[] token = new byte[7]; // length is bounded by 7
	    new SecureRandom().nextBytes(token);
	    String generatedString = "";
			generatedString = new String(token);
			if(SettingsManager.DEBUG)System.out.println("generated token: " + generatedString);
		return generatedString;
	}
	
	public static boolean generateAuthenticationFile(char[] user, char[] password) {
		String user_s =String.valueOf(user);
		String pswd_s =String.valueOf(password);
		boolean result = false;
		if(FileManager.verifyMainPath())
	    if(user_s.length() > 0 && pswd_s.length() > 0) {
			try {
		    	BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.getUserDirectory() + "\\authentication"));
				writer.write(encryptPswd(user_s+pswd_s, generateKey(pswd_s), generateIV()));
				writer.write("\n");
				writer.write(encryptPswd(pswd_s, generateKey(F5e8$pJ9 + ExceptionManager.XttNifxL()), generateIV()));
				writer.close();
				result = true;
				//if(SettingsManager.DEBUG)System.out.println("user dir: " + FileManager.getUserDirectory());
				//if(SettingsManager.DEBUG)System.out.println("generated authentication file: " + encryptPswd(user_s+pswd_s, generateKey(pswd_s), generateIV()));
			} catch (Exception ex) {
				//ExceptionManager.manage(ex, "GeneratingAuthenticationFile");
				ExceptionManager.manage(ex, "There was a problem while generating user Authentication File");
			}
	    }
	    else {
	    	if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Generating user authentication file failed due to insuficient input"));
	    	if(SettingsManager.DEBUG)throw new SecurityManagerException("Generating user authentication file failed due to insuficient input");
	    }
		return result;
	}
	
	public static String encryptPswd(String input, byte[] byteKey, byte[] iv) {
		byte[] cipherMessage = null;
		if(input.length() > 0) {
			if(!ByteArray.isEmpty(byteKey)) {
				if(!ByteArray.isEmpty(iv)) {
					try {
						SecretKey secretKey = new SecretKeySpec(byteKey, "AES");
					    final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
					    GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length
					    cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
					    
					    byte[] encrypted = null;
					    encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_16));
					    
					    ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + encrypted.length);
					    byteBuffer.putInt(iv.length);
					    byteBuffer.put(iv);
					    byteBuffer.put(encrypted);
					    cipherMessage = byteBuffer.array();
					    //System.err.println("result: " + new String(cipherMessage, StandardCharsets.UTF_16));
					 }catch(Exception ex) {
						// ExceptionManager.manage(ex, SecurityManager.class ,"Encryption failed");
						 if(SettingsManager.DEBUG)ExceptionManager.manage(ex,"Program could not encrypt data");
					 }
				}
				else {// iv is null
					if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
					if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (IV)");
				}
			}// key is null
			else {
				if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
				if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (KEY)");
			}
		}// input message is null
		else {
			if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
			if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (MESSAGE)");
		}
		return Base64.getEncoder().encodeToString(cipherMessage);
		//return cipherMessage;
	}
	
	public static byte[] encrypt(String input, byte[] byteKey, byte[] iv) {
		byte[] cipherMessage = null;
		if(input.length() > 0) {
			if(!ByteArray.isEmpty(byteKey)) {
				if(!ByteArray.isEmpty(iv)) {
					try {
						SecretKey secretKey = new SecretKeySpec(byteKey, "AES");
					    final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
					    GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length
					    cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
					    
					    byte[] encrypted = null;
					    encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_16));
					    
					    ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + encrypted.length);
					    byteBuffer.putInt(iv.length);
					    byteBuffer.put(iv);
					    byteBuffer.put(encrypted);
					    cipherMessage = byteBuffer.array();
					    //System.err.println("result: " + new String(cipherMessage, StandardCharsets.UTF_16));
					 }catch(Exception ex) {
						 if(SettingsManager.DEBUG) ExceptionManager.manage(ex, SecurityManager.class ,"Encryption failed");
					 }
				}
				else {// iv is null
					if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
					if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (IV)");
				}
			}// key is null
			else {
				if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
				if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (KEY)");
			}
		}// input message is null
		else {
			if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
			if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (MESSAGE)");
		}
		return cipherMessage;
		//return cipherMessage;
	}
	
	public static String decryptPswd(String cipherMessage, byte[] byteKey) {
		byte[] msg = Base64.getDecoder().decode(cipherMessage);
		byte[] plainText = null;
		if(!ByteArray.isEmpty(msg)) {
			if(!ByteArray.isEmpty(byteKey)) {
				try {			
					ByteBuffer byteBuffer = ByteBuffer.wrap(msg);
					int ivLength = byteBuffer.getInt();
					if(ivLength < 12 || ivLength >= 16) { // check input parameter
					    throw new IllegalArgumentException("invalid iv length");
					}
					byte[] iv = new byte[ivLength];
					byteBuffer.get(iv);
					byte[] cipherText = new byte[byteBuffer.remaining()];
					byteBuffer.get(cipherText);
					
					final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
					cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(byteKey, "AES"), new GCMParameterSpec(128, iv));
					
					plainText = cipher.doFinal(cipherText);
				}
				catch(Exception ex) {
					//ExceptionManager.manage(ex, SecurityManager.class , "Password is not working");
					if(SettingsManager.DEBUG)ExceptionManager.manage(ex, "Program could not decrypt data");
					return new String("");
				}
			}
			else {
				if(SettingsManager.DEBUG)	ExceptionManager.manage(new SecurityManagerException("Decryption failed due to insuficient input"));
				if(SettingsManager.DEBUG)throw new SecurityManagerException("Decryption failed due to insuficient input (KEY)");
			}
		}
		else {
			if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Decryption failed due to insuficient input"));
			if(SettingsManager.DEBUG)throw new SecurityManagerException("Decryption failed due to insuficient input (MESSAGE)");
		}
		return new String(plainText, StandardCharsets.UTF_16);
	}
	
	public static String decrypt(byte[] cipherMessage, byte[] byteKey) {
		byte[] plainText = null;
		if(!ByteArray.isEmpty(cipherMessage)) {
			if(!ByteArray.isEmpty(byteKey)) {
				try {			
					ByteBuffer byteBuffer = ByteBuffer.wrap(cipherMessage);
					int ivLength = byteBuffer.getInt();
					if(ivLength < 12 || ivLength >= 16) { // check input parameter
					    throw new IllegalArgumentException("invalid iv length");
					}
					byte[] iv = new byte[ivLength];
					byteBuffer.get(iv);
					byte[] cipherText = new byte[byteBuffer.remaining()];
					byteBuffer.get(cipherText);
					
					final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
					cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(byteKey, "AES"), new GCMParameterSpec(128, iv));
					
					plainText = cipher.doFinal(cipherText);
				}
				catch(Exception ex) {
					//ExceptionManager.manage(ex, SecurityManager.class , "Password is not working");
					//ExceptionManager.manage(ex, "Could not authenticate");
					return new String("");
				}
			}
			else {
				if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Decryption failed due to insuficient input"));
				if(SettingsManager.DEBUG)throw new SecurityManagerException("Decryption failed due to insuficient input (KEY)");
			}
		}
		else {
			if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Decryption failed due to insuficient input"));
			if(SettingsManager.DEBUG)throw new SecurityManagerException("Decryption failed due to insuficient input (MESSAGE)");
		}
		return new String(plainText, StandardCharsets.UTF_16);
	}
	
	public static String encryptField(String input) {
		byte[] cipherMessage = null;
		byte[] iv = generateIV();
		if(input.length() > 0) {
			if(!ByteArray.isEmpty(gdf0We5sf3029vbas)) {
				if(!ByteArray.isEmpty(iv)) {
					try {
						SecretKey secretKey = new SecretKeySpec(gdf0We5sf3029vbas, "AES");
					    final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
					    GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length
					    cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
					    
					    byte[] encrypted = null;
					    encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_16));
					    
					    ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + encrypted.length);
					    byteBuffer.putInt(iv.length);
					    byteBuffer.put(iv);
					    byteBuffer.put(encrypted);
					    cipherMessage = byteBuffer.array();
					    //System.err.println("result: " + new String(cipherMessage, StandardCharsets.UTF_16));
					 }catch(Exception ex) {
						 ExceptionManager.manage(ex, SecurityManager.class ,"Encryption failed");
					 }
				}
				else {// iv is null
					if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
					if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (IV)");
				}
			}// key is null
			else {
				if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
				if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (KEY)");
			}
		}// input message is null
		else {
			if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Encryption failed due to insuficient input"));
			if(SettingsManager.DEBUG)throw new SecurityManagerException("Encryption failed due to insuficient input (MESSAGE)");
		}
		return Base64.getEncoder().encodeToString(cipherMessage);
		//return cipherMessage;
	}

	public static String decryptField(String cipherMessage) {
		byte[] msg = Base64.getDecoder().decode(cipherMessage);
		byte[] plainText = null;
		if(!ByteArray.isEmpty(msg)) {
			if(!ByteArray.isEmpty(gdf0We5sf3029vbas)) {
				try {			
					ByteBuffer byteBuffer = ByteBuffer.wrap(msg);
					int ivLength = byteBuffer.getInt();
					if(ivLength < 12 || ivLength >= 16) { // check input parameter
					    throw new IllegalArgumentException("invalid iv length");
					}
					byte[] iv = new byte[ivLength];
					byteBuffer.get(iv);
					byte[] cipherText = new byte[byteBuffer.remaining()];
					byteBuffer.get(cipherText);
					
					final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
					cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(gdf0We5sf3029vbas, "AES"), new GCMParameterSpec(128, iv));
					
					plainText = cipher.doFinal(cipherText);
				}
				catch(Exception ex) {
					//ExceptionManager.manage(ex, SecurityManager.class , "Password is not working");
					ExceptionManager.manage(ex, "Program could not decrypt data");
					return new String("");
				}
			}
			else {
				if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Decryption failed due to insuficient input"));
				if(SettingsManager.DEBUG)throw new SecurityManagerException("Decryption failed due to insuficient input (KEY)");
			}
		}
		else {
			if(SettingsManager.DEBUG)ExceptionManager.manage(new SecurityManagerException("Decryption failed due to insuficient input"));
			if(SettingsManager.DEBUG)throw new SecurityManagerException("Decryption failed due to insuficient input (MESSAGE)");
		}
		return new String(plainText, StandardCharsets.UTF_16);
	}
}

