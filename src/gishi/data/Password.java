package gishi.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import gishi.control.FileManager;
import gishi.control.PasswordManager;
import gishi.control.SecurityManager;
import gishi.control.SettingsManager;

public class Password {

	// ########################################
	// ########################################

	private String path;

	private int id;
	private String iconPath;
	private String storageName;
	private String login;
	private String password;

	private String website;
	private String more;
	private PasswordCategory category;

	private String creationDate;
	private String lastEditDate;

	private boolean save = false;
	private int fav = 0;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

	// #########################################
	// #########################################
	
	/**
	 * Basic constructor for Password class
	 * 
	 * @param id          (Integer) Unique ID of the password
	 * @param storageName (String) Name of the Password object
	 * @param password    (String) Hashed password
	 */
	public Password(String passwordName, String password) {
		this.id = PasswordManager.getNextID();
		this.path = FileManager.getUserDirectory() + "\\passwords\\" + id + ".pswd";
		this.storageName = passwordName;
		this.password = password;
		this.creationDate = dateFormat.format(Calendar.getInstance().getTime());
		this.lastEditDate = dateFormat.format(Calendar.getInstance().getTime());

		this.category = PasswordCategory.all;
		// this.save = true;
	}

//ULTIMATE CONSTRUCTOR CREATE
	/**
	 * Constructor for password.
	 * 
	 * @param iconPath    Path to Icon
	 * @param storageName Displayed name
	 * @param login       User login for given service
	 * @param password    Password to given service
	 * @param website
	 * @param category
	 * @param more        More data about this storage
	 */
	public Password(String iconPath, String storageName, String login, String password, String website,
			PasswordCategory category, String more) {
		this.id = PasswordManager.getNextID();
		this.iconPath = iconPath;
		this.storageName = storageName;
		this.login = login;
		this.password = password;

		this.website = website;
		this.more = more;
		this.category = category;
		this.fav = 0;

		this.creationDate = dateFormat.format(Calendar.getInstance().getTime());
		this.lastEditDate = dateFormat.format(Calendar.getInstance().getTime());
	}

	/**
	 * Reference of given Password
	 * 
	 * @param pswd Password Object
	 */
	public Password(Password pswd) {
		this.id = pswd.getId();
		this.iconPath = pswd.getIconPath();
		this.storageName = pswd.getStoragedName();
		this.login = pswd.getLogin();
		this.password = pswd.getPassword();

		this.website = pswd.getWebsite();
		this.more = pswd.getMore();
		this.category = pswd.getCategory();
		this.fav = pswd.getFav();

		this.creationDate = pswd.getCreationDate();
		this.lastEditDate = pswd.getLastEditDate();
	}

	// ULTIMATE CONSTRUCTOR LOAD
	public Password(int id, String iconPath, String storageName, String login, String password, String website,
			PasswordCategory category, String more, int fav, String creationDate, String lastEditDate) {
		this.id = id;
		this.iconPath = iconPath;
		this.storageName = storageName;
		this.login = login;
		this.password = password;

		this.website = website;
		this.more = more;
		this.category = category;
		this.fav = fav;

		this.creationDate = creationDate;
		this.lastEditDate = lastEditDate;
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public String getPath() {
		return FileManager.getPasswordDirectory() + "\\" + id + ".pswd";
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getStoragedName() {
		return storageName;
	}

	public void setStorageName(String setStorageName) {
		this.storageName = setStorageName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = gishi.control.SecurityManager.encryptField(password);
	}

	public void setPassword(char[] password) {
		this.password = gishi.control.SecurityManager.encryptField(String.valueOf(password));
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	public PasswordCategory getCategory() {
		return category;
	}

	public void setCategory(PasswordCategory category) {
		this.category = category;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getLastEditDate() {
		return lastEditDate;
	}

	public void setEditDate() {
		this.lastEditDate = dateFormat.format(Calendar.getInstance().getTime());
	}

	public void setFav(int isFavourite) {
		this.fav = isFavourite;
	}

	public int getFav() {
		return this.fav;
	}

	public boolean toSave() {
		return save;
	}

	public void setToSave(boolean save) {
		this.save = save;
	}

	public String toString() {
		if (SettingsManager.DEBUG)
			System.out.println(new String("[" + id + "]Password Encrypted: \n" + "name: \t\t" + storageName
					+ "\npassword: \t" + password + "\npath: \t\t" + path));
		return new String("[" + id + "]Password: \n" + "name: \t" + storageName + "\nhashed pswd: \t" + password);
	}

	public String encryptedToString() {
		// byte[] base = Base64.getDecoder().decode(storageName);
		// byte[] base2 = Base64.getDecoder().decode(password);

		if (SettingsManager.DEBUG)
			System.out.println(new String("[" + id + "]Password Decrypted: \n" + "name: \t\t" + storageName
					+ "\npassword: \t" + SecurityManager.decryptField(password) + "\niconPath: \t\t" + iconPath)
					+ "\ndate: \t\t" + lastEditDate + "\ncategory: \t" + category);

		return new String("[" + id + "]Password: \n" + "name: \t" + storageName + "\nhashed pswd: \t" + password);
	}
}
