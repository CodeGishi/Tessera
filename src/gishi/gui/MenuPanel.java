package gishi.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.laf.panel.WebPanel;

import gishi.control.UITheme;
import gishi.templates.MenuButton;
import gishi.util.Icons;

@SuppressWarnings("serial")
public class MenuPanel extends WebPanel {

	private static final int 		v_gap 				= 0;
	private static final int 		h_gap 				= 0;

	private static final int 		size				= UITheme.sideMenuWidth/2;
	private static final Dimension 	spacerSize			= new Dimension(2*size, size);
	private static final Dimension 	preferredSize 		= new Dimension(UITheme.sideMenuWidth, UITheme.frameHeight);
	
	
	private static final ImageIcon 	icon 				= Icons.load("/icons/tessera_white.png", size, 1.0);
	private static final ImageIcon 	data 				= Icons.load("/icons/light/tiles.png", size, 1.0);
	private static final ImageIcon 	plus 				= Icons.load("/icons/light/plus.png", size, 1.0);
	private static final ImageIcon 	settings 			= Icons.load("/icons/light/settings.png", size, 1.0);
	private static final ImageIcon 	check 				= Icons.load("/icons/light/check.png", size, 1.0);
	private static final ImageIcon 	generate			= Icons.load("/icons/light/edit.png", size, 1.0);
	private static final ImageIcon 	more 				= Icons.load("/icons/light/dots.png", size, 1.0);

	
	/**
	 * Left side main control menu.
	 * 
	 * @param parent Instance of MainFrame 
	 */
	public MenuPanel(MainFrame parent) {
		
		setBackground(UITheme.menuBackground);
		setLocation(0, 0);
		setSize(preferredSize);
		setPreferredSize(preferredSize);
		setLayout(new FlowLayout(FlowLayout.CENTER, h_gap, v_gap));

		JPanel spacer = new JPanel();
		spacer.setPreferredSize(spacerSize);
		spacer.setBackground(UITheme.transparent);
		add(spacer);
		
		JLabel button1 = new JLabel();
		button1.setIcon(icon);
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((MainFrame) parent).swapLeft(parent.getContentPane(), ((MainFrame) parent).getActivePanel(),
						((MainFrame) parent).getWelcomePanel());
			}
		});	
		add(button1);
		
		JPanel spacer1 = new JPanel();
		spacer1.setPreferredSize(spacerSize);
		spacer1.setBackground(UITheme.transparent);
		add(spacer1);
		
		MenuButton button2 = new MenuButton(data);
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				((MainFrame) parent).swapLeft(parent.getContentPane(), ((MainFrame) parent).getActivePanel(),
						((MainFrame) parent).getLibraryPanel());
			}
		});
		button2.setTooltipText("show password library");
		add(button2);
		
		MenuButton button3 = new MenuButton(plus);
		button3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				((MainFrame) parent).swapLeft(parent.getContentPane(), ((MainFrame) parent).getActivePanel(),
						((MainFrame) parent).getCreatePanel());
			}
		});
		button3.setTooltipText("add new password storage");
		add(button3);

		MenuButton button4 = new MenuButton(check);
		button4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				((MainFrame) parent).swapLeft(parent.getContentPane(), ((MainFrame) parent).getActivePanel(),
						((MainFrame) parent).getCheckPanel());
			}
		});
		button4.setTooltipText("check password strength");
		add(button4);

		MenuButton button5 = new MenuButton(settings);
		button5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				((MainFrame) parent).swapLeft(parent.getContentPane(), ((MainFrame) parent).getActivePanel(),
						((MainFrame) parent).getSettingsPanel());
			}
		});
		button5.setTooltipText("settings");
		add(button5);
		
		MenuButton button6 = new MenuButton(generate);
		button6.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				((MainFrame) parent).swapLeft(parent.getContentPane(), ((MainFrame) parent).getActivePanel(),
						((MainFrame) parent).getPanelGenerate());
			}
		});
		button6.setTooltipText("generate password");
		add(button6);
		
		MenuButton button7 = new MenuButton(more);
		button7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				((MainFrame) parent).swapLeft(parent.getContentPane(), ((MainFrame) parent).getActivePanel(),
						((MainFrame) parent).getPanelMore());
			}
		});
		button7.setTooltipText("for fun purposes");
		add(button7);
		
		
	}
}
