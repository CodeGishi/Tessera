package gishi.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextArea;

import gishi.control.FileManager;
import gishi.control.PasswordManager;
import gishi.control.SecurityManager;
import gishi.control.SettingsManager;
import gishi.control.UITheme;
import gishi.templates.TFrame;
import gishi.util.TimedMessage;
import nbvcxz.resources.Feedback;
import nbvcxz.resources.FeedbackUtil;
import nbvcxz.scoring.Result;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class NewUserPanel extends TFrame{

	private final Color foreground 		= new Color(250, 	250, 	250			);
	private final Color background 		= new Color(25, 	25, 	25			);
	private final Color inputBackground = new Color(30, 	30, 	30			);
	private final Color shine			= new Color(55, 	20, 	40, 	155	);
	
	private final WebLabel lblCreate;
	private final WebLabel lblMsg;
	private final WebLabel lblMsg2;
	private final WebLabel lblUser;
	private final WebLabel lblPassword;
	
	private final WebCheckBox checkUnderstand;
	
	private final WebButton createBtn;
	private final WebButton cancelBtn;
	
	private final WebPasswordField inputUser;
	private final WebPasswordField inputPassword;	
	
	private final WebTextArea warnings;
	private final WebTextArea suggestions;
	
	private final Dimension field_size;
	private final Dimension button_size;
	private final int v_gap;
	private final int alert_time 	= 1;
	
	public NewUserPanel() {
		
		setType(Type.POPUP);
		setBackground(inputBackground);
		setSize(40 * UITheme.frameWidth / 100, 90 * UITheme.frameHeight / 100);
		setLocation(UITheme.screenWidth / 2 - getWidth() / 2, UITheme.screenHeight / 2 - getHeight() / 2);
		getContentPane().setLayout(null);
		getContentPane().setBackground(inputBackground);

		field_size 	= new Dimension(250 * UITheme.frameDimension.width / 1000, 40 * UITheme.frameDimension.height / 1000);
		button_size = new Dimension(250 * UITheme.frameDimension.width / 1000, 80 * UITheme.frameDimension.height / 1000);
		v_gap 		= field_size.height/4;

		lblCreate = new WebLabel("Create New User");
		lblCreate.setForeground(foreground);
		lblCreate.setFont(UITheme.enlargedFont);
		lblCreate.setSize(field_size.width*2, field_size.height);
		lblCreate.setLocation(getWidth() / 2 - field_size.width / 2, 2*field_size.height);
		getContentPane().add(lblCreate);
		
		lblMsg = new WebLabel("");
		lblMsg.setForeground(UITheme.errorMsg);
		lblMsg.setFont(UITheme.defaultFont);
		lblMsg.setSize(field_size.width*2, field_size.height);
		lblMsg.setLocation(getWidth() / 2 - field_size.width / 2, lblCreate.getLocation().y + lblCreate.getHeight() + 3*v_gap);
		getContentPane().add(lblMsg);

		lblUser = new WebLabel("Username");
		lblUser.setForeground(foreground);
		lblUser.setFont(UITheme.boldFont);
		lblUser.setSize(field_size);
		lblUser.setLocation(this.getWidth() / 2 - field_size.width / 2, lblMsg.getLocation().y + field_size.height - v_gap);
		getContentPane().add(lblUser);

		inputUser = new WebPasswordField();
		inputUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkInpu();
			}
		});
		inputUser.setDrawFocus(false);
		inputUser.setForeground(foreground);
		inputUser.setBackground(inputBackground);
		inputUser.setCaretColor(UITheme.error);
		inputUser.setFont(UITheme.boldFont);
		inputUser.setSize(field_size);
		inputUser.setLocation(this.getWidth() / 2 - field_size.width / 2, lblUser.getLocation().y + field_size.height-v_gap);
		inputUser.setEchoChar((char) 0);
		getContentPane().add(inputUser);

		lblPassword = new WebLabel("Password");
		lblPassword.setForeground(foreground);
		lblPassword.setFont(UITheme.boldFont);
		lblPassword.setSize(field_size);
		lblPassword.setLocation(this.getWidth() / 2 - field_size.width / 2,	inputUser.getLocation().y + field_size.height + v_gap);
		getContentPane().add(lblPassword);

		inputPassword = new WebPasswordField();
		inputPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkInpu();
			}
		});
		inputPassword.setDrawFocus(false);
		inputPassword.setForeground(foreground);
		inputPassword.setBackground(inputBackground);
		inputPassword.setCaretColor(UITheme.error);
		inputPassword.setFont(UITheme.boldFont);
		inputPassword.setEchoChar('\u00A4');
		inputPassword.setSize(field_size);
		inputPassword.setLocation(this.getWidth() / 2 - field_size.width / 2, lblPassword.getLocation().y + field_size.height - v_gap);
		getContentPane().add(inputPassword);

		WebButton wbtnShow = new WebButton();
		wbtnShow.setToolTipText("Password look up");
		wbtnShow.setRound(4);
		wbtnShow.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				inputPassword.setEchoChar((char) 0);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				inputPassword.setEchoChar('\u00A4');
			}
		});
		wbtnShow.setSize(field_size.height, field_size.height);
		wbtnShow.setLocation(inputPassword.getLocation().x + inputPassword.getWidth() + v_gap, inputPassword.getLocation().y);
		wbtnShow.setDrawShade(false);
		wbtnShow.setDrawFocus(false);
		wbtnShow.setDrawSides(true, true, true, true);
		wbtnShow.setFont(UITheme.boldFont);
		wbtnShow.setForeground(foreground);
		wbtnShow.setTopBgColor(background);
		wbtnShow.setBottomBgColor(background);
		wbtnShow.setTopSelectedBgColor(background);
		wbtnShow.setBottomSelectedBgColor(background);
		wbtnShow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().add(wbtnShow);
		
		createBtn = new WebButton("Create");
		createBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				createAction();
			}
		});
		createBtn.setSize(button_size);
		createBtn.setLocation(this.getWidth() / 2 - field_size.width / 2, inputPassword.getLocation().y + field_size.height + 2*v_gap);
		createBtn.setDrawShade(false);
		createBtn.setDrawFocus(false);
		createBtn.setDrawSides(true, true, true, true);
		createBtn.setFont(UITheme.boldFont);
		createBtn.setRolloverShine(true);
		createBtn.setShineColor(shine);
		createBtn.setForeground(foreground);
		createBtn.setTopBgColor(background);
		createBtn.setBottomBgColor(background);
		createBtn.setTopSelectedBgColor(background);
		createBtn.setBottomSelectedBgColor(background);
		createBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().add(createBtn);

		cancelBtn = new WebButton("Cancel");
		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setVisible(false);
			}
		});
		cancelBtn.setSize(button_size.width, 70*button_size.height/100);
		cancelBtn.setLocation(createBtn.getLocation().x, createBtn.getLocation().y + button_size.height);
		cancelBtn.setDrawShade(false);
		cancelBtn.setDrawFocus(false);
		cancelBtn.setDrawSides(true, true, true, true);
		cancelBtn.setFont(UITheme.boldFont);
		cancelBtn.setRolloverShine(true);
		cancelBtn.setShineColor(shine);
		cancelBtn.setForeground(foreground);
		cancelBtn.setTopBgColor(background);
		cancelBtn.setBottomBgColor(background);
		cancelBtn.setTopSelectedBgColor(background);
		cancelBtn.setBottomSelectedBgColor(background);
		cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().add(cancelBtn);
		
		warnings = new WebTextArea();
		warnings.setWrapStyleWord(true);
		warnings.setLineWrap(true);
		warnings.setEditable(false);
		warnings.setForeground(foreground);
		warnings.setBackground(inputBackground);
		warnings.setFont(UITheme.defaultFont);
		warnings.setSize(80*getWidth()/100, field_size.height*2);
		warnings.setLocation(getWidth() / 2 - warnings.getWidth()/2, cancelBtn.getLocation().y + cancelBtn.getHeight() + 10*v_gap);
		getContentPane().add(warnings);
		  
		suggestions = new WebTextArea();
		suggestions.setWrapStyleWord(true);
		suggestions.setLineWrap(true);
		suggestions.setEditable(false);
		suggestions.setForeground(foreground);
		suggestions.setBackground(inputBackground);
		suggestions.setFont(UITheme.defaultFont);
		suggestions.setSize(80*getWidth()/100, field_size.height*3);
		suggestions.setLocation(getWidth() / 2 - suggestions.getWidth()/2, warnings.getLocation().y + warnings.getHeight() + v_gap);
		getContentPane().add(suggestions);
				
		lblMsg2 = new WebLabel("");
		lblMsg2.setForeground(UITheme.errorMsg);
		lblMsg2.setFont(UITheme.defaultFont);
		lblMsg2.setSize(field_size.width*2, field_size.height);
		lblMsg2.setLocation(getWidth() / 2 - warnings.getWidth()/2, cancelBtn.getLocation().y + cancelBtn.getHeight() + 5*v_gap);
		getContentPane().add(lblMsg2);
		
		checkUnderstand = new WebCheckBox("I understand the risk. Let me in with weak password.");
		checkUnderstand.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(checkUnderstand.isSelected()){
					SettingsManager.setWeakEntry(true);
					
				}else {
					SettingsManager.setWeakEntry(false);
				}
			}
		});
		checkUnderstand.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
		});
		checkUnderstand.setRequestFocusEnabled(false);
		checkUnderstand.setFocusable(false);
		checkUnderstand.setSelected(false);
		checkUnderstand.setBackground(background);
		checkUnderstand.setForeground(UITheme.errorMsg);
		checkUnderstand.setSize(field_size.width*2, field_size.height);
		checkUnderstand.setLocation(getWidth() / 2 - warnings.getWidth()/2, cancelBtn.getLocation().y + cancelBtn.getHeight() + v_gap);
		checkUnderstand.setVisible(false);
		getContentPane().add(checkUnderstand);
	}
	
	public void showNewUserFrame() {
		setVisible(true);
		inputUser.setText("");
		inputPassword.setText("");
	}
	
	/**
	 * Validates user input.
	 * Checks if username is not taken and main password strength .
	 * 
	 * @return boolean
	 */
	private boolean checkInpu() {
		boolean result1 = false;
		boolean result2 = false;
		
		if (inputPassword.getPassword().length > 0) {
			if (inputUser.getPassword().length > 0) {
				File userDir = new File(FileManager.getUsersDirectory() + "\\" + String.valueOf(inputUser.getPassword()));
				if(userDir.isDirectory()) {
					result1 = false;
					inputUser.setBackground(UITheme.error);
					new TimedMessage(lblMsg, "This Username is already taken", 7);
				}else {
					result1 = true;
					inputUser.setBackground(inputBackground);
				}
			}
			else {
				new TimedMessage(lblMsg, "Username cannot be blank", 7);
				inputUser.setBackground(UITheme.error);
			}
			inputPassword.setBackground(inputBackground);
		}		
		else {
			new TimedMessage(lblMsg, "Password cannot be blank", 7);
			warnings.setText("");
			suggestions.setText("");
			lblMsg2.setText("");
			inputPassword.setBackground(UITheme.error);
		}
			
		if(result1) {
			if(!String.valueOf(inputUser.getPassword()).matches("^\\W.*$")) {
				Result result = PasswordManager.nbvcxz.estimate(String.valueOf(inputPassword.getPassword()));
				Feedback feedback = FeedbackUtil.getFeedback(result);
				String warningString = "Warning:\n";
				String suggestionString = "Suggestion:\n"; 
				if (result.isMinimumEntropyMet()) {
					result2 = true;
					inputPassword.setBackground(inputBackground);
					checkUnderstand.setVisible(false);
					warningString = "";
					suggestionString = "";
					lblMsg2.setText("");
					new TimedMessage(lblMsg, "", 1);
				} else {
					warningString = "Warning:\n";
					suggestionString = "Suggestion:\n";
					if (feedback.getWarning() != null) {
						warningString += feedback.getWarning() + "\n";
					}
					for (String suggestion : feedback.getSuggestion()) {
						suggestionString += suggestion + "\n";
					}
					new TimedMessage(lblMsg, "This password is too weak", 7);
					inputPassword.setBackground(UITheme.error);
					checkUnderstand.setVisible(true);
					lblMsg2.setText("Entropy: " + result.getEntropy().toString().substring(0, 4) + " [min. 45]");
					result2 = false;
					if (checkUnderstand.isSelected()) {
						result2 = true;
					}
				}
				warnings.setText(warningString);
				suggestions.setText(suggestionString);

			} else {
				new TimedMessage(lblMsg, "Username cannot start with a special character", 10);
				result2 = false;
			}
		}
		
		boolean result = result1 && result2;
		
		return result;
	}

	/**
	 * Highlights field containing an error.
	 * 
	 */
	private void markWrongField() {

		if (inputUser.getPassword().length <= 0) {
			inputUser.setBackground(UITheme.error);
			new TimedMessage(lblMsg, "No Username...", alert_time);
		} else {
			inputUser.setBackground(inputBackground);
		}

		if (inputPassword.getPassword().length <= 0) {
			inputPassword.setBackground(UITheme.error);
			new TimedMessage(lblMsg, "No Password...", alert_time);
		}
		else {
			inputPassword.setBackground(inputBackground);
		}
	}
	
	/**
	 * Tries to create user directory and authentication file.
	 *  
	 */
	private void createAction() {
		markWrongField();
		if(checkInpu()) {
			if(FileManager.createUserDirectory(String.valueOf(inputUser.getPassword()))) {
				if(SecurityManager.generateAuthenticationFile(inputUser.getPassword(), inputPassword.getPassword())) {
					LoginPanel.showMessage("Account creation complete");
					removeAll();
					revalidate();
					setVisible(false);
				}
			}		
		}
	}
}
