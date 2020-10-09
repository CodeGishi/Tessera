package gishi.templates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.alee.laf.panel.WebPanel;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

import gishi.control.SettingsManager;
import gishi.control.UITheme;

@SuppressWarnings("serial")
public class MenuButton extends WebPanel {

	private boolean mouseOnBtn = false;
	private boolean animation = false;
	private Timer colorTimer;
	
	public MenuButton(ImageIcon icon) {
		//TooltipManager.setTooltip ( this, tooltip, TooltipWay.trailing, 1000 );

		setPaintBackground(false);
		setWebColoredBackground(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (mouseOnBtn) {
				} else {
					mouseOnBtn = true;
				}
				if(SettingsManager.getAllAnimationSettings()) {
					startColorTimer();
				}else {
					setBackground(UITheme.menuButtonHighlight);
					repaint();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(SettingsManager.getAllAnimationSettings()) {
					pauseColorTimer();
				}
				setBackground(UITheme.menuBackground);
				mouseOnBtn = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(SettingsManager.getAllAnimationSettings()) {
					pauseColorTimer();
				}
				setBackground(UITheme.menuButtonHighlight);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(UITheme.menuBackground);
			}
		});
		
		setBackground(UITheme.menuBackground);
		setSize(UITheme.sideMenuWidth, UITheme.sideMenuWidth);
		setPreferredSize(this.getSize());
		setBorder(null);
		
		JLabel Icon = new JLabel();
		Icon.setIcon(icon);
		Icon.setHorizontalAlignment(SwingConstants.CENTER);
		add(Icon, BorderLayout.CENTER);
	}
	public void setTooltipText(String tooltip) {
		//this.tooltip = tooltip;
		TooltipManager.setTooltip ( this, tooltip, TooltipWay.trailing, 1000 );
	}
	public void startColorTimer() {
		if (!animation) {
			animation = true;
			colorTimer = new Timer();
			colorTimer.schedule(new TimerTask() {
				int counter = 0;
				int steps = 20;
				int r = getBackground().getRed();
				int g = getBackground().getGreen();
				int b = getBackground().getBlue();
				public void run() {
					setBackground(new Color((r += 1), (g += 1), (b += 1)));
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
			setBackground(UITheme.menuBackground);
		}
		animation = false;
		colorTimer.cancel();
	}
}
