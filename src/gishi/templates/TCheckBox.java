package gishi.templates;

import com.alee.laf.checkbox.WebCheckBox;

import gishi.control.SettingsManager;
import gishi.control.UITheme;

@SuppressWarnings("serial")
public class TCheckBox extends WebCheckBox{
	
	/**
	 * Default project CheckBox.
	 * 
	 */
	public TCheckBox() {
		setRequestFocusEnabled(false);
		setBackground(UITheme.inputBackground);
		setAnimated(SettingsManager.getAllAnimationSettings());
	}
	public TCheckBox(String text) {
		setText(text);
		setRequestFocusEnabled(false);
		setBackground(UITheme.inputBackground);
		setAnimated(SettingsManager.getAllAnimationSettings());
	}
}
