package gishi.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextArea;

import com.alee.laf.panel.WebPanel;

import gishi.control.SettingsManager;
import gishi.control.UITheme;
import gishi.templates.ContentPanel;

@SuppressWarnings("serial")
public class MorePanel extends ContentPanel {

	int tileSize = 46;

	private WebPanel contentPane;
	static JTextArea textArea;

	/**
	 * Panel with Array of Interactive Tiles 
	 * 
	 * @param parent 
	 */
	public MorePanel(MainFrame parent) {

		setParentFrame(parent);

		contentPane = new WebPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				parent.setLastMouseActivity(System.currentTimeMillis());
			}
		});
		contentPane.setLayout(new FlowLayout());
		contentPane.setLocation(0, 0);
		contentPane.setSize(this.getSize());
		contentPane.setBackground(UITheme.background);
		add(contentPane);

		initLabels();
	}
	
	/**
	 * initialize array of interactive tiles.
	 * 20x13
	 * 
	 */
	private void initLabels() {
		contentPane.removeAll();
		for (int i = 0; i < 18; i++) {
			for (int j = 0; j < 13; j++) {
				MiniTile mt = new MiniTile();
				contentPane.add(mt);
			}
		}
		revalidate();
		repaint();
	}

	private class MiniTile extends WebPanel {
		/*
		 * ANIMATION RELATED
		 */
		private Timer colorTimer;
		private Timer clickTimer;
		private boolean animation = false;
		private boolean animationEnabled = SettingsManager.getAllAnimationSettings()
				&& SettingsManager.getTileAnimationSettings();
		private boolean mouseOnBtn = false;

		private MiniTile() {
			setSize(tileSize, tileSize);
			setPreferredSize(tileSize, tileSize);
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
					mouseOnBtn = false;
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if (animationEnabled)
						pauseColorTimer();
					setBorderColor(UITheme.tileBorderColorHighlight);
					startMouseTimer();
					setBackground(UITheme.tileHighlight);
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					setBackground(UITheme.tileBackground);
					if (animationEnabled)
						startColorTimer();
				}
			});

		}

		/**
		 * Method for changing tile color in time
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
				}, 0, 25);
			} else {
			}
		}

		public void pauseColorTimer() {
			if (mouseOnBtn == false) {
				setBackground(UITheme.tileBackground);
			}
			animation = false;
			colorTimer.cancel();
		}

		public void startMouseTimer() {
			clickTimer = new Timer();
			clickTimer.schedule(new TimerTask() {
				int counter = 0;
				int time_max = 15 * 4;

				public void run() {
					if (counter == time_max) {
						pauseMouseTimer();
					}
					counter++;
				}
			}, 0, 25);
		}

		public void pauseMouseTimer() {
			setBorderColor(UITheme.tileBorderColor);
			clickTimer.cancel();
		}
	}
}
