package gishi.templates;

import java.awt.Cursor;

import com.alee.laf.button.WebButton;

import gishi.control.SettingsManager;
import gishi.control.UITheme;

@SuppressWarnings("serial")
public class TButton extends WebButton {

	/**
	 * Default project button.
	 * 
	 */
	public TButton() {

		setRound(4);
		setFont(UITheme.defaultFont);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		setTopBgColor(UITheme.inputBackground);
		setBottomBgColor(UITheme.inputBackground);

		setTopSelectedBgColor(UITheme.inputBackground);
		setBottomSelectedBgColor(UITheme.inputBackground);
		
		setShineColor(UITheme.buttonShine);
		
		setDrawShade(false);
		setDrawFocus(false);
		setRolloverShine(SettingsManager.getAllAnimationSettings());
		setDrawSides(true, true, true, true);

		setRequestFocusEnabled(false);
		setMoveIconOnPress(false);
	}

	public TButton(String text) {

		setRound(4);
		setText(text);
		setFont(UITheme.defaultFont);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		setTopBgColor(UITheme.inputBackground);
		setBottomBgColor(UITheme.inputBackground);

		setTopSelectedBgColor(UITheme.inputBackground);
		setBottomSelectedBgColor(UITheme.inputBackground);
		
		setShineColor(UITheme.buttonShine);
		setForeground(UITheme.foreground);
		setSelectedForeground(UITheme.foreground);
		
		setDrawShade(false);
		setDrawFocus(false);
		setRolloverShine(SettingsManager.getAllAnimationSettings());
		setDrawSides(true, true, true, true);

		setRequestFocusEnabled(false);
		setMoveIconOnPress(false);
	}
}
