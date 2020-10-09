package gishi.templates;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;

import com.alee.laf.panel.WebPanel;

import gishi.control.PasswordManager;
import gishi.control.SettingsManager;
import gishi.control.UITheme;
import gishi.gui.MainFrame;
import gishi.util.Icons;

@SuppressWarnings("serial")
public class PasswordTile extends WebPanel {
	/*
	 * ANIMATION RELATED
	 */
	private Timer colorTimer;
	private Timer clickTimer;
	private int timerSpeed = 25;
	private int clickMaxInterval = 10;
	private boolean animation = false;
	private boolean animationEnabled = SettingsManager.getAllAnimationSettings()
			&& SettingsManager.getTileAnimationSettings();
	//private boolean firstClick = false;
	private boolean mouseOnBtn = false;
	/*
	 * PASSWORD TILE LOOKS
	 */
	private static int iconSize; // = 258*SettingsManager.tileDimension.width/1000; //ICON SCALE: 0.95
	private static int favSize; // = 70*SettingsManager.tileDimension.width/1000;
	/*
	 * PASSWORD TILE FIELDS
	 */
	private TLabel lblIcon;
	private TLabel lblName;
	private TLabel lblFav;

	private int pswdID = 0;

	/**
	 * Visual Library Component containing stored Password data.
	 * 
	 * @param pswdID ID of the password
	 */
	public PasswordTile(int pswdID) {

		this.pswdID = pswdID;
		setSize(SettingsManager.tileDimension);
		setPreferredSize(SettingsManager.tileDimension);
		setBackground(UITheme.tileBackground);
		setBorderColor(UITheme.tileBorderColor);
		setWebColoredBackground(false);

		setUndecorated(false);
		setLayout(null);
		setPaintFocus(true);
		setShadeWidth(0);
		setRound(4);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (mouseOnBtn) {

				} else {
					mouseOnBtn = true;
				}
				if (animationEnabled) {
					startColorTimer();
				} else {
					setBackground(UITheme.tileHighlight);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(UITheme.tileBackground);
				setBorderColor(UITheme.tileBorderColor);
				mouseOnBtn = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (animationEnabled)
					pauseColorTimer();
				
				setBorderColor(UITheme.tileBorderColorHighlight);
				setBackground(UITheme.tileHighlight);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(UITheme.tileBackground);
				setBorderColor(UITheme.tileBorderColor);
				if (animationEnabled)
					startColorTimer();
				openEditPanel();
			}
		});

		if (!SettingsManager.listDisplay) {
			iconSize = 855 * SettingsManager.tileDimension.height / 1000; // ICON SCALE: 0.95
			favSize = 232 * SettingsManager.tileDimension.height / 1000;
// Tile Icon
			lblIcon = new TLabel();
			lblIcon.setIcon(Icons.load(PasswordManager.getPassword(pswdID).getIconPath(), iconSize, 0.95));
			// System.out.println("PATH tile gets: " +
			// PasswordManager.getPassword(pswdID).getIconPath());

			lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
			lblIcon.setBounds(this.getHeight() / 2 - iconSize / 2, this.getHeight() / 2 - iconSize / 2, iconSize,
					iconSize);
			add(lblIcon);
// StorageName 	
			lblName = new TLabel();
			lblName.setText(PasswordManager.getPassword(pswdID).getStoragedName());
			lblName.setHorizontalAlignment(SwingConstants.LEFT);
			lblName.setFont(UITheme.boldFont);
			lblName.setBounds(lblIcon.getLocation().x + 11 * iconSize / 10, this.getHeight() / 2 - iconSize / 2,
					3 * iconSize, iconSize);
			add(lblName);
// FavouriteButton 
			lblFav = new TLabel();
			lblFav.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (PasswordManager.getPassword(pswdID).getFav() <= 0) {
						lblFav.setEnabled(true);
						PasswordManager.getPassword(pswdID).setFav(1);
						PasswordManager.savePassword(PasswordManager.getPassword(pswdID));
						PasswordManager.updateActiveList();
						getTileSection().getListPanel().getLibraryPanel().updateLibrary();
					} else {
						lblFav.setEnabled(false);
						PasswordManager.getPassword(pswdID).setFav(0);
						PasswordManager.savePassword(PasswordManager.getPassword(pswdID));
						PasswordManager.updateActiveList();
						getTileSection().getListPanel().getLibraryPanel().updateLibrary();
					}
				}
			});
			if (PasswordManager.getPassword(pswdID).getFav() > 0) {
				lblFav.setEnabled(true);
			} else {
				lblFav.setEnabled(false);
			}
			lblFav.setIcon(Icons.load(UITheme.themeIconPath + "heart.png", favSize, 1));
			lblFav.setBounds(this.getWidth() - 1500 * favSize / 1000, 0 + favSize / 2, favSize, favSize);
			add(lblFav);
		} else if (SettingsManager.listDisplay) {
			iconSize = 25 * SettingsManager.tileDimension.width / 1000; // ICON SCALE: 0.95
			favSize = 20 * SettingsManager.tileDimension.width / 1000;
// Tile Icon
//			lblIcon = new TLabel();
//			lblIcon.setText("ID "+PasswordManager.getPassword(pswdID).getId());
//			lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
//			lblIcon.setBounds(this.getHeight() / 2 - iconSize / 2, this.getHeight() / 2 - iconSize / 2, iconSize, iconSize);
//			add(lblIcon);
// StorageName 	
			lblName = new TLabel();
			lblName.setText(PasswordManager.getPassword(pswdID).getStoragedName());
			lblName.setHorizontalAlignment(SwingConstants.LEFT);
			lblName.setFont(UITheme.boldFont);
			lblName.setBounds(this.getHeight() - iconSize / 2, this.getHeight() / 2 - iconSize / 2, 15 * iconSize,
					iconSize);
			add(lblName);
// FavouriteButton 
			lblFav = new TLabel();
			lblFav.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (PasswordManager.getPassword(pswdID).getFav() <= 0) {
						lblFav.setEnabled(true);
						PasswordManager.getPassword(pswdID).setFav(1);
						PasswordManager.savePassword(PasswordManager.getPassword(pswdID));
						PasswordManager.updateActiveList();
						getTileSection().getListPanel().getLibraryPanel().updateLibrary();
					} else {
						lblFav.setEnabled(false);
						PasswordManager.getPassword(pswdID).setFav(0);
						PasswordManager.savePassword(PasswordManager.getPassword(pswdID));
						PasswordManager.updateActiveList();
						getTileSection().getListPanel().getLibraryPanel().updateLibrary();
					}
				}
			});
			if (PasswordManager.getPassword(pswdID).getFav() > 0) {
				lblFav.setEnabled(true);
			} else {
				lblFav.setEnabled(false);
			}
			lblFav.setIcon(Icons.load(UITheme.themeIconPath + "heart.png", favSize, 1));
			lblFav.setBounds(getWidth() - 1500 * favSize / 1000, getHeight() / 2 - favSize / 2, favSize, favSize);
			add(lblFav);
		}
	}

	
	static private MainFrame mf;
	/**
	 * Show EditPanel.
	 */
	private void openEditPanel() {
		mf = (MainFrame) (((ListSection) this.getParent()).getListPanel().getLibraryPanel().getParentFrame());
		mf.swapLeft(mf.getContentPane(), mf.getActivePanel(), mf.getEditPanel(pswdID));
	}

	/**
	 * Color changing animation on tile. Color dependent on the user settings.
	 * 
	 */
	public void startColorTimer() {
		int steps;
		if (!animation) {
			animation = true;
			if (UITheme.theme.equals("dark_blue") || UITheme.theme.equals("dark_red")) {
				steps = 20;
			} else {
				steps = 255 - getBackground().getRed() - 1;
			}
			colorTimer = new Timer();
			colorTimer.schedule(new TimerTask() {
				int counter = 0;
				int r = getBackground().getRed();
				int g = getBackground().getGreen();
				int b = getBackground().getBlue();

				public void run() {
					if (UITheme.theme.equals("dark_blue")) {
						if (counter % 2 == 0)
							setBackground(new Color((r -= 1) % 256, (g += 1) % 256, (b += 2) % 256, 55));
						else
							setBackground(new Color((r += 0) % 256, (g += 0) % 256, (b += 2) % 256, 55));
					}
					if (UITheme.theme.equals("dark_red")) {
						setBackground(new Color((r += 5) % 256, (g += 1) % 256, (b += 3) % 256, 55));
					} else {
						setBackground(new Color((r += 1) % 256, (g += 1) % 256, (b += 1) % 256));
					}
					if (counter == steps) {
						pauseColorTimer();
					}
					counter++;
				}
			}, 0, timerSpeed);
		} else {
		}
	}

	/**
	 * Ends color animation.
	 */
	public void pauseColorTimer() {
		if (mouseOnBtn == false) {
			setBackground(UITheme.tileBackground);
		}
		animation = false;
		colorTimer.cancel();
	}
	
	/**
	 * Mouse timer for detecting double clicking.
	 * 
	 * @apiNote disabled
	 */
	public void startMouseTimer() {
		clickTimer = new Timer();
		clickTimer.schedule(new TimerTask() {
			int counter = 0;
			int time_max = clickMaxInterval * 4;

			public void run() {
				if (counter == time_max) {
					pauseMouseTimer();
				}
				counter++;
			}
		}, 0, timerSpeed);
	}
	
	/**
	 * Mouse timer for detecting double clicking.
	 * Time run out.
	 * 
	 * @apiNote disabled
	 */
	public void pauseMouseTimer() {
		//firstClick = false;
		setBorderColor(UITheme.tileBorderColor);
		clickTimer.cancel();
	}

	public ListSection getTileSection() {
		return (ListSection) this.getParent();
	}
}

//pink tint on tile
/*
 * PT Tint pink tint: pnl.setBackground(new Color((r += 0) % 256, (g -= 1) %
 * 256, (b += 1) % 256)); || pnl.setBackground(new Color((r += 1) % 256, (g -=
 * 1) % 256, (b += 1) % 256)); blue tint: pnl.setBackground(new Color((r -= 1) %
 * 256, (g -= 1) % 256, (b += 1) % 256)); ocen tint: pnl.setBackground(new
 * Color((r -= 1) % 256, (g += 1) % 256, (b += 1) % 256)); mint tint:
 * pnl.setBackground(new Color((r -= 1) % 256, (g += 1) % 256, (b -= 1) % 256));
 * lime tint: pnl.setBackground(new Color((r -= 0) % 256, (g += 1) % 256, (b -=
 * 1) % 256)); || pnl.setBackground(new Color((r += 1) % 256, (g += 1) % 256, (b
 * -= 1) % 256));
 */