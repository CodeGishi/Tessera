package gishi.templates;

import com.alee.laf.text.WebTextField;

import gishi.control.UITheme;

@SuppressWarnings("serial")
public class TTextField extends WebTextField {

	/**
	 * Default project TextField.
	 * 
	 */
	public TTextField() {
		setFont(UITheme.defaultFont);
		setForeground(UITheme.foreground);
		setBackground(UITheme.inputBackground);
		//setInputPromptForeground(UITheme.inputBackground);
		setCaretColor(UITheme.tileBorderColorHighlight);
		
		setRound(4);
		setColumns(10);
		setDrawShade(false);
		setDrawFocus(true);
		//setRequestFocusEnabled(isEnabled());
	}
}
