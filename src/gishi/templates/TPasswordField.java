package gishi.templates;

import com.alee.laf.text.WebPasswordField;

import gishi.control.UITheme;

@SuppressWarnings("serial")
public class TPasswordField extends WebPasswordField{
	
	/**
	 * Default project PasswordField.
	 * 
	 */
	public TPasswordField() {
		setRound(4);
		setDrawFocus(true);
		setDrawShade(false);
		setForeground(UITheme.foreground);
		setFont(UITheme.defaultFont);setBackground(UITheme.inputBackground);
		setCaretColor(UITheme.tileBorderColorHighlight);
	}
	
}
