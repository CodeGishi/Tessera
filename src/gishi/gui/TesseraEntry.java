package gishi.gui;

import java.awt.EventQueue;
import java.util.Locale;

import com.alee.laf.WebLookAndFeel;

import gishi.control.FileManager;

public class TesseraEntry {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					WebLookAndFeel.install();							// init web laf
					Locale.setDefault(Locale.ENGLISH);					// set locale for eng (for password dictionaries)
					
					if(FileManager.verifyMainPath()) { 					// basic information (no user needed)
						IntroAnimation intro = new IntroAnimation(); 	// logo animation
						intro.setVisible(true);
					}
					else {
						// no lunch then
					}
					
//					@unused for disabled intro animation 
//					if (!SettingsManager.getStartAnimationSettings()) {
//						MainFrame mf = new MainFrame();
//						mf.setVisible(true);
//					} 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
