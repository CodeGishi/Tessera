package gishi.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import gishi.exceptions.FileManagerException;

public class FileManager {
	
	private static String workingDirectory = "";
	private static String usersDirectory = "";
	private static String userDirectory = "";
	private static String passwordsDirectory = "";
	private static File userConfig;
	private static File userAuthentication;
	private static boolean initialized = false;
	private static boolean initializeMainPath = false;
	private static boolean initializedUserPath = false;
	
	/**
	 * Bundle of methods validating essential directories and files
	 * 
	 * @param user User who's directory would be examined
	 * @throws FileManagerException
	 */
	public static boolean initialize(String user) throws FileManagerException {
		if(user.length() > 0) {
			verifyMainPath();
			verifyUserPath(user);
			initialized =  initializeMainPath && initializedUserPath;
			//RuntimeVariables.user = String.valueOf(user);
			return (initialized);
		}
		else {
			if(SettingsManager.DEBUG)ExceptionManager.manage(new FileManagerException("Initializing failed due to insufficient input (USER)"));
			if(SettingsManager.DEBUG)throw new FileManagerException("Initializing failed due to insufficient input (USER)");
			return (initialized);
		}
	}
	
	public static boolean verifyMainPath() throws FileManagerException {
		boolean result = false;
		File workingDir = new File(Paths.get(".").toAbsolutePath().normalize().toString());
		File usrsDir = null;		
		if(workingDir.isDirectory()) {
			if(workingDir.list().length > 0) {
				workingDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
				usrsDir = new File (workingDirectory + "\\users");
				if(usrsDir.exists()) {
					usersDirectory = workingDirectory + "\\users";
					initializeMainPath = true;
					result = true;
				}
				else {//no users directory
					if(SettingsManager.DEBUG)ExceptionManager.manage(new FileManagerException("No users directory"));
					throw new FileManagerException("No users directory");
				}
			}
			else {//working directory is empty
				if(SettingsManager.DEBUG)ExceptionManager.manage(new FileManagerException("Working directory is empty"));
				throw new FileManagerException("Working directory is empty");
			}
		}
		else {//working directory is not a folder
			if(SettingsManager.DEBUG)ExceptionManager.manage(new FileManagerException("Working directory is not a folder"));
			throw new FileManagerException("Working directory is not a folder");
		}
		return result;
	}

	private static boolean verifyUserPath(String user) throws FileManagerException{
		boolean result = false, flag1 = false, flag2=false, flag3 = false;
		if(initializeMainPath) {
			File userDir = new File(usersDirectory + "\\" + user);
			if(userDir.exists()) {
				if(userDir.isDirectory()) {
					if(userDir.list().length > 0) {
						userDirectory = usersDirectory + "\\" + user;
						//User authentication file
						File userAuth = new File(usersDirectory + "\\" + user + "\\authentication");
						if(userAuth.isFile()) {
							if(userAuth.length() != 0) {	
								userAuthentication = userAuth;
								flag1 = true;
							}
							else {//user auth is empty
								ExceptionManager.manage(new FileManagerException("Authentication file is empty"));
								if(SettingsManager.DEBUG)throw new FileManagerException("Authentication file is empty");
							}
						}
						else {//user auth is not a file / not exist
							ExceptionManager.manage(new FileManagerException("Could not find user authentication file"));
							if(SettingsManager.DEBUG)throw new FileManagerException("User authentication file is not a file / does not exist");
						}
						//User config file
						File userConf = new File(usersDirectory + "\\" + user + "\\config");
						if(userConf.isFile()) {
							if(userConf.length() != 0) {
								userConfig = userConf;
								flag2 = true;
							}
							else {//user conf is empty
								ExceptionManager.manage(new FileManagerException("Configuration file is empty"));
								BufferedWriter writer;
								try {
									writer = new BufferedWriter(new FileWriter((usersDirectory + "\\" + user + "\\config")));
									writer.write("true");
									writer.write("\n");
									writer.write("true");
									writer.write("\n");
									writer.write("false");
									writer.write("\n");
									writer.write(232+"");
									writer.write("\n");
									writer.write(70+"");
									writer.write("\n");
									writer.write("dark_red");
									writer.write("\n");
									writer.write(new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
									writer.write("\n");
									writer.close();
									userConfig = new File(usersDirectory + "\\" + user + "\\config");
								} catch (IOException e) {
								}
								if(SettingsManager.DEBUG)throw new FileManagerException("Configuration file is empty");
							}
						}//user conf is not a file / not exist
						else {
							if(SettingsManager.DEBUG)ExceptionManager.manage(new FileManagerException("Could not find configuration file"));
							BufferedWriter writer;
							try {
								writer = new BufferedWriter(new FileWriter((usersDirectory + "\\" + user + "\\config")));
								writer.write("true");
								writer.write("\n");
								writer.write("true");
								writer.write("\n");
								writer.write("false");
								writer.write("\n");
								writer.write(232+"");
								writer.write("\n");
								writer.write(70+"");
								writer.write("\n");
								writer.write("dark_red");
								writer.write("\n");
								writer.write(new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
								writer.write("\n");
								writer.close();
								userConfig = new File(usersDirectory + "\\" + user + "\\config");
							} catch (IOException e) {
							}
							if(SettingsManager.DEBUG)throw new FileManagerException("User configuration file is not a file / does not exist");
						}
						//User passwords data
						File userPass = new File(usersDirectory + "\\" + user + "\\passwords");
						if(userPass.isDirectory()) {
							flag3 = true;
							passwordsDirectory = usersDirectory + "\\" + user + "\\passwords";
						}
						else {//user pass dir is not a folder / not exist
							ExceptionManager.manage(new FileManagerException("User passwords data is missing"));
							if(SettingsManager.DEBUG)throw new FileManagerException("User passwords data is missing");
						}
						//######################### !!! ######################### 
						if(flag1 && flag2 && flag3) {
							initializedUserPath = true;
							result = true; 
						}//######################## !!! ######################### 
						
					}
					else {//user dir is empty
						if(SettingsManager.DEBUG)ExceptionManager.manage(new FileManagerException("User directory is empty"));
						if(SettingsManager.DEBUG)throw new FileManagerException("User directory is empty");
					}
				}
				else {//user dir is not a folder
					if(SettingsManager.DEBUG)ExceptionManager.manage(new FileManagerException("User directory is not a folder"));
					if(SettingsManager.DEBUG)throw new FileManagerException("User directory is not a folder");
				}
			}//user dir does not exist
			else {
				if(SettingsManager.DEBUG)ExceptionManager.manage(new FileManagerException("User directory does not exist"));
				if(SettingsManager.DEBUG)throw new FileManagerException("User directory does not exist");
			}
		}// main path was not initialized
		return result;
	}
	
	@SuppressWarnings("unused")
	private static boolean verifyCustomUserPath(String path) throws FileManagerException{
		boolean result = false, flag1 = false, flag2=false, flag3 = false;
		File userDir = new File(path);
		if(userDir.exists()) {
			if(userDir.isDirectory()) {
				if(userDir.list().length > 0) {
					userDirectory = path;
					//User authentication file
					File userAuth = new File(path + "\\authentication");
					if(userAuth.isFile()) {
						if(userAuth.length() != 0) {
							userAuthentication = userAuth;
							flag1 = true;
						}
						else {//user auth is empty
							throw new FileManagerException("Authentication file is empty");
						}
					}
					else {//user auth is not a file / not exist
						throw new FileManagerException("User authentication file is not a file / does not exist");
					}
					//User config file
					File userConf = new File(path + "\\config");
					if(userConf.isFile()) {
						if(userConf.length() != 0) {
							userConfig = userConf;
							flag2 = true;
						}
						else {//user conf is empty
							throw new FileManagerException("Configuration file is empty");
						}
					}//user conf is not a file / not exist
					else {
						throw new FileManagerException("User configuration file is not a file / does not exist");
					}
					//User passwords data
					File userPass = new File(path + "\\passwords");
					if(userPass.isDirectory()) {
						passwordsDirectory = path + "\\passwords";
						flag3 = true;
					}
					else {//user pass dir is not a folder / not exist
						throw new FileManagerException("User password data is missing");
					}					
					//######################### !!! ######################### 
					if(flag1 && flag2 && flag3) {
						initializedUserPath = true;
						result = true; 
					}//######################## !!! ######################### 
				}
				else {//user dir is empty
					throw new FileManagerException("User directory is empty");
				}
			}
			else {//user dir is not a folder
				throw new FileManagerException("User directory is not a folder");
			}
		}//user dir does not exist
		else {	
			throw new FileManagerException("User directory does not exist");
		}
		return result;
	}
	
	public static boolean createUserDirectory(String user) {
		
		boolean result = false;
		if(verifyMainPath()) {
			File newUserDirectory = new File(usersDirectory+"\\"+user);
			if(SettingsManager.DEBUG)System.out.println("new dir: " + newUserDirectory);
			if(!newUserDirectory.isDirectory()){
				if(SettingsManager.DEBUG)System.out.println("Passed dir check...");
				Path userDir = Paths.get(usersDirectory + "\\" + user);
				Path userPasswordStorage = Paths.get(usersDirectory + "\\" + user + "\\passwords");
				Path userConfiFile = Paths.get(usersDirectory + "\\" + user + "\\config");
				Path userAuthenticationFile = Paths.get(usersDirectory + "\\" + user + "\\authentication");
				if(SettingsManager.DEBUG)System.out.println("Paths initialized...");
		       	try {
		       		Files.createDirectories(userDir);
		       		if(SettingsManager.DEBUG)System.out.println("dir created..." + userDir);
		       		userDirectory = usersDirectory + "\\" + user;
		       		Files.createDirectories(userPasswordStorage);
		       		if(SettingsManager.DEBUG)System.out.println("storage created..." );
		       		Files.createFile(userConfiFile);
			       		BufferedWriter writer = new BufferedWriter(new FileWriter((usersDirectory + "\\" + user + "\\config")));
						writer.write("true");
						writer.write("\n");
						writer.write("true");
						writer.write("\n");
						writer.write("false");
						writer.write("\n");
						writer.write(232+"");
						writer.write("\n");
						writer.write(70+"");
						writer.write("\n");
						writer.write("dark_red");
						writer.write("\n");
						writer.write(new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
						writer.write("\n");
						writer.close();
					    userConfig = new File(usersDirectory + "\\" + user + "\\config");;
					   // SettingsManager.saveSettings();
					    if(SettingsManager.DEBUG)System.out.println("config created..." + userConfiFile);
		       		Files.createFile(userAuthenticationFile);
		       		userAuthentication = new File(usersDirectory + "\\" + user + "\\authentication");
		       		if(SettingsManager.DEBUG)System.out.println("authentication created..." + userAuthenticationFile);
		       		result = true;
		        } catch (Exception ex) { //fail to create files
		        	//ExceptionManager.manage(ex, FileManager.class, "Could not create user directory");
		        	ExceptionManager.manage(ex, "Could not create user directory" );
		        }
			}
			else {//username taken
				ExceptionManager.manage(new FileManagerException("This Username is already taken"));
			}
		}
		else {// problems with working directory
			
		}
		return result;
	}
	
	public static void deleteUserDirectory(String user) throws IOException {
		if(verifyMainPath()) {
			File userDir = new File(usersDirectory+"\\"+user);
			deleteUserFiles(userDir);
		}			
	}
	
	private static void deleteUserFiles(File inputFile) throws IOException {
		if (inputFile.isDirectory()) {
			File[] entries = inputFile.listFiles();
			if (entries != null) {
				for (File entry : entries) {
					deleteUserFiles(entry);
				}
			}
		}
		if (!inputFile.delete()) {
			throw new IOException("Failed to delete " + inputFile);
		}
	}
	
	public static String getWorkingDirectory() {
		if(initialized) 		return workingDirectory;
		else						return "";
	};
	
	public static String getUserDirectory() {
		return userDirectory;
	}

	public static String getUsersDirectory() {
		return usersDirectory;
	}
	
	
	public static String getPasswordDirectory() {
		if(initialized)		return passwordsDirectory;
		else						return "";
	}
	
	public static File getConfigFile() {
		if(initialized)	return userConfig;
		else			return new File("");
	}
	
	public static File getAuthenticationFile() {
		if(initialized)		return userAuthentication;
		else						return new File("");
	}

	public static boolean wasInitialized() {
		return initialized;
	}
	
	public static int getFilesCount(String directory, String extension) {
		int count = 0;
		File dir = new File(directory);
		File [] filess = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(extension);
		    }
		});	
		for (File files : filess) {
		    if(files != null)
			count++;
		}
		return count;
	}
	
}
