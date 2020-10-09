package gishi.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.text.WebPasswordField;

import gishi.control.FileManager;
import gishi.control.PasswordManager;
import gishi.control.SecurityManager;
import gishi.control.SettingsManager;
import gishi.control.UITheme;
import gishi.templates.ContentPanel;
import gishi.util.Icons;
import gishi.util.TimedMessage;

@SuppressWarnings("serial")
public class LoginPanel extends ContentPanel {

	private final Color foreground = new Color(250, 250, 250);
	private final Color background = new Color(25, 25, 25);
	private final Color inputBackground = new Color(50, 50, 50);

	private static NewUserPanel nu = new NewUserPanel();;

	private static WebLabel lblMsg = new WebLabel();
	private final WebLabel lblUser;
	private final WebLabel lblPassword;
	private final WebLabel lblNewUser;

	private final WebButton loginBtn;
	private final WebButton newUserBtn;

	private final WebPasswordField inputUser;
	private final WebPasswordField inputPassword;

	private final Dimension logo_size;
	private final Dimension field_size;
	private final Dimension button_size;
	private final int v_gap;

	private Timer timer;
	private final int loading_time = 100;
	private final static int alert_time = 2;

	
	/**
	 * Initialize LoginPanel.
	 * Contains login and user creation forms.
	 * 
	 * @param parent
	 */
	public LoginPanel(MainFrame parent) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				nu.setVisible(false);
			}
		});
		setParentFrame(parent);
		setBackground(background);
		setSize(parent.getSize());
		setLocation(0, 0);
		setLayout(null);

		logo_size = new Dimension(350 * getSize().height / 1000, 350 * getSize().height / 1000);
		field_size = new Dimension(250 * getSize().width / 1000, 40 * getSize().height / 1000);
		button_size = new Dimension(250 * getSize().width / 1000, 80 * getSize().height / 1000);
		v_gap = field_size.height;

		WebLabel label = new WebLabel();
		label.setSize(logo_size);
		label.setLocation(this.getWidth() / 2 - logo_size.width / 2, 5 * logo_size.height / 10);
		label.setIcon(Icons.load("/icons/tessera_white.png", logo_size.height));
		add(label);

		lblMsg = new WebLabel("");
		lblMsg.setForeground(UITheme.errorMsg);
		lblMsg.setFont(UITheme.defaultFont);
		lblMsg.setSize(field_size);
		lblMsg.setLocation(this.getWidth() / 2 - field_size.width / 2,
				label.getLocation().y + label.getHeight() + 2 * v_gap);
		add(lblMsg);

		lblUser = new WebLabel("User");
		lblUser.setText("Username");
		lblUser.setForeground(foreground);
		lblUser.setFont(UITheme.boldFont);
		lblUser.setSize(field_size);
		lblUser.setLocation(this.getWidth() / 2 - field_size.width / 2, lblMsg.getLocation().y + field_size.height);
		add(lblUser);

		inputUser = new WebPasswordField();
		inputUser.setDrawFocus(false);
		inputUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					loginAction();
				}
			}
		});
		inputUser.setCaretColor(UITheme.error);
		inputUser.setForeground(foreground);
		inputUser.setBackground(inputBackground);
		inputUser.setFont(UITheme.boldFont);
		inputUser.setEchoChar('\u00A4');
		inputUser.setSize(field_size);
		inputUser.setLocation(this.getWidth() / 2 - field_size.width / 2, lblUser.getLocation().y + field_size.height);
		add(inputUser);

		lblPassword = new WebLabel("Password");
		lblPassword.setForeground(foreground);
		lblPassword.setFont(UITheme.boldFont);
		lblPassword.setSize(field_size);
		lblPassword.setLocation(this.getWidth() / 2 - field_size.width / 2,
				inputUser.getLocation().y + field_size.height + 7);
		add(lblPassword);

		inputPassword = new WebPasswordField();
		inputPassword.setDrawFocus(false);
		inputPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					loginAction();
				}
			}
		});
		inputPassword.setCaretColor(UITheme.error);
		inputPassword.setForeground(foreground);
		inputPassword.setBackground(inputBackground);
		inputPassword.setFont(UITheme.boldFont);
		inputPassword.setEchoChar('\u00A4');
		inputPassword.setSize(field_size);
		inputPassword.setLocation(this.getWidth() / 2 - field_size.width / 2,
				lblPassword.getLocation().y + field_size.height);
		add(inputPassword);

		loginBtn = new WebButton("LOGIN");
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				loginAction();
			}
		});
		loginBtn.setSize(button_size);
		loginBtn.setLocation(this.getWidth() / 2 - field_size.width / 2,
				inputPassword.getLocation().y + field_size.height + 15);
		loginBtn.setDrawShade(false);
		loginBtn.setDrawFocus(false);
		loginBtn.setDrawSides(true, true, true, true);
		loginBtn.setFont(UITheme.boldFont);
		loginBtn.setRolloverShine(true);
		loginBtn.setShineColor(new Color(55, 20, 40, 155));
		loginBtn.setForeground(foreground);
		loginBtn.setTopBgColor(background);
		loginBtn.setBottomBgColor(background);
		loginBtn.setTopSelectedBgColor(background);
		loginBtn.setBottomSelectedBgColor(background);
		loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(loginBtn);

//btn new user
		newUserBtn = new WebButton();
		newUserBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				nu.showNewUserFrame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewUser.setText("Create New User");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewUser.setText("");
			}
		});
		newUserBtn.setIcon(Icons.load("/icons/light/plus.png", field_size.height - 5, 1.0));
		newUserBtn.setSize(button_size.height, button_size.height);
		newUserBtn.setLocation(0 + button_size.height, loginBtn.getLocation().y);
		newUserBtn.setDrawShade(false);
		newUserBtn.setDrawFocus(false);
		newUserBtn.setMoveIconOnPress(false);
		newUserBtn.setDrawSides(true, true, true, true);
		newUserBtn.setFont(UITheme.boldFont);
		newUserBtn.setRolloverShine(false);
		newUserBtn.setForeground(foreground);
		newUserBtn.setTopBgColor(background);
		newUserBtn.setBottomBgColor(background);
		newUserBtn.setTopSelectedBgColor(background);
		newUserBtn.setBottomSelectedBgColor(background);
		newUserBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(newUserBtn);

//lblNewUser
		lblNewUser = new WebLabel();
		lblNewUser.setForeground(foreground);
		lblNewUser.setFont(UITheme.boldFont);
		lblNewUser.setSize(button_size);
		lblNewUser.setLocation(newUserBtn.getLocation().x + newUserBtn.getWidth() + v_gap, newUserBtn.getLocation().y);
		add(lblNewUser);
	}

	/**
	 * Validation and Authentication of login with given input.
	 * 
	 */
	private void loginAction() {
		if (inputUser.getPassword().length > 0) {
			if (inputPassword.getPassword().length > 0) {
				if (FileManager.initialize(String.copyValueOf(inputUser.getPassword()))) {
					if (SecurityManager.authenticate(inputUser.getPassword(), inputPassword.getPassword())) {
						removeLogin();
						// showMessage(0);
					} else {// auth failed
						showMessage(3);
					}
				} else {// FileManager Exception
					showMessage(5);
				}
			} // no pswd
			else {
				showMessage(2);
			}
		} // no username
		else {
			showMessage(1);
		}
	}

	/**
	 * Hides input form while loging in.
	 * 
	 */
	private void removeLogin() {
		WebProgressBar progressBar = new WebProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setSize(field_size);
		progressBar.setLocation(this.getWidth() / 2 - progressBar.getWidth() / 2, inputPassword.getLocation().y);
		add(progressBar);

		remove(lblMsg);
		remove(lblUser);
		remove(inputUser);
		remove(lblPassword);
		remove(inputPassword);
		remove(loginBtn);
		remove(newUserBtn);
		remove(lblNewUser);
		getParentFrame().repaint();

		startLoading();
	}

	/**
	 * Shows user error message accordingly to given error code.
	 * 
	 * @param code
	 */
	private void showMessage(int code) {
		if (code == 1) {
			new TimedMessage(lblMsg, "No Username...", alert_time);
		} else if (code == 2) {
			new TimedMessage(lblMsg, "No Password...", alert_time);
		} else if (code == 3) {
			new TimedMessage(lblMsg, "Authentication Failed...", alert_time);
		} else if (code == 4) {
			new TimedMessage(lblMsg, "No Input...", alert_time);
		} else if (code == 5) {
			new TimedMessage(lblMsg, "Lacking user files...", alert_time);
		} else {
			new TimedMessage(lblMsg, "Unknown input error", alert_time);
		}
	}

	public void startLoading() {
		/*
		 * for fade out login parent.initMainFrame(); parent.revalidate();
		 * parent.repaint();
		 */
		timer = new Timer();
		timer.schedule(new TimerTask() {
			int counter = 0;

			public void run() {
				/*
				 * for fade out login lp.setBackground(new
				 * Color(lp.getBackground().getRed(),lp.getBackground().getGreen(),lp.
				 * getBackground().getBlue(),lp.getBackground().getAlpha()-2));
				 * parent.repaint(); repaint();
				 */
				if (counter == loading_time) {
					finalizeLogin();
					pause();
				}
				counter++;
			}
		}, 0, 10);
	}

	public void pause() {
		timer.cancel();
	}

	private void finalizeLogin() {
		// FileManager.initialize(String.valueOf(inputUser.getPassword()));
		PasswordManager.loadPasswordList();

		SettingsManager.loadSettings();
		UITheme.loadTheme();

		SettingsManager.setThisLoginDate(
				new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));

		((MainFrame) getParentFrame()).initMainFrame();

		this.setVisible(false);
	}

	public static void showMessage(String msg) {
		new TimedMessage(lblMsg, msg, alert_time * 2);
	}

}
