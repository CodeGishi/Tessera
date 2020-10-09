package gishi.templates;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gishi.control.SettingsManager;
import gishi.control.UITheme;
import gishi.gui.ListPanel;

@SuppressWarnings("serial")
public class ListSection extends JPanel {

	private final int width;
	private final int h_gap = 29 * SettingsManager.tileDimension.width / 1000;
	private final int v_gap = 200 * UITheme.controlMenuHeight / 1000;
	private final int header_size = 25;

	private int row_max = 0;
	private int tileCounter = 0;
	private boolean empty = false;
	
	ListPanel listPanel;
	
	/**
	 * Section of the ListPanel
	 * 
	 * @param list
	 */
	public ListSection(JPanel list) {
		this.listPanel = (ListPanel) list;
		width = list.getWidth();
		row_max = width / (SettingsManager.tileDimension.width + h_gap);
		setBackground(UITheme.background);

		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(v_gap);
		flowLayout.setHgap(h_gap);

		JPanel bg = new JPanel();
		bg.setBackground(UITheme.background);
		bg.setPreferredSize(new Dimension(98*list.getWidth()/100, list.getHeight()));
		bg.setLayout(new BorderLayout(0, 0));
		add(bg);
		
		//"Password Storage was found empty."
		JLabel lblSign = new JLabel();
		lblSign.setEnabled(true);
		lblSign.setFont(new Font("Tahoma", Font.BOLD,  40 * UITheme.frameHeight / 1000));
		lblSign.setText("Empty Password Storage");
		lblSign.setHorizontalAlignment(SwingConstants.CENTER);
		lblSign.setForeground(UITheme.background2);
		bg.add(lblSign, BorderLayout.CENTER);
		empty = true;
	}
	
	/**
	 * Section of the ListPanel
	 * 
	 * @param list
	 */
	public ListSection(JPanel list, String sign) {
		this.listPanel = (ListPanel) list;
		width = list.getWidth();
		row_max = width / (SettingsManager.tileDimension.width + h_gap);
		setBackground(UITheme.background);
		
		FlowLayout flowLayout = (FlowLayout) getLayout();
		if(SettingsManager.listDisplay) {
			flowLayout.setAlignment(FlowLayout.CENTER);
		}else {
			flowLayout.setAlignment(FlowLayout.LEFT);
		}
		
		flowLayout.setVgap(v_gap);
		flowLayout.setHgap(h_gap);
		
		if(!sign.equals("all")) {
			JPanel bg = new JPanel();
			bg.setBackground(UITheme.background);
			bg.setPreferredSize(new Dimension(list.getWidth(), header_size));
			bg.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			add(bg);
		
			JLabel lblSign = new JLabel(sign);
			lblSign.setHorizontalAlignment(SwingConstants.CENTER);
			lblSign.setPreferredSize(new Dimension(header_size * 3, header_size));
			lblSign.setForeground(UITheme.foreground);
			bg.add(lblSign);
		
			JPanel line = new JPanel();
			line.setPreferredSize(new Dimension(80 * list.getWidth() / 100, 1));
			line.setBackground(UITheme.foreground);
			bg.add(line);
		}
	}

	/**
	 * Adds PasswordTile Object to section of the ListPanel.
	 * 
	 * @param pt PasswordTile to add
	 */
	public void addTile(PasswordTile pt) {
		calc();
		tileCounter++;
		add(pt);
	}
	
	public void closeList() {
		if(!empty)
		calc();
	}
	
	public void addDummy(PasswordTile pt) {
		tileCounter++;
		pt.setBackground(UITheme.transparent);
		add(pt);
		calc();
	}
	
	Dimension d;
	/**
	 * Calculates section dimensions.
	 */
	private void calc() {
		if(SettingsManager.listDisplay) {
			d = new Dimension(width,
					header_size + 1*v_gap + ((tileCounter / row_max) + 1) * (SettingsManager.tileDimension.height + v_gap));
		}else {
			d = new Dimension(width,
					header_size + 2*v_gap + ((tileCounter / row_max) + 1) * (SettingsManager.tileDimension.height + 2 * v_gap));
		}
		setSize(d);
		setPreferredSize(d);
	}

	public ListPanel getListPanel() {
		return this.listPanel;
	}
}
