package gishi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.alee.extended.button.WebSwitch;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.spinner.WebSpinner;
import com.alee.utils.swing.UnselectableButtonGroup;

import gishi.control.SettingsManager;
import gishi.control.UITheme;
import gishi.templates.ContentPanel;
import gishi.templates.TCombo;
import gishi.templates.TLabel;

@SuppressWarnings("serial")
public class SettingsPanel extends ContentPanel {

	private WebPanel contentPane;

	// AnimationIntro;
	// private OnOffSwitch ofs1;
	// AnimationAll;
	private OnOffSwitch ofs2;
	// AnimationTiles;
	private OnOffSwitch ofs3;
	// Theme
	private TCombo comboTheme;
	// Grid
	private WebRadioButton radioButtonA;
	// List
	private WebRadioButton radioButtonS;
	// Tile Count
	private WebSpinner spinner = new WebSpinner();
	// Time
	private WebRadioButton radioButton5;
	private WebRadioButton radioButton15;
	private WebRadioButton radioButton30;
	private WebRadioButton radioButton60;

	int v_gap;
	int last_field_y;
	private int left_column_x;
	private int right_column_x;
	private Dimension label_size;
	private Dimension field_size;
	private Dimension button_size;
	private Font fontSection = new Font("Tahoma", Font.PLAIN, 28 * UITheme.frameHeight / 1000);
	private Font fontLabel = new Font("Tahoma", Font.PLAIN, 25 * UITheme.frameHeight / 1000);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SettingsPanel(MainFrame parent) {

		setParentFrame(parent);

		contentPane = new WebPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				parent.setLastMouseActivity(System.currentTimeMillis());
			}
		});
		contentPane.setLayout(null);
		contentPane.setLocation(0, 0);
		contentPane.setSize(this.getSize());
		contentPane.setBackground(UITheme.background);
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		add(contentPane);

		button_size = new Dimension(80 * getWidth() / 1000, 25 * getWidth() / 1000);
		label_size = new Dimension(200 * getWidth() / 1000, 25 * getWidth() / 1000);
		field_size = new Dimension(220 * getWidth() / 1000, 25 * getWidth() / 1000);
		v_gap = 7 * this.getWidth() / 1000;

		last_field_y = 1 * getHeight() / 100;
		left_column_x = 30 * getWidth() / 100;
		right_column_x = left_column_x + label_size.width / 2 - 3 * v_gap;

		/*
		 * ANIMATIONS
		 */
		TLabel lblAnimation = new TLabel("animation");
		lblAnimation.setFont(fontSection);
		lblAnimation.setSize(label_size);
		lblAnimation.setPreferredSize(label_size);
		Point temp = nextLabelLocation();
		lblAnimation.setLocation(temp.x - 2 * v_gap, temp.y + 3 * v_gap);
		contentPane.add(lblAnimation);

		WebPanel panel_1 = new WebPanel();
		panel_1.setBackground(UITheme.foreground);
		panel_1.setSize(40 * this.getWidth() / 100, 1);
		panel_1.setPreferredSize(new Dimension(35 * this.getWidth() / 100, 1));
		panel_1.setLocation(lblAnimation.getLocation().x - v_gap,
				lblAnimation.getLocation().y + lblAnimation.getHeight() - 22 * lblAnimation.getHeight() / 100);
		last_field_y = panel_1.getLocation().y + panel_1.getHeight() + v_gap;
		contentPane.add(panel_1);

		TLabel lblAnimationAll = new TLabel("a l l         a n i m a t i o n ");
		lblAnimationAll.setFont(fontLabel);
		lblAnimationAll.setSize(label_size);
		lblAnimationAll.setPreferredSize(label_size);
		lblAnimationAll.setLocation(nextLabelLocation());
		contentPane.add(lblAnimationAll);

		ofs2 = new OnOffSwitch();
		ofs2.setLocation(nextFieldLocation());
		contentPane.add(ofs2);

		TLabel lblAnimationTiles = new TLabel("t i l e s     a n i m a t i o n ");
		lblAnimationTiles.setFont(fontLabel);
		lblAnimationTiles.setSize(label_size);
		lblAnimationTiles.setPreferredSize(label_size);
		lblAnimationTiles.setLocation(nextLabelLocation());
		contentPane.add(lblAnimationTiles);

		ofs3 = new OnOffSwitch();
		ofs3.setLocation(nextFieldLocation());
		contentPane.add(ofs3);
		/*
		 * THEME
		 */
		TLabel lblThemeSection = new TLabel("theme");
		lblThemeSection.setFont(fontSection);
		lblThemeSection.setSize(label_size);
		lblThemeSection.setPreferredSize(label_size);
		temp = nextLabelLocation();
		lblThemeSection.setLocation(temp.x - 2 * v_gap, temp.y + 3 * v_gap);
		contentPane.add(lblThemeSection);

		WebPanel panel_2 = new WebPanel();
		panel_2.setBackground(UITheme.foreground);
		panel_2.setSize(40 * this.getWidth() / 100, 1);
		panel_2.setPreferredSize(new Dimension(35 * this.getWidth() / 100, 1));
		panel_2.setLocation(lblThemeSection.getLocation().x - v_gap,
				lblThemeSection.getLocation().y + lblThemeSection.getHeight() - 22 * lblThemeSection.getHeight() / 100);
		last_field_y = panel_2.getLocation().y + panel_2.getHeight() + v_gap;
		contentPane.add(panel_2);

		TLabel lblTheme = new TLabel("d i s p l a y     t h e m e");
		lblTheme.setFont(fontLabel);
		lblTheme.setSize(label_size);
		lblTheme.setPreferredSize(label_size);
		lblTheme.setLocation(nextLabelLocation());
		contentPane.add(lblTheme);

		comboTheme = new TCombo();
		comboTheme.setFont(UITheme.boldFont);
		comboTheme.setModel(new DefaultComboBoxModel(new String[] { "light", "dark_red", "dark_blue" }));
		comboTheme.setSize(120 * button_size.width / 100, button_size.height);
		comboTheme.setPreferredSize(button_size);
		comboTheme.setLocation(nextFieldLocation());
		comboTheme.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {

			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				SettingsManager.setTheme(comboTheme.getSelectedItem().toString());
				UITheme.loadTheme();
				((MainFrame) getParentFrame()).refreshMainFrame();
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			}
		});
		contentPane.add(comboTheme);

		/*
		 * AUTOMATIC LOGOUT
		 */
		TLabel lblLogoutSection = new TLabel("automatioc logout");
		lblLogoutSection.setFont(fontSection);
		lblLogoutSection.setSize(label_size);
		lblLogoutSection.setPreferredSize(label_size);
		temp = nextLabelLocation();
		lblLogoutSection.setLocation(temp.x - 2 * v_gap, temp.y + 3 * v_gap);
		contentPane.add(lblLogoutSection);

		WebPanel panel_3 = new WebPanel();
		panel_3.setBackground(UITheme.foreground);
		panel_3.setSize(40 * this.getWidth() / 100, 1);
		panel_3.setPreferredSize(new Dimension(35 * this.getWidth() / 100, 1));
		panel_3.setLocation(lblLogoutSection.getLocation().x - v_gap, lblLogoutSection.getLocation().y
				+ lblLogoutSection.getHeight() - 22 * lblLogoutSection.getHeight() / 100);
		last_field_y = panel_3.getLocation().y + panel_3.getHeight() + v_gap;
		contentPane.add(panel_3);

		radioButton5 = new WebRadioButton(" 5 min.");
		radioButton5.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SettingsManager.setTimeToExit(5);
			}
		});
		radioButton5.setSelected(true);
		radioButton5.setAnimated(SettingsManager.getAllAnimationSettings());
		radioButton5.setForeground(UITheme.foreground);
		radioButton5.setSize(panel_3.getWidth() / 4, field_size.height);
		radioButton5.setLocation(nextLabelLocation());
		contentPane.add(radioButton5);

		radioButton15 = new WebRadioButton(" 15 min.");
		radioButton15.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SettingsManager.setTimeToExit(15);
			}
		});
		radioButton15.setAnimated(SettingsManager.getAllAnimationSettings());
		radioButton15.setForeground(UITheme.foreground);
		radioButton15.setSize(panel_3.getWidth() / 4, field_size.height);
		radioButton15.setLocation(radioButton5.getLocation().x + radioButton5.getWidth(), radioButton5.getLocation().y);
		contentPane.add(radioButton15);

		radioButton30 = new WebRadioButton(" 30 min.");
		radioButton30.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SettingsManager.setTimeToExit(30);
			}
		});
		radioButton30.setAnimated(SettingsManager.getAllAnimationSettings());
		radioButton30.setForeground(UITheme.foreground);
		radioButton30.setSize(panel_3.getWidth() / 4, field_size.height);
		radioButton30.setLocation(radioButton15.getLocation().x + radioButton15.getWidth(),
				radioButton15.getLocation().y);
		contentPane.add(radioButton30);

		radioButton60 = new WebRadioButton(" never ");
		radioButton60.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SettingsManager.setTimeToExit(52000000); // ~90 years
			}
		});
		radioButton60.setAnimated(SettingsManager.getAllAnimationSettings());
		radioButton60.setForeground(UITheme.foreground);
		radioButton60.setSize(panel_3.getWidth() / 4, field_size.height);
		radioButton60.setLocation(radioButton30.getLocation().x + radioButton30.getWidth(),
				radioButton30.getLocation().y);
		contentPane.add(radioButton60);

		UnselectableButtonGroup.group(radioButton5, radioButton15, radioButton30, radioButton60);

		/*
		 * TILES
		 */
		TLabel lblTiles = new TLabel("password tiles");
		lblTiles.setFont(fontSection);
		lblTiles.setSize(label_size);
		lblTiles.setPreferredSize(label_size);
		temp = nextLabelLocation();
		lblTiles.setLocation(temp.x - 2 * v_gap, temp.y + 3 * v_gap);
		contentPane.add(lblTiles);

		WebPanel panel_4 = new WebPanel();
		panel_4.setBackground(UITheme.foreground);
		panel_4.setSize(40 * this.getWidth() / 100, 1);
		panel_4.setPreferredSize(new Dimension(35 * this.getWidth() / 100, 1));
		panel_4.setLocation(lblTiles.getLocation().x - v_gap,
				lblTiles.getLocation().y + lblTiles.getHeight() - 22 * lblTiles.getHeight() / 100);
		last_field_y = panel_4.getLocation().y + panel_4.getHeight() + v_gap;
		contentPane.add(panel_4);

		TLabel lblTilesMode = new TLabel("t i l e s     d i s p l a y");
		lblTilesMode.setFont(fontLabel);
		lblTilesMode.setSize(label_size);
		lblTilesMode.setPreferredSize(label_size);
		lblTilesMode.setLocation(nextLabelLocation());
		contentPane.add(lblTilesMode);

		radioButtonA = new WebRadioButton(" grid");
		radioButtonA.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				spinner.setValue(4);
				spinner.setEnabled(true);
			}
		});
		radioButtonA.setSelected(true);
		radioButtonA.setAnimated(SettingsManager.getAllAnimationSettings());
		radioButtonA.setForeground(UITheme.foreground);
		radioButtonA.setSize(button_size.width, field_size.height);
		// radioButtonA.setLocation(lblTilesMode.getLocation().x +
		// lblTilesMode.getWidth() + 12*v_gap, lblTilesMode.getLocation().y);
		radioButtonA.setLocation(nextFieldLocation());
		contentPane.add(radioButtonA);

		radioButtonS = new WebRadioButton(" list");
		radioButtonS.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				spinner.setValue(1);
				spinner.setEnabled(false);
			}
		});
		radioButtonS.setAnimated(SettingsManager.getAllAnimationSettings());
		radioButtonS.setForeground(UITheme.foreground);
		radioButtonS.setSize(button_size.width, field_size.height);
		nextLabelLocation();
		radioButtonS.setLocation(nextFieldLocation());
		contentPane.add(radioButtonS);

		UnselectableButtonGroup.group(radioButtonA, radioButtonS);

		TLabel lblTilesCount = new TLabel("t i l e s     in   r o w");
		lblTilesCount.setFont(fontLabel);
		lblTilesCount.setSize(label_size);
		lblTilesCount.setPreferredSize(label_size);
		lblTilesCount.setLocation(nextLabelLocation());
		contentPane.add(lblTilesCount);

//spinner		
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateTileGrid();
			}
		});
		spinner.setBackground(UITheme.background);
		spinner.setForeground(UITheme.foreground);
		spinner.setModel(new SpinnerNumberModel(4, 3, 5, 1));
		spinner.setSize(button_size);
		spinner.setLocation(nextFieldLocation());
		contentPane.add(spinner);

		loadSettings();
	}

	private void loadSettings() {
//		if(SettingsManager.getStartAnimationSettings()) {
//			ofs1.ws.setSelected(true);
//		}
//		else {
//			ofs1.ws.setSelected(false);
//		}
		if (SettingsManager.getAllAnimationSettings()) {
			ofs2.ws.setSelected(true);
			if (SettingsManager.getTileAnimationSettings()) {
				ofs3.ws.setSelected(true);
			} else {
				ofs3.ws.setSelected(false);
			}
		} else {
			ofs2.ws.setSelected(false);
			ofs3.ws.setSelected(false);
			ofs3.ws.setEnabled(false);
		}

		comboTheme.setSelectedItem(UITheme.theme);

		if (SettingsManager.getTimeToExit() == 15) {
			radioButton15.setSelected(true);
		}
		if (SettingsManager.getTimeToExit() == 30) {
			radioButton30.setSelected(true);
		}
		if (SettingsManager.getTimeToExit() == 60) {
			radioButton60.setSelected(true);
		}

		if (SettingsManager.listDisplay) {
			radioButtonA.setSelected(false);
			radioButtonS.setSelected(true);
			spinner.setEnabled(false);
			spinner.setValue(1);
		} else if (!SettingsManager.listDisplay) {
			if (SettingsManager.tileDimension.width == 290 * UITheme.frameWidth / 1000) {
				spinner.setValue(3);
			} else if (SettingsManager.tileDimension.width == 220 * UITheme.frameWidth / 1000) {
				spinner.setValue(4);
			} else if (SettingsManager.tileDimension.width == 175 * UITheme.frameWidth / 1000) {
				spinner.setValue(5);
			}
		} else {

		}
	}

	private void updateTileGrid() {
		if (spinner.getValue().toString().equals("1")) {
			SettingsManager.listDisplay = true;
			SettingsManager.tileDimension = new Dimension(820 * UITheme.frameWidth / 1000,
					35 * UITheme.frameHeight / 1000); // 1
		} else if (spinner.getValue().toString().equals("3")) {
			SettingsManager.listDisplay = false;
			SettingsManager.tileDimension = new Dimension(290 * UITheme.frameWidth / 1000,
					110 * UITheme.frameHeight / 1000); // 3
		} else if (spinner.getValue().toString().equals("4")) {
			SettingsManager.listDisplay = false;
			SettingsManager.tileDimension = new Dimension(220 * UITheme.frameWidth / 1000,
					110 * UITheme.frameHeight / 1000); // 4
		} else if (spinner.getValue().toString().equals("5")) {
			SettingsManager.listDisplay = false;
			SettingsManager.tileDimension = new Dimension(175 * UITheme.frameWidth / 1000,
					110 * UITheme.frameHeight / 1000); // 5

		}
		((LibraryPanel) ((MainFrame) getParentFrame()).getLibraryPanel()).updateLibrary();
		SettingsManager.saveSettings();
	}

	private class OnOffSwitch extends WebPanel {
		WebSwitch ws = new WebSwitch();

		private OnOffSwitch() {
			setBackground(UITheme.background);
			setSize(button_size);
			setLayout(new BorderLayout(0, 0));

			ws.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == (ofs2.ws) && SettingsManager.getAllAnimationSettings() != ws.isSelected()) {// all
						SettingsManager.setAllAnimationSettings(ws.isSelected());
						ofs3.ws.setSelected(ws.isSelected());
						ofs3.ws.setEnabled(ws.isSelected());
						SettingsManager.setTileAnimationSettings(ws.isSelected());
						((MainFrame) getParentFrame()).setCreateState(false);
						((LibraryPanel) ((MainFrame) getParentFrame()).getLibraryPanel()).updateLibrary();
					} else if (e.getSource() == (ofs3.ws)
							&& SettingsManager.getTileAnimationSettings() != ws.isSelected()) {// tile
						SettingsManager.setTileAnimationSettings(ws.isSelected());
						((LibraryPanel) ((MainFrame) getParentFrame()).getLibraryPanel()).updateLibrary();
					}
				}
			});
			ws.getGripper().setRound(4);
			ws.getGripper().setShadeWidth(0);
			ws.getGripper().setBackground(UITheme.background2);
			ws.getGripper().setWebColoredBackground(false);
			ws.setWebColoredBackground(false);
			ws.setBorderColor(UITheme.background);
			add(ws);
		}
	}

	private Point nextLabelLocation() {
		last_field_y += field_size.height + v_gap;
		return new Point(left_column_x, last_field_y);
	}

	private Point nextFieldLocation() {
		return new Point(right_column_x + label_size.width + v_gap, last_field_y);
	}
}
