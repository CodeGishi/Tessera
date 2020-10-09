package gishi.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ScrollPaneConstants;

import com.alee.laf.scroll.WebScrollPane;

import gishi.control.PasswordManager;
import gishi.control.UITheme;
import gishi.templates.ContentPanel;

@SuppressWarnings("serial")
public class LibraryPanel extends ContentPanel {
	
	private final int scrollUnits = 100 * UITheme.frameHeight / 1000;
	
	private final ListPanel list;
	private final WebScrollPane scrollPane;
	private final ListNavigation navigation;
	private boolean initialized = false;
	
	
	/**
	 * Initialize Library Panel.
	 * Shows List of Password Tiles.
	 * 
	 * @param parent
	 */
	public LibraryPanel(MainFrame parent) {
		setParentFrame(parent);
		
		setLayout(new BorderLayout(0, 0));

		list = new ListPanel(this);
		list.addLoadingBar();
		
		scrollPane = new WebScrollPane(list);
		scrollPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				parent.setLastMouseActivity(System.currentTimeMillis());
			}
		});
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(scrollUnits);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);

		navigation = new ListNavigation(this);
		add(navigation, BorderLayout.NORTH);
	}
	
	public void setInitialized(boolean state) {
		this.initialized = state;
	}
	
	public void updateLibrary() {
		if(initialized) {
			list.listAlphabetically(PasswordManager.getActiveList(), 1);
			navigation.clearData();
		}
	}
	public ListPanel getList() {
		return list;
	}
}
