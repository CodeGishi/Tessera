package gishi.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Comparator;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import com.alee.laf.progressbar.WebProgressBar;

import gishi.control.UITheme;
import gishi.data.Password;
import gishi.data.PasswordList;
import gishi.templates.ListSection;
import gishi.templates.PasswordTile;

@SuppressWarnings("serial")
public class ListPanel extends JPanel {

	private int listHeight = 500;
	private final LibraryPanel parent;
	
	/**
	 * List of PaswordTiles.
	 * 
	 * @param parent
	 */
	public ListPanel(final LibraryPanel parent) {
		this.parent = parent;
		setSize(parent.getSize());
		setPreferredSize(parent.getSize());
		setBackground(UITheme.background);
		setLayout(null);
	}

	/**
	 * Sorts PasswordList according to Alphabetical Order
	 * 
	 * @param passwordList
	 * @param order (-1 for descending, 1 for ascending)
	 */
	public void listAlphabetically(PasswordList passwordList, int order) {
		clear();
		if (order < 0) {
			passwordList.sort(Comparator.comparing(Password::getStoragedName, String.CASE_INSENSITIVE_ORDER));
			passwordList = passwordList.reverse();
		} else {
			passwordList.sort(Comparator.comparing(Password::getStoragedName, String.CASE_INSENSITIVE_ORDER));
		}
		
		String sign = "";
		boolean isFirst = true;
		boolean isNull = false;
		ListSection ls = null;
		
		Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
		Pattern number = Pattern.compile("[0-9]");
		Pattern word = Pattern.compile("[A-Z]");
		if (passwordList.size() != 0) {
			for (Password pswd : passwordList) {
				if (isFirst) {
					if (special.matcher(pswd.getStoragedName().toUpperCase().substring(0, 1)).find()) {
						sign = "@";
						if (pswd.getStoragedName().equals("---")) {
							sign = "no results";
							isNull = true;
						}
					} else if (number.matcher(pswd.getStoragedName().toUpperCase().substring(0, 1)).find()) {
						sign = "#";
					} else if (word.matcher(pswd.getStoragedName().toUpperCase().substring(0, 1)).find()) {
						sign = pswd.getStoragedName().toUpperCase().substring(0, 1);
					}
					//
					ls = new ListSection(this, sign);
					isFirst = false;
					//
				} else {
					if (special.matcher(pswd.getStoragedName().toUpperCase().substring(0, 1)).find()) {
						
					} 
					if (number.matcher(pswd.getStoragedName().toUpperCase().substring(0, 1)).find()) {
						if (sign.equals("@")) {
							add(ls);
							listHeight += ls.getHeight();
							sign = "#";
							ls = new ListSection(this, sign);
						}
						if (word.matcher(sign).find() && !sign.equals("#")) {
							add(ls);
							listHeight += ls.getHeight();
							sign = "#";
							ls = new ListSection(this, sign);
						}
					} 
					if (word.matcher(pswd.getStoragedName().toUpperCase().substring(0, 1)).find()) {
						if (!sign.equals(pswd.getStoragedName().toUpperCase().substring(0, 1))) {
							
							add(ls);
							listHeight += ls.getHeight();

							sign = pswd.getStoragedName().toUpperCase().substring(0, 1);
							ls = new ListSection(this, sign);
						}
					}
				}
				if (!isNull) {
					PasswordTile t = new PasswordTile(pswd.getId());
					ls.addTile(t);
				} else {
				}
			}
			//
		} else {
			ls = new ListSection(this);
		}
		ls.closeList();
		add(ls); // add last section
		listHeight += ls.getHeight();
		updateHeight();

		revalidate();
		repaint();
	}
	
	/**
	 * Lists all Passwords from Library.
	 * 
	 * @param passwordList
	 * @param order (-1 for descending, 1 for ascending)
	 */
	public void listAll(PasswordList passwordList, int order) {
		
		clear();
		if (order < 0) {
			passwordList.sort(Comparator.comparing(Password::getId));
			passwordList = passwordList.reverse();
		} else {
			passwordList.sort(Comparator.comparing(Password::getId));
		}
		String sign = "";
		boolean isFirst = true;
		boolean isNull = false;
		ListSection ls = null;
		
		if (passwordList.size() != 0) {
			for (Password pswd : passwordList) {
				if (isFirst) {
					sign = "all";
					if (pswd.getStoragedName().equals("---")) {
						sign = "no results";
						isNull = true;
					}
					ls = new ListSection(this, sign);
					isFirst = false;
					//
				} else {
				}
				if (!isNull) {
					PasswordTile t = new PasswordTile(pswd.getId());
					ls.addTile(t);
				} else {
				}
			}
		} else {
			ls = new ListSection(this);
		}
		ls.closeList();
		add(ls); // add last section
		listHeight += ls.getHeight();
		updateHeight();

		revalidate();
		repaint();
	}
	
	/**
	 * Sorts PasswordList according to Password ID.
	 * 
	 * @param passwordList
	 * @param order (-1 for descending, 1 for ascending)
	 */
	public void listNumerically(PasswordList passwordList, int order) {
		passwordList.sort(Comparator.comparing(Password::getId));
	}

	/**
	 * Sorts PasswordList according to LastEditDate.
	 * 
	 * @param passwordList
	 * @param order (-1 for descending, 1 for ascending)
	 */
	public void listChronologically(PasswordList passwordList, int order) {
		passwordList.sort(Comparator.comparing(Password::getLastEditDate));
	}

	/**
	 * Updates ListPanel Height to enable scroll.
	 */
	private void updateHeight() {
		setPreferredSize(new Dimension(this.getWidth(), listHeight));// update actual size 
	}

	/**
	 * Resets and clreas the List
	 */
	public void clear() {
		listHeight = 0;
		removeAll();
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		revalidate();
		repaint();
	}
	
	/**
	 * Show Loading bar while waiting.
	 */
	public void addLoadingBar() {
		WebProgressBar progressBar = new WebProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setSize(this.getWidth()/3, this.getWidth()/30);
		progressBar.setLocation(this.getWidth()/2 - progressBar.getWidth()/2, this.getHeight()/2 - progressBar.getHeight()/2);
		add(progressBar);
	}
	
	
	public LibraryPanel getLibraryPanel() {
		return this.parent;
	}
}
