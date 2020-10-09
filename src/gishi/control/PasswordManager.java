package gishi.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import gishi.data.Password;
import gishi.data.PasswordCategory;
import gishi.data.PasswordList;
import nbvcxz.Nbvcxz;
import nbvcxz.resources.ConfigurationBuilder;

public class PasswordManager {
	
	private static PasswordList mainlist 	= new PasswordList(); 
	private static PasswordList activelist 	= new PasswordList();
	private static Password		nullpswd 	= new Password("---", "a");
	public static Nbvcxz nbvcxz = new Nbvcxz(new ConfigurationBuilder()
	        .setLocale(Locale.forLanguageTag("en"))
	        .setMinimumEntropy(45d)
	        .createConfiguration());
	
	
	public PasswordManager(String name, String password, String iconPath) {
		if(SettingsManager.DEBUG)System.out.println();
	}
	
	public static int getNextID() {
		int id = 0;
		for(int i = 0; i < mainlist.size(); i++) {
			if(mainlist.get(i).getId() > id) {
				id = mainlist.get(i).getId();
			}
		}
		return id+1;
	}
	
	/**
	 * 
	 * @param ID
	 * @return Password with given ID or NULL if not found.
	 */
	public static Password getPassword(final int ID) {
		Password result = null;
		for(Password p : PasswordManager.getMainList()) {
			if(p.getId() == ID) {
				result = p;
			}
		}
		return result;	
	}
	
	/**
	 * Adds the given Password to the Library.
	 * Saves Password file.
	 * @param pswd
	 */
	public static void addNewPassword(final Password pswd) {
		mainlist.add(pswd);
		savePassword(pswd);
		updateActiveList();
	}
	/**
	 * Deletes Password user library. Files included.
	 * @param pswd
	 * @throws IOException
	 */
	public static void deletePassword(final Password pswd) throws IOException {
		File file = null;
		if(pswd != null) {
			file = new File(pswd.getPath());
			if(file.delete()) 
	        { 
				mainlist.remove(pswd);
				updateActiveList();
				if(SettingsManager.DEBUG)System.out.println("File deleted successfully"); 
	        }
	        else
	        { 
	        	if(SettingsManager.DEBUG)System.out.println("File deletion failed"); 
	        	throw new IOException() ;
	        }
		}
	}
	
	/**
	 * Checks directory structure and files.
	 * @return
				<b>1</b> if UserDirectory contains passwords. 
				</br> 
				<b>0</b> if UserDirectory is empty.
				<b>-2</b> if FileManager wasn't initialized.
	 */
	public static int checkPasswordsDirectory() {
		if(FileManager.wasInitialized()) {
			if(getFilesCount(FileManager.getPasswordDirectory()) > 0) {
				//there are files but with wrong extension
				if(getPasswordsCount(FileManager.getPasswordDirectory()) > 0) {
					// there are passwords
					return 1;
				}else {//no password files
					return 0;
				}
			}else {//no  files
				return 0;
			}
		}else {
			if(SettingsManager.DEBUG)System.out.println("PasswordManager (checkPswdDir) Initialize main paths");
			return -2;
		}
	}
	
	/**
	 * @return List of all Passwords 
	 */
	public static PasswordList getMainList() {
		return mainlist;
	}
	
	/**
	 * @return List of Selected Passwords 
	 */
	public static PasswordList getActiveList() {
		return activelist;
	}
	
	/**
	 * Refreshes list of selected passwords.
	 */
	public static void updateActiveList() {
		activelist.clear();
		for(Password pswd : mainlist) {
			activelist.add(pswd);
		}
	}
	
	private static int getFilesCount(String passwordDirectory) {
		int count = 0;
		File dir = new File(passwordDirectory);
		File [] filess = dir.listFiles();		
		for (File f : filess) {
		    if(f != null)
			count++;
		}
		return count;
	}
	
	/**
	 * Simple check for custom file extension.
	 * 
	 * @param passwordDirectory
	 * @return number of .pswd files
	 */
	private static int getPasswordsCount(String passwordDirectory) {
		int count = 0;
		File dir = new File(passwordDirectory);
		File [] filess = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".pswd");
		    }
		});	
		for (File pswdfiles : filess) {
		    if(pswdfiles != null)
			count++;
		}
		return count;
	}
	
	/**
	 * Loads and Populates PasswordList.
	 * 
	 * @throws Exception while loading password files.
	 */
	public static void loadPasswordList(){
		mainlist.clear();
		activelist.clear();
		if (checkPasswordsDirectory() > 0) {
			File[] filess = new File(FileManager.getPasswordDirectory()).listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".pswd");
				}
			});
			if (filess.length != 0) {
				try {
					for (File pswdfile : filess) {
						mainlist.add(parsePasword(pswdfile));
						activelist.add(parsePasword(pswdfile));
					} 
					PasswordList.initialized = true;
				}catch (Exception ex) {
						ExceptionManager.manage(ex, PasswordManager.class, "Exception while loading PasswordList");
						// ex.printStackTrace();
					}	
			} else {
				if(SettingsManager.DEBUG)System.out.println("no .pswd files");
			}
		} 
		else if(checkPasswordsDirectory() == 0) {
			//mainlist.add(nullpswd);
			PasswordList.initialized = true;
		}
		else {
			if(SettingsManager.DEBUG)System.out.println("PassworManager(load passwords): Initialize main paths");
		}
	}
	
	/**
	 * 
	 * @param category
	 * @return Sublist of Passwords with given PasswordCategory.
	 */
	public static PasswordList getSubList(PasswordCategory category) {
		activelist.clear();
		if(category == PasswordCategory.valueOf("all")) {
			for(Password pswd : mainlist) {
				activelist.add(pswd);
			}
		}
		else {
			for(Password pswd : mainlist) {
				if(pswd.getCategory() == category) {
					activelist.add(pswd);
				}
			}
			if(activelist.size() == 0) {
				activelist.add(nullpswd);
			}
		}
		return activelist;
	}
	
	/**
	 * 
	 * @param keyword
	 * @return Sublist of Passwords containing keyword.
	 */
	public static PasswordList getSubList(String keyword) {
		activelist.clear();
		String tmp = keyword.toUpperCase();
	    String pattern = "\\b"+tmp;
	    Pattern p = Pattern.compile(pattern);
		for(Password pswd : mainlist) {
			if(p.matcher(pswd.getStoragedName().toUpperCase()).find()) {
				activelist.add(pswd);
			}
		}
		if(activelist.size() == 0) {
			activelist.add(nullpswd);
		}
		return activelist;
	}

	/**
	 * 
	 * @param keyword
	 * @return Sublist of Passwords marked as favorites.
	 */
	public static PasswordList getFavourites() {
		activelist.clear();
		
		for(Password pswd : mainlist) {
			if(pswd.getFav() > 0) {
				activelist.add(pswd);
			}
		}
		if(activelist.size() == 0) {
			activelist.add(nullpswd);
		}
		return activelist;
	}
	
	public static void printActiveList() {
		for (Password pswdfile : activelist) {
			pswdfile.encryptedToString();			
		}
	}

	/**
	 * Encrypts and Saves given Password to file.
	 * 
	 * @param pswd
	 * @throws While saving .pswd file
	 */
	public static void savePassword(final Password pswd) {		
			try {
				new FileWriter(pswd.getPath()).close(); //clean
				BufferedWriter writer = new BufferedWriter(new FileWriter(pswd.getPath()));
				writer.write(SecurityManager.encryptField(Integer.toString(pswd.getId())));
				writer.write("\n");// ID
				writer.write(SecurityManager.encryptField(pswd.getIconPath()));
				writer.write("\n");// icon
				writer.write(SecurityManager.encryptField(pswd.getStoragedName()));
				writer.write("\n");// StorageName
				writer.write(SecurityManager.encryptField(pswd.getLogin()));
				writer.write("\n");// login
				writer.write(SecurityManager.encryptField(pswd.getPassword()));
				writer.write("\n");// password
				writer.write(SecurityManager.encryptField(pswd.getWebsite()));
				writer.write("\n");// website
				writer.write(SecurityManager.encryptField(pswd.getCategory().toString()));
				writer.write("\n");// category
				writer.write(SecurityManager.encryptField(pswd.getMore()));
				writer.write("\n");// more
				writer.write(SecurityManager.encryptField(Integer.toString(pswd.getFav())));
				writer.write("\n");// favourite
				writer.write(SecurityManager.encryptField(pswd.getCreationDate()));
				writer.write("\n");// creation date
				writer.write(SecurityManager.encryptField(pswd.getLastEditDate()));
								   // edit date
				writer.close();
				
			} catch (Exception ex) {
				//ExceptionManager.manage(ex, PasswordManager.class, "Exception while saving password");
				ExceptionManager.manage(ex, "Could not save password");
				if(SettingsManager.DEBUG)ex.printStackTrace();
			}
	}
	
	/**
	 * Parsing given .pswd file
	 * 
	 * @param pswdfile
	 * @return Password
	 * @throws FileNotFoundException
	 */
	private static Password parsePasword(final File pswdfile) throws FileNotFoundException {
		
		Password result = null;
		Scanner sc = new Scanner(pswdfile);
	    String line = "";
	    int lineNumber = 0;	    
	   
	    int fav 			= 0;
	    int id 				= 0;
	    String iconPath 	= "";
	    String storageName 	= "";
	    String login 		= "";
	    String password 	= "";
		String website 		= "";
		String more 		= "";
		String creation 	= "";
		String edit 		= "";
		
		PasswordCategory category = null;
		
	    while(sc.hasNextLine()) {
		    line = sc.nextLine();
			    if(lineNumber == 0) {
			    	id = Integer.parseInt(gishi.control.SecurityManager.decryptField(line));
			    }
			    else if(lineNumber == 1) {
			    	iconPath = SecurityManager.decryptField(line);
			    }
				else if(lineNumber == 2) {
					storageName = SecurityManager.decryptField(line);
				}
				else if(lineNumber == 3) {
					login = SecurityManager.decryptField(line);
				}
				else if(lineNumber == 4) {
					password = SecurityManager.decryptField(line);
				}
				else if(lineNumber == 5) {
					website = SecurityManager.decryptField(line);
				}
				else if(lineNumber == 6) {
					category = PasswordCategory.valueOf(SecurityManager.decryptField(line));
				}
				else if(lineNumber == 7) {
					more = SecurityManager.decryptField(line);
				}
				else if(lineNumber == 8) {
					fav = Integer.parseInt(gishi.control.SecurityManager.decryptField(line));
				}
				else if(lineNumber == 9) {
					creation = SecurityManager.decryptField(line);
				}
				else if(lineNumber == 10) {
					edit = SecurityManager.decryptField(line);
				}
		    lineNumber ++;
	    }
	    sc.close();
	    result = new Password(id, iconPath, storageName, login, password, website, category, more, fav, creation, edit);
	    return result;
	}
}
