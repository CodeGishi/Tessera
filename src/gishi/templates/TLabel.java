package gishi.templates;

import com.alee.laf.label.WebLabel;

import gishi.control.UITheme;

@SuppressWarnings("serial")
public class TLabel extends WebLabel {

	/**
	 * Default project Label.
	 * 
	 */
	public TLabel() {
		setFont(UITheme.defaultFont);
		setForeground(UITheme.foreground);
	}

	public TLabel(String text) {
		setText(text);
		setFont(UITheme.defaultFont);
		setForeground(UITheme.foreground);
	}
	public TLabel(String text, boolean negate) {
		setText(text);
		setFont(UITheme.defaultFont);
		setForeground(UITheme.background);
	}
}
