package gishi.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
import java.util.TimerTask;

import com.alee.laf.panel.WebPanel;

import gishi.control.SettingsManager;
import gishi.control.UITheme;
import gishi.templates.TFrame;

@SuppressWarnings("serial")
public class MainFrame extends TFrame {

	private static final int 		content_x 			= UITheme.sideMenuWidth;
	private static final int 		content_y			= UITheme.controlMenuHeight;
	private static final int 		animationTime 		= 10;
	private static final int 		slow_delimeter 		= 2;
	
	private boolean animating = false;
	private boolean creating = false;
	
	private static WebPanel contentPane;
	private WebPanel activePanel;
	
	private WelcomePanel welcomePanel;
	private LibraryPanel libraryPanel;
	private CreatePanel createPanel;
	private CreatePanel editPanel;
	private CheckPanel checkPanel;
	private GeneratePanel generatePanel;
	private SettingsPanel settingsPanel;
	private MorePanel morePanel;
	
	private Timer activityTimer;
	private long lastMouseActivity;
	
	public MainFrame() {
		
		setLastMouseActivity(System.currentTimeMillis());
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				setLastMouseActivity(System.currentTimeMillis());
			}
		});
		
		setTitle("Tessera [LoginScreen]");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/icons/tessera_white.png")));
		
		contentPane = new WebPanel();
		contentPane.setBackground(UITheme.background);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		addControls(2, true);
		
		LoginPanel lp = new LoginPanel(this);
		getContentPane().add(lp);
	}
	public MainFrame(boolean showlogin) {
		setLastMouseActivity(System.currentTimeMillis());
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				setLastMouseActivity(System.currentTimeMillis());
			}
		});
		
		setTitle("Tessera [LoginScreen]");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/icons/tessera_white.png")));
		
		contentPane = new WebPanel();
		contentPane.setBackground(UITheme.background);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		addControls(2, true);
		initMainFrame();
	}

	
	public void refreshMainFrame() {
		pause();
		removeAll();
		revalidate();
		try {
			MainFrame mf = new MainFrame(false);
			mf.setVisible(true);
		}catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(false);
	}
	
	/**
	 * Initialize components of the MainFrame.
	 * 
	 */
	public void initMainFrame() {
		
		setLastMouseActivity(System.currentTimeMillis());
		startActivityTimer();
		setTitle("Tessera");
		contentPane.setBackground(UITheme.background);
		
		getContentPane().add(new MenuPanel(this));

		welcomePanel = new WelcomePanel(this);
		welcomePanel.setVisible(true);
		getContentPane().add(welcomePanel);
		
		libraryPanel = new LibraryPanel(this);
		libraryPanel.setVisible(false);
		getContentPane().add(libraryPanel);

		createPanel = new CreatePanel(this);
		createPanel.setVisible(false);
		getContentPane().add(createPanel);

		checkPanel = new CheckPanel(this);
		checkPanel.setVisible(false);
		getContentPane().add(checkPanel);
		
		generatePanel = new GeneratePanel(this);
		generatePanel.setVisible(false);
		getContentPane().add(generatePanel);
		
		settingsPanel = new SettingsPanel(this);
		settingsPanel.setVisible(false);
		getContentPane().add(settingsPanel);

		morePanel = new MorePanel(this);
		morePanel.setVisible(false);
		getContentPane().add(morePanel);
		
		activePanel = welcomePanel;

		revalidate();
		repaint();
		pack();
	}

	public WebPanel getWelcomePanel() {
		return welcomePanel;
	}

	public LibraryPanel getLibraryPanel() {
		return libraryPanel;
	}

	public CreatePanel getCreatePanel() {
		if(!creating) {
			createPanel = new CreatePanel(this);
			createPanel.setVisible(false);
			getContentPane().add(createPanel);
		}		
		return createPanel;
	}

	public CreatePanel getEditPanel(int pswdID) {
		editPanel = new CreatePanel(this, pswdID);
		editPanel.setVisible(false);
		getContentPane().add(editPanel);
		return editPanel;
	}
	
	public CheckPanel getCheckPanel() {
		return checkPanel;
	}

	public SettingsPanel getSettingsPanel() {
		return settingsPanel;
	}
	public GeneratePanel getPanelGenerate() {
		return generatePanel;
	}
	public MorePanel getPanelMore() {
		return morePanel;
	}
	public WebPanel getActivePanel() {
		return activePanel;
	}

	
	/**
	 * Monitors user activity via mouse listener to automatically log off after set time.
	 * 
	 */
	public void startActivityTimer() {
		activityTimer = new Timer();
		activityTimer.schedule(new TimerTask() {
			int counter = 0;
			public void run() {
				if (counter > 2) {
					if(System.currentTimeMillis() - getLastMouseActivity() > SettingsManager.getTimeToExit()*60*1000) {
						pause();
						try {
							MainFrame mf = new MainFrame();
							mf.setAutoRequestFocus(false);
							mf.setVisible(true);
						}catch (Exception e) {
							e.printStackTrace();
						}
						removeAll();
						revalidate();
						setVisible(false);
					}
					else {
					}
				}
				counter++;
			}
		}, 0, 60*1000);
	}
	
	/**
	 * Pause the ActivityTimer.
	 * 
	 */
	public void pause() {
		activityTimer.cancel();
	}
	
	public void setCreateState(boolean state) {
		this.creating = state;
	}
	
	private int calculateStep(int base_step) {
		return (base_step * (base_step / 8));
	}

	private int calculateSmallStep(int base_step, int small_step) {
		return ((base_step * base_step) - (7 * (base_step * base_step) / 8)) / (7 * small_step / 5);
	}
	
	/**
	 * Animated swap to the right of displayed content.
	 * 
	 * </br></br>
	 * <b>Note:</b> If the animating flag is set to false, no animation will occur
	 * 
	 * @param container parent container
	 * @param p1 currently displayed panel
	 * @param p2 panel to be displayed
	 */
	public void swapLeft(Container container, WebPanel p1, WebPanel p2) {
		if(SettingsManager.getAllAnimationSettings())
		if (activePanel != p2 && !animating) {
			p2.setLocation(container.getWidth() + p1.getWidth() / 7, content_y);
			p1.setVisible(true);
			p2.setVisible(true);
			animating = true;
			int start_location = (int) p2.getLocation().getX();
			int dummy_step = 1;
			int dummy_base_step = 1;
			int dummy_small_step = 1;
			int step_counter = 0;
			double dist = p2.getWidth();

			while (start_location > content_x) {
				if (start_location - content_x < dist / slow_delimeter) {
					dummy_step = calculateSmallStep(dummy_base_step, dummy_small_step);
					dummy_small_step++;
				} else {
					dummy_step = calculateStep(dummy_base_step);
				}
				if (start_location > content_x) {
					if (start_location - dummy_step > content_x) {
						start_location -= dummy_step;
					} else {
						start_location = content_x;
					}
					step_counter++;
					dummy_base_step++;
				}
			}
			final int f_count = step_counter;
			TimerTask task = new TimerTask() {
				int step_base = 1;
				int small_step = 1;
				int step = 1;
				double dist = p2.getWidth();
				int c_max = 255;
				int c_step = (int) (c_max / f_count) - 1;
				int c = 0;
				public void run() {
					if (p2.getLocation().getX() - content_x < dist / slow_delimeter) {
						step = calculateSmallStep(step_base, small_step);
						small_step++;
					} else {
						step = calculateStep(step_base);
					}
					if (p2.getLocation().getX() > content_x) {
						if (p2.getLocation().getX() - step > content_x) {
							p1.setLocation((int) p1.getLocation().getX() - 2 * step, content_y);
							p2.setLocation((int) (p2.getLocation().getX() - step), content_y);
						} else {
							p1.setLocation(content_x - p1.getWidth(), content_y);
							p2.setLocation(content_x, content_y);
						}
						p1.setBackground(new Color(p1.getBackground().getRed(), p1.getBackground().getGreen(),
								p1.getBackground().getBlue(), 15));
						p2.setBackground(new Color(p2.getBackground().getRed(), p2.getBackground().getGreen(),
								p2.getBackground().getBlue(), c += c_step));

						container.repaint();
						step_base++;
					} else {
						activePanel = p2;
						p1.setVisible(false);
						container.repaint();
						animating = false;
						this.cancel();
					}
				}
			};
			Timer timer = new Timer("Timer");
			timer.schedule(task, 0, animationTime);
		} else {
		}
		else {
			if(activePanel != p2) {
				p1.setVisible(true);
				p2.setVisible(true);
				p1.setLocation(content_x + 2 * p1.getWidth(), content_y);
				p2.setLocation(content_x, content_y);
				activePanel = p2;
				p1.setVisible(false);
				container.revalidate();
				container.repaint();
			}
		}
	}
	
	/**
	 * Animated swap to the left of displayed content 
	 * 
	 * </br></br>
	 * <b>Note:</b> If the animating flag is set to false, no animation will occur
	 * 
	 * @param container parent container
	 * @param p1 currently displayed panel
	 * @param p2 panel to be displayed
	 */
	public void swapRight(Container container, WebPanel p1, WebPanel p2) {
		if(SettingsManager.getAllAnimationSettings())
		if (activePanel != p2 && !animating) {
			p2.setLocation(0 - p2.getWidth(), content_y);
			p1.setVisible(true);
			p2.setVisible(true);
			animating = true;
			int start_location = (int) p2.getLocation().getX();
			int dummy_step = 1;
			int dummy_base_step = 1;
			int dummy_small_step = 1;
			int step_counter = 0;
			double dist = p2.getWidth() * -1;

			while (start_location < content_x) {
				if ( content_x - start_location > dist / slow_delimeter) {
					dummy_step = calculateSmallStep(dummy_base_step, dummy_small_step);
					dummy_small_step++;
				} else {
					dummy_step = calculateStep(dummy_base_step);
				}
				if (start_location < content_x) {
					if (start_location + dummy_step < content_x) {
						start_location += dummy_step;
					} else {
						start_location = content_x;
					}
					step_counter++;
					dummy_base_step++;
				}
			}
			final int f_count = step_counter;
			TimerTask task = new TimerTask() {
				int step_base = 1;
				int small_step = 1;
				int step = 1;
				double dist = p2.getWidth();
				int c_max = 255;
				int c_step = (int) (c_max / f_count) - 1;
				int c = 0;
				public void run() {
					if (content_x + p2.getLocation().getX() > dist / slow_delimeter) {
						step = calculateSmallStep(step_base, small_step);
						small_step++;
					} else {
						step = calculateStep(step_base);
					}
					if (p2.getLocation().getX() < content_x) {
						if (p2.getLocation().getX() + step < content_x) {
							p1.setLocation((int) p1.getLocation().getX() + 3 * step / 2, content_y);
							p2.setLocation((int) (p2.getLocation().getX() + step), content_y);
						} else {
							p1.setLocation(content_x + 2 * p1.getWidth(), content_y);
							p2.setLocation(content_x, content_y);
						}
						p1.setBackground(new Color(p1.getBackground().getRed(), p1.getBackground().getGreen(),
								p1.getBackground().getBlue(), 15));
						p2.setBackground(new Color(p2.getBackground().getRed(), p2.getBackground().getGreen(),
								p2.getBackground().getBlue(), c += c_step));

						container.repaint();
						step_base++;
					} else {
						activePanel = p2;
						p1.setVisible(false);
						container.repaint();
						animating = false;
						this.cancel();
					}
				}
			};
			Timer timer = new Timer("Timer");
			timer.schedule(task, 0, animationTime);
		} else {
		}
		else {
			if(activePanel != p2) {
				p1.setVisible(true);
				p2.setVisible(true);
				p1.setLocation(content_x + 2 * p1.getWidth(), content_y);
				p2.setLocation(content_x, content_y);
				activePanel = p2;
				p1.setVisible(false);
				container.revalidate();
				container.repaint();
			}
		}
	}
	
	/**
	 * 
	 * @return time from last detected mouse activity
	 */
	public long getLastMouseActivity() {
		return lastMouseActivity;
	}
	
	/**
	 * Sets lastMouseActivity value
	 * 
	 */
	public void setLastMouseActivity(long lastMouseActivity) {
		if(SettingsManager.DEBUG)System.out.println("activity update " + lastMouseActivity);
		this.lastMouseActivity = lastMouseActivity;
	}

}
