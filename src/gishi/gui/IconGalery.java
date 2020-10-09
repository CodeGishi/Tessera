package gishi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;

import gishi.control.FileManager;
import gishi.control.UITheme;
import gishi.templates.TButton;
import gishi.util.Icons;

@SuppressWarnings("serial")
public class IconGalery extends WebDialog{

	static Dimension preferredSize = new Dimension(530*UITheme.frameWidth/1000, 152*UITheme.frameHeight/1000);
	int iconsize = 108* UITheme.frameHeight / 1000;
	
	static CreatePanel createPanel;
	static IconGalery ig = new IconGalery();
	
	/**
	 * Icon galery pop up dialog.
	 * Shows a galery of avilable PasswordTile icons.
	 * 
	 */
	public IconGalery() {
		setUndecorated(true);
		setSize(preferredSize);
		setPreferredSize(preferredSize);
		setBackground(UITheme.transparent);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		WebPanel panel = new WebPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		WebScrollPane scrollPane = new WebScrollPane(panel);
		scrollPane.setShadeWidth(4);
		scrollPane.setDrawFocus(false);
		scrollPane.setRequestFocusEnabled(false);
		scrollPane.setPaintButtons(false);
		scrollPane.setDrawBackground(true);
		scrollPane.setRound(5);
		scrollPane.setMargin(new Insets(1, 1, 1, 1));
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane);
		
		File dir = new File(FileManager.getWorkingDirectory()+"\\res\\tiles");
	
		File [] filess = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		    	return name.endsWith(".png");
		    }
		});	
		
		for (File files : filess) {
		    if(files != null) {
		    	String tmp = FileManager.getWorkingDirectory() + "\\res";
		    	String temp = files.getPath().replace(tmp, "");
		    	String normalPath = temp.replace("\\", "/");
		    	galeryIcon icon = new galeryIcon(normalPath);
		    	panel.add(icon);
		    }
		    else {
		    	//System.out.println("file is null");
		    }
		}
	}
	
	/**
	 * Galery inner class of iconed buttons for displaying possible PasswordTile icon
	 *
	 */
	private class galeryIcon extends TButton{
		String path = "";
		public galeryIcon(String iconPath) {
			this.path = iconPath;
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					createPanel.getSelectedIconPath(path);
					hideGalery();
				}
			});
			setRolloverShadeOnly(true);
			setRolloverDecoratedOnly(true);
			//setRolloverDarkBorderOnly(true);
			setRequestFocusEnabled(true);
			setDrawShade(true);
			setHorizontalAlignment(SwingConstants.CENTER);
			setPreferredSize(new Dimension(iconsize,iconsize));
			setIcon(Icons.load(path, iconsize, 0.8));
			
			setTopBgColor(	new Color(235, 235, 235	));
			setBottomBgColor(new Color(235, 235, 235));
			setShineColor(	new Color(255, 255, 255	));
		}
	}
	
	public static void showGalery(CreatePanel parent, Point anchor) {
			createPanel = parent;
			ig.setLocation(anchor);
			ig.setVisible(true);
	}
	public static void hideGalery() {
			ig.setVisible(false);
	}
	public static boolean isGaleryVisible() {
		return ig.isVisible();
	}
}
