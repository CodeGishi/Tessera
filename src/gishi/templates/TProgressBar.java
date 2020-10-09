package gishi.templates;

import java.awt.Point;

import com.alee.laf.progressbar.WebProgressBar;

import gishi.control.SettingsManager;
import gishi.control.UITheme;

@SuppressWarnings("serial")
public class TProgressBar extends WebProgressBar{

	/**
	 * Default project ProgressBar.
	 * 
	 */
	public TProgressBar(){
		
		setShadeWidth(0);
		setInnerRound(4);
		
		setStringPainted(true);
		setForeground(UITheme.foreground);
		
		setBgTop(UITheme.inputBackground);
		setBgBottom(UITheme.inputBackground);
		setProgressTopColor(UITheme.background);
		setProgressBottomColor(UITheme.background);
		if(SettingsManager.getAllAnimationSettings()) {
			setHighlightDarkWhite(UITheme.inputBackground);
		}else {
			setHighlightDarkWhite(UITheme.background);
		}
	}
	
	public void setBarLocation(int x, int y) {
		this.setLocation(new Point(x+2, y+2));
	}
	
}
