package gishi.templates;

import javax.swing.DefaultComboBoxModel;

import com.alee.laf.combobox.WebComboBox;

import gishi.control.UITheme;
import gishi.data.PasswordCategory;

@SuppressWarnings("serial")
public class TCombo extends WebComboBox{

	/**
	 * Default project ComboBox.
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TCombo() {
		setRound(4);
		setDrawFocus(true);
		setFont(UITheme.defaultFont);
		setBackground(UITheme.inputBackground);
		setForeground(UITheme.foreground);
		setExpandedBgColor(UITheme.inputBackground);
		setWebColoredBackground(false);
		setRequestFocusEnabled(false);
		//new String[] {"all", "email"}
		setModel(new DefaultComboBoxModel(PasswordCategory.values()));
		//setModel(new DefaultComboBoxModel(PasswordCategory.values()));
		
		/*
		addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
				
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				
			}
		});
		*/
		
	}
	
	public PasswordCategory getSelectedCategory() {
		return (PasswordCategory.valueOf(this.getSelectedItem().toString()));
	}
}
