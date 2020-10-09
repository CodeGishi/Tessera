package gishi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.alee.laf.panel.WebPanel;

import gishi.control.PasswordManager;
import gishi.control.UITheme;
import gishi.data.PasswordCategory;
import gishi.templates.NavigationButton;
import gishi.templates.TButton;
import gishi.templates.TCombo;
import gishi.templates.TLabel;
import gishi.templates.TTextField;

@SuppressWarnings("serial")
public class ListNavigation extends WebPanel {

	private int h_gap = 10;
	private Dimension field_size = new Dimension(50 * (UITheme.frameWidth - UITheme.sideMenuWidth) / 1000,
			30 * (UITheme.frameWidth - UITheme.sideMenuWidth) / 1000);

	@SuppressWarnings("unused")
	private Dimension button_size;
	private int direction = 1;

	private LibraryPanel parentt;

	private TButton btnUp;
	private TButton btnDown;
	private TButton btnFav;
	private TButton btnAll;
	private TButton btnAll2;
	private TCombo comboBox;
	private TTextField txtfldSearch;
	TLabel labell;
	Timer timer;
	
	/**
	 * ListPanel Navigation Components.
	 * 
	 * @param parent
	 */
	public ListNavigation(LibraryPanel parent) {
		this.parentt = parent;
		setSize(parentt.getWidth(), 55 * parentt.getWidth() / 1000);
		setPreferredSize(parentt.getWidth(), 55 * parentt.getWidth() / 1000);
		setLayout(new BorderLayout(0, 0));
		setBackground(UITheme.background2);
		
		showControls(true);
		direction = 1;
		
		/*
		 * TODO CountDownLatch : use await on latch
		 */
		startTiming(200); // list loading, prevents form errors //
	}

	
	/**
	 * ListPanel Navigation Components.
	 * 
	 * @param parent
	 * @param time
	 */
	public ListNavigation(JPanel parent, int time) {
		this.parentt = (LibraryPanel) parent;
		field_size = new Dimension(100 * parentt.getWidth() / 1000, 25 * parentt.getWidth() / 1000);
		button_size = new Dimension(25 * parentt.getWidth() / 1000, 25 * parentt.getWidth() / 1000);
		setSize(parentt.getWidth(), 55 * parentt.getWidth() / 1000);
		setPreferredSize(parentt.getWidth(), 55 * parentt.getWidth() / 1000);
		setLayout(new BorderLayout(0, 0));
		setBackground(UITheme.background2);
		
		showControls(true);
		direction = 1;
		
		/*
		 * TODO CountDownLatch : use await on latch
		 */
		startTiming(time); // list loading, prevents form errors //
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void showControls(boolean state) {
		WebPanel listControl = new WebPanel();
		listControl.setVisible(state);
		listControl.setLayout(null);
		listControl.setPreferredSize(this.getSize());
		listControl.setBackground(UITheme.background2);
		add(listControl, BorderLayout.WEST);
		{
			TLabel lblCategory = new TLabel("");
			lblCategory.setFont(UITheme.boldFont);
			lblCategory.setSize(field_size);
			lblCategory.setLocation(10, 0);
			listControl.add(lblCategory);

			comboBox = new TCombo();
			comboBox.setDrawFocus(false);
			comboBox.setFont(UITheme.enlargedFont);
			comboBox.setEnabled(false);
			comboBox.setSize(field_size.width * 2, field_size.height);
			// comboBox.setLocation(lblCategory.getLocation().x, lblCategory.getLocation().y
			// + field_size.height);
			comboBox.setLocation(10, this.getHeight() / 2 - comboBox.getHeight() / 2);
			comboBox.setModel(new DefaultComboBoxModel(PasswordCategory.values()));
			comboBox.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) {
				}

				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					txtfldSearch.setText("Search...");
					txtfldSearch.setFocusable(false);
					parentt.getList().listAlphabetically(
							PasswordManager.getSubList((PasswordCategory) comboBox.getSelectedItem()), direction);
					txtfldSearch.setFocusable(true);
				}

				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				}
			});
			comboBox.setSelectedIndex(0);
			listControl.add(comboBox);

			TLabel lblSearch = new TLabel("");
			lblSearch.setSize(field_size);
			lblSearch.setFont(UITheme.boldFont);
			lblSearch.setLocation(comboBox.getLocation().x + comboBox.getWidth() + h_gap, comboBox.getLocation().y);
			listControl.add(lblSearch);

			txtfldSearch = new TTextField();
			txtfldSearch.setDrawFocus(false);
			txtfldSearch.setDrawShade(true);
			txtfldSearch.setEnabled(false);
			txtfldSearch.setSize(45 * field_size.width / 10, field_size.height);
			txtfldSearch.setLocation(lblSearch.getLocation().x, lblSearch.getLocation().y);
			txtfldSearch.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					comboBox.setSelectedIndex(0);
					parentt.getList().listAlphabetically(PasswordManager.getSubList(txtfldSearch.getText()), direction);
				}
			});
			txtfldSearch.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					txtfldSearch.setText("");
				}
			});
			txtfldSearch.setText("Search...\r\n");
			listControl.add(txtfldSearch);

			TLabel lblControls = new TLabel("");
			lblControls.setSize(field_size);
			lblControls.setFont(UITheme.boldFont);
			lblControls.setLocation(txtfldSearch.getLocation().x + txtfldSearch.getWidth() + h_gap,
					lblSearch.getLocation().y);
			listControl.add(lblControls);

			btnUp = new NavigationButton();
			btnUp.setEnabled(false);
			((NavigationButton) btnUp).highlight();
			btnUp.setLocation(lblControls.getLocation().x + h_gap, ((NavigationButton) btnUp).verticalCenter(this));
			((NavigationButton) btnUp).addIcon("ascending.png");
			btnUp.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					highlightActiveOrder(e);
					direction = 1;
					parentt.getList().listAlphabetically(PasswordManager.getActiveList(), direction);
				}
			});
			listControl.add(btnUp);

			btnDown = new NavigationButton();
			btnDown.setEnabled(false);
			btnDown.setLocation(btnUp.getLocation().x + btnUp.getWidth(),
					((NavigationButton) btnUp).verticalCenter(this));
			((NavigationButton) btnDown).addIcon("descending.png");
			btnDown.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					highlightActiveOrder(e);
					direction = -1;
					parentt.getList().listAlphabetically(PasswordManager.getActiveList(), direction);
				}
			});
			listControl.add(btnDown);

			TLabel lblSeparator1 = new TLabel("");
			lblSeparator1.setSize(btnDown.getSize());
			lblSeparator1.setFont(UITheme.defaultFont);
			lblSeparator1.setHorizontalAlignment(SwingConstants.CENTER);
			lblSeparator1.setLocation(btnDown.getLocation().x + btnDown.getWidth() / 3,
					((NavigationButton) btnUp).verticalCenter(this));
			listControl.add(lblSeparator1);

			btnAll = new NavigationButton();
			// btnAll.setText("all");
			btnAll.setEnabled(false);
			// ((NavigationButton) btnAll).highlight();
			((NavigationButton) btnAll).addIcon("reload.png");
			// btnAll.setSize(btnUp.getWidth()*2, btnUp.getHeight());
			btnAll.setLocation(lblSeparator1.getLocation().x + lblSeparator1.getWidth(),
					((NavigationButton) btnUp).verticalCenter(this));
			btnAll.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					clearData();

					highlightActiveOrder(e);
					PasswordManager.updateActiveList();
					parentt.getList().listAlphabetically(PasswordManager.getMainList(), direction);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
			});
			listControl.add(btnAll);

			btnAll2 = new NavigationButton();
			btnAll2.setEnabled(false);
			((NavigationButton) btnAll2).addIcon("star.png");
			btnAll2.setLocation(btnAll.getLocation().x + btnAll.getWidth(),
					((NavigationButton) btnUp).verticalCenter(this));
			btnAll2.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					clearData();
					
					highlightActiveOrder(e);
					parentt.getList().listAll(PasswordManager.getMainList(), direction);
				}
			});
			listControl.add(btnAll2);

			btnFav = new NavigationButton();
			btnFav.setEnabled(false);
			((NavigationButton) btnFav).addIcon("heart.png");
			btnFav.setLocation(btnAll2.getLocation().x + btnAll2.getWidth(),
					((NavigationButton) btnUp).verticalCenter(this));
			btnFav.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					clearData();
					highlightActiveOrder(e);
					parentt.getList().listAlphabetically(PasswordManager.getFavourites(), direction);
				}
			});
			listControl.add(btnFav);

		} // CONTROLS END
	}

	private void highlightActiveOrder(MouseEvent e) {
		if (e.getSource() == btnUp) {
			((NavigationButton) btnUp).highlight();
			((NavigationButton) btnDown).dropHighlight();
			((NavigationButton) btnAll2).dropHighlight();
		} else if (e.getSource() == btnDown) {
			((NavigationButton) btnDown).highlight();
			((NavigationButton) btnUp).dropHighlight();
			((NavigationButton) btnAll2).dropHighlight();
		} else if (e.getSource() == btnFav) {
			((NavigationButton) btnFav).highlight();
			((NavigationButton) btnAll2).dropHighlight();
		} else if (e.getSource() == btnAll2) {
			((NavigationButton) btnAll2).highlight();
			((NavigationButton) btnUp).dropHighlight();
			((NavigationButton) btnDown).dropHighlight();
			((NavigationButton) btnFav).dropHighlight();
		} else if (e.getSource() == btnAll) {
			((NavigationButton) btnUp).highlight();
			((NavigationButton) btnDown).dropHighlight();
			((NavigationButton) btnFav).dropHighlight();
			((NavigationButton) btnAll2).dropHighlight();
		}
	}

	public void clearData() {
		requestFocus();
		direction = 1;
		comboBox.setSelectedIndex(0);
		txtfldSearch.setText("Search...");
		((NavigationButton) btnUp).highlight();
		((NavigationButton) btnDown).dropHighlight();
		((NavigationButton) btnFav).dropHighlight();
		((NavigationButton) btnAll2).dropHighlight();
	}

	public void startTiming(int time) {
		parentt.getList().addLoadingBar();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			int counter = 0;

			public void run() {
				if (counter == time) {
					parentt.setInitialized(true);
					comboBox.setEnabled(true);
					txtfldSearch.setEnabled(true);
					btnUp.setEnabled(true);
					btnDown.setEnabled(true);
					btnFav.setEnabled(true);
					btnAll.setEnabled(true);
					btnAll2.setEnabled(true);
					parentt.getList().listAlphabetically(PasswordManager.getMainList(), direction);
					pause();
				}
				counter++;
			}
		}, 0, 10);
	}

	public void pause() {
		timer.cancel();
	}
}
