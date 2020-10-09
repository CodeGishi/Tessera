package gishi.templates;

import javax.swing.JFrame;

import com.alee.laf.rootpane.WebFrame;

import gishi.control.UITheme;
import gishi.gui.ControlMenu;

@SuppressWarnings("serial")
public class TFrame extends WebFrame {

	/**
	 * Default project Frame.
	 * 
	 */
	public TFrame() {
		
		setUndecorated(true);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(UITheme.screenWidth/2 - UITheme.frameWidth/2, UITheme.screenHeight/2 - UITheme.frameHeight/2);
		setSize(UITheme.frameDimension);
		setPreferredSize(UITheme.frameDimension);
		setBackground(UITheme.background);
	}
	
	public void addControls(int controls, boolean movement) {
		ControlMenu cm = new ControlMenu(this, controls, movement);
		add(cm);
	}
	
	public void addControls(int controls, boolean movement, String title) {
		ControlMenu cm = new ControlMenu(this, controls, movement, title);
		add(cm);
	}
}
