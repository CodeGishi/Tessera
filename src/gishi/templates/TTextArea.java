package gishi.templates;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;

import com.alee.laf.text.WebTextArea;

import gishi.control.UITheme;
import gishi.util.DocumentSizeFilter;

@SuppressWarnings("serial")
public class TTextArea extends WebTextArea {

	/**
	 * Default project TextArea.
	 * 
	 */
	public TTextArea() {
		setLineWrap(true);
		setWrapStyleWord(true);
		setFont(UITheme.defaultFont);
		setBackground(UITheme.inputBackground);
		setForeground(UITheme.foreground);
		setCaretColor(UITheme.tileBorderColorHighlight);
		
		DefaultStyledDocument doc = new DefaultStyledDocument();
		doc.setDocumentFilter(new DocumentSizeFilter(150));
		doc.addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

			}

			@Override
			public void removeUpdate(DocumentEvent e) {

			}
		});
		setDocument(doc);
	}
}
