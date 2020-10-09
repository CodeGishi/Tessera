package gishi.templates;

import java.awt.Cursor;

import gishi.control.UITheme;
import gishi.gui.ListNavigation;
import gishi.util.Icons;

@SuppressWarnings("serial")
public class NavigationButton extends TButton{

	/**
	 * Button template for ListNavigation.
	 */
	public NavigationButton() {
		setRound(4);
		setFont(UITheme.defaultFont);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		setSize(35*(UITheme.frameWidth - UITheme.sideMenuWidth)/1000, 35*(UITheme.frameWidth - UITheme.sideMenuWidth)/1000);
		setPreferredSize(this.getSize());
		
		setForeground(UITheme.foreground);
		setTopBgColor(UITheme.inputBackground);
		setBottomBgColor(UITheme.inputBackground);
		setTopSelectedBgColor(UITheme.inputBackground);
		setBottomSelectedBgColor(UITheme.inputBackground);
		
		setDrawShade(false);
		setShadeWidth(2);
		setDrawFocus(false);
		setRolloverShine(true);
		setDrawSides(true, true, true, true);
		
		setRequestFocusEnabled(false);
		setMoveIconOnPress(true);
	}
	
	public int verticalCenter(ListNavigation nav) {
		return nav.getHeight()/2 - this.getHeight()/2;
	}
	
	public void addIcon(String icon) {
		setIcon(Icons.load(UITheme.themeIconPath + icon, this.getWidth(), 0.65));
	}
	
	public void highlight() {
		
		if(UITheme.theme.equals("light")) {
			setDrawShade(true);
			setShadeColor(UITheme.tileBorderColorHighlight);
		}
		else {
			setShadeColor(UITheme.foreground);
			setDrawShade(true);
		}
		setDrawShade(true);
		repaint();
	}
	
	public void dropHighlight() {
		if(UITheme.theme.equals("light")) {
			setDrawShade(false);
			//setShadeColor(getDefaultButtonShadeColor());
			repaint();
		}else {
			setDrawShade(false);
		}
		repaint();
	}
}
