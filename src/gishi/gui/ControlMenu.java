package gishi.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

import gishi.control.UITheme;
import gishi.templates.ContentPanel;
import gishi.templates.TFrame;
import gishi.util.Icons;

@SuppressWarnings("serial")
public class ControlMenu extends ContentPanel {

	private static final int button_size = 800 * UITheme.controlMenuHeight / 1000;
	private static final ImageIcon exitIcon = Icons.load("/icons/exit.png", button_size, 1);
	private static final ImageIcon iconizeIcon = Icons.load("/icons/minimize.png", button_size, 1);

	private TFrame parent = null;
	private String title = "";
	private Point initialClick = null;
	private int buttons = 1;
	private boolean frameMovement = false;

	/**
	 * Title bar and frame controls Minimize, Exit.
	 * 
	 * @param parent
	 */
	public ControlMenu(TFrame parent) {
		this.parent = parent;
		basicProperties();
	}

	public ControlMenu(TFrame parent, int options) {
		this.parent = parent;
		this.buttons = options;
		basicProperties();
	}

	public ControlMenu(TFrame parent, int options, boolean movement) {
		this.parent = parent;
		this.buttons = options;
		this.frameMovement = movement;
		basicProperties();
	}

	public ControlMenu(TFrame parent, int options, boolean movement, String title) {
		this.parent = parent;
		this.buttons = options;
		this.title = title;
		this.frameMovement = movement;
		basicProperties();
	}

	private void basicProperties() {
		setLocation(0, 0);
		setLayout(new BorderLayout(0, 0));
		setBackground(UITheme.menuBackground);
		setSize(parent.getWidth(), UITheme.controlMenuHeight);
		setPreferredSize(getSize());

		WebPanel buttonPanel = new WebPanel();

		buttonPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		flowLayout_1.setVgap(120 * button_size / 1000);
		flowLayout_1.setHgap(165 * button_size / 1000);
		buttonPanel.setLayout(flowLayout_1);
		buttonPanel.setBackground(UITheme.transparent);
		add(buttonPanel, BorderLayout.EAST);

		WebPanel panelWest = new WebPanel();
		panelWest.setBackground(UITheme.transparent);
		add(panelWest, BorderLayout.WEST);

		WebLabel lblTitle = new WebLabel();
		lblTitle.setText(title);
		lblTitle.setBackground(UITheme.transparent);
		panelWest.add(lblTitle);

		if (buttons == 1) {
			WebLabel exit = new WebLabel();
			exit.setIcon(exitIcon);
			exit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					((MainFrame) parent).pause();
					System.exit(0);
				}
			});
			buttonPanel.add(exit);
		} else {
			WebLabel iconize = new WebLabel();
			iconize.setIcon(iconizeIcon);
			iconize.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					parent.setState(Frame.ICONIFIED);
				}
			});
			buttonPanel.add(iconize);

			WebLabel exit = new WebLabel();
			exit.setIcon(exitIcon);
			exit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.exit(0);
				}
			});
			buttonPanel.add(exit);
		}
		if (frameMovement) {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					initialClick = e.getPoint();
				}
			});
			/*
			 * Enables window relocation
			 */
			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {

					// get location of Window
					int thisX = getLocationOnScreen().x;
					int thisY = getLocationOnScreen().y;

					// Determine how much the mouse moved since the initial click
					int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
					int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

					// Move window to this position
					int X = thisX + xMoved;
					int Y = thisY + yMoved;

					parent.setLocation(X, Y);
				}
			});
		}
	}

}
