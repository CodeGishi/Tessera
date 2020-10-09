package gishi.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.alee.extended.statusbar.WebMemoryBar;
import com.alee.laf.panel.WebPanel;

import gishi.control.SettingsManager;
import gishi.control.UITheme;
import gishi.templates.ContentPanel;
import gishi.templates.TLabel;

@SuppressWarnings("serial")
public class WelcomePanel extends ContentPanel{
	private WebPanel contentPane;
	public WelcomePanel(MainFrame parent) {

		setParentFrame(parent);

		contentPane = new WebPanel();
		contentPane.setLayout(null);
		contentPane.setLocation(0, 0);
		contentPane.setSize(this.getSize());
		contentPane.setBackground(UITheme.background);
		add(contentPane);
		
		TLabel lblWelcome = new TLabel("TESSERA");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblWelcome.setForeground(UITheme.background2);
		lblWelcome.setSize(getWidth()/2,getHeight()/10);
		lblWelcome.setLocation(getWidth()/2 - lblWelcome.getWidth()/2, 40*getHeight()/100 - lblWelcome.getHeight()/2);
		contentPane.add(lblWelcome);
		
		TLabel lblDate = new TLabel("Last login  " + SettingsManager.getLastLoginDate());
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDate.setForeground(UITheme.background2);
		lblDate.setSize(getWidth()/2,getHeight()/10);
		lblDate.setLocation(lblWelcome.getLocation().x, lblWelcome.getLocation().y + lblWelcome.getHeight());
		contentPane.add(lblDate);
		
		if(!SettingsManager.isWeakEntry() &&  SettingsManager.isWeakEntry()) {
			/*
			TLabel labelEntry = new TLabel("Change Password");
			labelEntry.setHorizontalAlignment(SwingConstants.CENTER);
			labelEntry.setForeground(UITheme.error);
			labelEntry.setFont(new Font("Tahoma", Font.PLAIN, 20));
			labelEntry.setSize(getWidth()/2,getHeight()/10);
			labelEntry.setLocation(lblWelcome.getLocation().x, lblDate.getLocation().y + lblDate.getHeight());
			contentPane.add(labelEntry);
			*/
		}
		
		JPanel panel = new JPanel();
		panel.setBackground(UITheme.inputBackground);
		panel.setSize(getWidth()/2, getHeight()/20);
		panel.setLocation(getWidth()/2 - panel.getWidth()/2, 90*getHeight()/100);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		WebMemoryBar memoryBar1 = new WebMemoryBar();
		memoryBar1.drawBorder = false;
		memoryBar1.setDrawBorder(false);
		memoryBar1.setForeground(UITheme.foreground);
		memoryBar1.setDrawShade(false);
		memoryBar1.setFocusable(false);
		memoryBar1.setFillBackground(false);
		if(UITheme.theme.equals("light")) {
			memoryBar1.setUsedBorderColor(UITheme.background2);
		}
		else {
			memoryBar1.setUsedBorderColor(UITheme.background);
		}		
		memoryBar1.setUsedFillColor(UITheme.background);
		panel.add(memoryBar1, BorderLayout.CENTER);
		
		
	}
}
