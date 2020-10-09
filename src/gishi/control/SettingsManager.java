package gishi.control;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class SettingsManager {
	
	public  static final boolean DEBUG = false;
	private static final boolean showWarnings = true;
	private static final boolean showLogin = true;
	
	private static int 		timeToExit				= 5;
	private static String 	lastLoginDate 			= "null";
	private static String 	thisLoginDate 			= "null";
	private static String 	theme			 		= "null";
	private static boolean 	allowStartAnimation		= false;
	private static boolean 	allowAllAnimation		= true;
	private static boolean 	allowTileAnimation		= true;
	public  static boolean 	listDisplay 			= false;
	private static boolean 	weakEntry 				= true;
	public  static Dimension tileDimension 			= new Dimension(220 * UITheme.frameWidth / 1000, 110 * UITheme.frameHeight / 1000); 

	
	public static void saveSettings() {
		if (FileManager.wasInitialized()) {
			try {
				File config = FileManager.getConfigFile();
				new FileWriter(config).close(); //clean
				BufferedWriter writer = new BufferedWriter(new FileWriter(config));
				if(allowAllAnimation) {
					writer.write("true");
					writer.write("\n");
				}else {
					writer.write("false");
					writer.write("\n");
				}
				if(allowTileAnimation) {
					writer.write("true");
					writer.write("\n");
				}else {
					writer.write("false");
					writer.write("\n");
				}
				if(listDisplay) {
					writer.write("true");
					writer.write("\n");
				}else {
					writer.write("false");
					writer.write("\n");
				}
				writer.write(tileDimension.width+"");
				writer.write("\n");
				writer.write(tileDimension.height+"");
				writer.write("\n");
				writer.write(theme);
				writer.write("\n");
				writer.write(thisLoginDate);
				writer.write("\n");
				writer.write(timeToExit+"");
				writer.write("\n");
				writer.close();
			} catch (Exception ex) {
				ExceptionManager.manage(ex, SettingsManager.class, "Exception while saving config file");
			}
		} else {
			if(SettingsManager.DEBUG)System.out.println("Initialize main paths");
		}
	}
	
	public static void loadSettings() {		
		if (FileManager.wasInitialized()) {
			Scanner sc;
			int width = 0;
			int height = 0;
			try {
				sc = new Scanner(FileManager.getConfigFile());
				String line;
				int lineNumber = 0;
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					if (SettingsManager.DEBUG)System.out.println("setting " + lineNumber + " " + line);
					if (lineNumber == 0) {
						if(!line.equals(""))
						if (line.equals("true")) {
							allowAllAnimation = true;
						} else {
							allowAllAnimation = false;
						}
					} else if (lineNumber == 1) {
						if(!line.equals(""))
						if (line.equals("true")) {
							allowTileAnimation = true;
						} else {
							allowTileAnimation = false;
						}

					} else if (lineNumber == 2) {
						if(!line.equals(""))
						if (line.equals("true")) {
							listDisplay = true;
						} else {
							listDisplay = false;
						}
					} else if (lineNumber == 3) {
						if(!line.equals("")) width = Integer.parseInt(line);
					} else if (lineNumber == 4) {
						if(!line.equals("")) height = Integer.parseInt(line);
					} else if (lineNumber == 5) {
						if(!line.equals("")) theme = line;
					} else if (lineNumber == 6) {
						if(!line.equals("")) lastLoginDate = line;
					} else if(lineNumber == 7) {
						if(!line.equals("")) timeToExit = Integer.parseInt(line);
					}
					lineNumber++;
				}
				if(height > 0 && width > 0)
					tileDimension = new Dimension(width, height);
			} catch (FileNotFoundException e) {
				
			}
		}
	}
	
	public static boolean isWeakEntry() {
		return weakEntry;
	}
	public static void setWeakEntry(boolean weakEntry) {
		SettingsManager.weakEntry = weakEntry;
	}
	public static int getTimeToExit() {
		return timeToExit;
	}
	public static void setTimeToExit(int minutes) {
		timeToExit = minutes;
		saveSettings();
	}
	public static String getLastLoginDate() {
		return lastLoginDate;
	}
	public static void setLastLoginDate(String date) {
		lastLoginDate = date;
	}
	public static void setThisLoginDate(String date) {
		thisLoginDate = date;
		saveSettings();
	}
	public static String getTheme() {
		return theme;
	}
	public static void setTheme(String newtheme) {
		theme = newtheme;
		saveSettings();
	}
	
	public static boolean getWarningsSettings() {
		return showWarnings;
	}
	public static boolean getLoginSettings() {
		return showLogin;
	}
	
	public static boolean getStartAnimationSettings() {
		return allowStartAnimation;
	}
	public static void setStartAnimationSettings(boolean animationState) {
		allowStartAnimation = animationState;
		saveSettings();
	}
	
	public static boolean getAllAnimationSettings() {
		return allowAllAnimation;
	}
	public static void setAllAnimationSettings(boolean animationState) {
		allowAllAnimation = animationState;
		saveSettings();
	}
	public static boolean getTileAnimationSettings() {
		return allowTileAnimation;
	}
	public static void setTileAnimationSettings(boolean animationState) {
		allowTileAnimation = animationState;
		saveSettings();
	}
	
	public static void saveDefaultSettings() {
		try {
			File config = FileManager.getConfigFile();
			new FileWriter(config).close(); // clean
			BufferedWriter writer = new BufferedWriter(new FileWriter(config));
			
			if (allowAllAnimation) {
				writer.write("true");
				writer.write("\n");
			} else {
				writer.write("false");
				writer.write("\n");
			}
			if (allowTileAnimation) {
				writer.write("true");
				writer.write("\n");
			} else {
				writer.write("false");
				writer.write("\n");
			}
			if (listDisplay) {
				writer.write("true");
				writer.write("\n");
			} else {
				writer.write("false");
				writer.write("\n");
			}
			writer.write("\n");
			writer.write("\n");
			writer.write(thisLoginDate);
			writer.write("\n");
			writer.close();
		} catch (Exception ex) {
			ExceptionManager.manage(ex, SettingsManager.class, "Exception while saving configuration file");
			ex.printStackTrace();
		}
	}
	
}
