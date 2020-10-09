package gishi.templates;

import java.awt.Dimension;
import java.awt.Point;

import com.alee.laf.panel.WebPanel;

import gishi.control.UITheme;
import gishi.gui.MainFrame;

@SuppressWarnings("serial")
public class ContentPanel extends WebPanel{
	
	private static final Point 	contentAnchor 		= new Point(UITheme.sideMenuWidth, UITheme.controlMenuHeight);
	private static final Dimension contentDimension = new Dimension(UITheme.frameWidth - UITheme.sideMenuWidth, UITheme.frameHeight - UITheme.controlMenuHeight);
	
	private MainFrame parent;
	
	public ContentPanel() {
		
		setSize(contentDimension);
		setPreferredSize(contentDimension);
		setLocation(contentAnchor);
		setBackground(UITheme.background);
		setLayout(null);
	}
	public void setParentFrame(TFrame parent) {
		this.parent = (MainFrame) parent;
	}
	public TFrame getParentFrame() {
		return parent;
	}
}
