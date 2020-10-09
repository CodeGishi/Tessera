package gishi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import gishi.control.UITheme;
import gishi.util.Icons;

@SuppressWarnings("serial")
public class IntroAnimation extends JFrame {

	private static final int 		animationTime 		= 35;
	private static final int 		animationDelay 		= 500;
	private static final int 		animationDelimeter 	= 3 * 255 / 4;
	private static final int 		size 				= UITheme.animationSize;
	private static final String 	logo 				= "/icons/tessera_white.png";
	private static final Dimension 	preffered_size 		= new Dimension(size, size);
	
	private Timer t;
	private OvalBackground background = new OvalBackground();
	
	/**
	 * Launch program icon animation.
	 * 
	 */
	public IntroAnimation() {
		
		setTitle("Tessera");
		setAlwaysOnTop(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/icons/tessera_white.png")));
		setUndecorated(true);
		setType(Type.POPUP);
		setLocation(UITheme.screenWidth/2 - size/2, UITheme.screenHeight/2 - size/2);
		setSize(preffered_size);
		setPreferredSize(preffered_size);
		setBackground(new Color(0, 0, 0, 0));

		background = new OvalBackground();
		background.setBackground(new Color(0, 0, 0, 0));
		getContentPane().add(background);

		t = new Timer(animationTime, new ActionListener() {
			int alpha = 255;
			int step = 1;
			int sstep = 1;
			public void actionPerformed(ActionEvent e) {
				step = (int) (3 * sstep * Math.log(sstep * sstep) / 7);
				if (alpha > 0) {
					if (alpha < animationDelimeter) {
						alpha = alpha - 9 * (step / (1 + (sstep))) / 2;
						if (alpha < 0) {
							alpha = 0;
						}
					} else {
						alpha = alpha - 5 * (step / (1 + (sstep))) / 2;
					}
					background.setBackgroundAlpha(alpha);
					sstep++;
					repaint();
				} else {
					t.stop();
					try {
						Thread.sleep(1500);
					} catch (Exception ex) {
						//catch Exception
					}
					setVisible(false);
					MainFrame mf = new MainFrame();
					mf.setVisible(true);
				}
			}
		});
		t.setInitialDelay(animationDelay);
		t.start();
		pack();
	}

	/**
	 * Generates white translucent oval shape as intro background.
	 *
	 */
	private class OvalBackground extends JPanel {
		int alpha = 255;
		int ovalSize = size;
		int imageSize = 5 * ovalSize / 8;

		public void setBackgroundAlpha(int alphaValue) {
			this.alpha = alphaValue;
		}

		private int calculateSize() {
			int logoSize = 0;
			if (imageSize - alpha <= 0) {
				logoSize = 1;
			} else {
				logoSize = imageSize - alpha;
			}
			return logoSize;
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(Icons.load(logo, calculateSize(), 1.0).getImage(),
					(ovalSize / 2) - (calculateSize()) / 2, 
					(ovalSize / 2) - (calculateSize()) / 2, 
					null);
			g.setColor(new Color(250, 250, 250, alpha));
			g.fillOval((ovalSize / 2) - (ovalSize - alpha) / 2, 
					(ovalSize / 2) - (ovalSize - alpha) / 2, 
					ovalSize - alpha,
					ovalSize - alpha);
		}
	}
}
