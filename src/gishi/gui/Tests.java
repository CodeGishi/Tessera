package gishi.gui;

import java.util.Timer;
import java.util.TimerTask;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;

import gishi.control.FileManager;
import gishi.control.UITheme;

@SuppressWarnings("serial")
public class Tests extends WebFrame {
	
	/**
	 * Create the panel.
	 */
	public Tests() {
		setSize(300,300);
		setBackground(UITheme.background);
		getContentPane().setLayout(null);
		
		WebLabel lblNewLabel = new WebLabel("Working dir: ");
		lblNewLabel.setBounds(10, 11, 152, 39);
		getContentPane().add(lblNewLabel);
		
		WebLabel label = new WebLabel(FileManager.getWorkingDirectory());
		label.setBounds(172, 11, 344, 39);
		getContentPane().add(label);
		
		WebLabel lblChangeUserPassword = new WebLabel("Change User Password");
		lblChangeUserPassword.setBounds(10, 61, 214, 28);
		getContentPane().add(lblChangeUserPassword);
		
		WebButton btnChange = new WebButton("Change");
		btnChange.setBounds(274, 187, 160, 74);
		getContentPane().add(btnChange);
		
	}
	
	public static void log(String msg) {
		
	}
	
	Timer monitor;
	boolean disconected = false;
	int timeout = 10;
	int timeoutCounter = 0;
	public void startConnectionMonitor(){
		monitor = new Timer();
		monitor.schedule(new TimerTask() {
			public void run() {
				
			}
		}, 0, 1000);
	}
	
	public void stopSessionMonitor() {
		monitor.cancel();
	}
	
}
