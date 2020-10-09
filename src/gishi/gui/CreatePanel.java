package gishi.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Timer;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

import gishi.control.ExceptionManager;
import gishi.control.PasswordManager;
import gishi.control.UITheme;
import gishi.data.Password;
import gishi.data.PasswordCategory;
import gishi.templates.ContentPanel;
import gishi.templates.TButton;
import gishi.templates.TCheckBox;
import gishi.templates.TCombo;
import gishi.templates.TLabel;
import gishi.templates.TPasswordField;
import gishi.templates.TProgressBar;
import gishi.templates.TTextArea;
import gishi.templates.TTextField;
import gishi.util.Icons;
import gishi.util.TimedMessage;
import nbvcxz.resources.Generator;

@SuppressWarnings("serial")
public class CreatePanel extends ContentPanel {

/*
 * PANEL CONSTANTS
 */
	WebPanel contentPane = new WebPanel();
	Timer timer;
	Password pswd;
	Password pswd_edit;
	
	int v_gap = 7;
	int last_field_y = 0;
	String spinnerValue = "strong";
	
/*
 * INPUT FIELDS & RELATED CONSTANTS
 */
	private int icon_size;
	private int left_column_x;
	private int right_column_x;
	private Dimension label_size;
	private Dimension field_size;
	private Font def = UITheme.enlargedFont;
	private Font fontSection = new Font("Tahoma", Font.PLAIN, 28 * UITheme.frameHeight / 1000);
	private String iconPath = "/tiles/0tessera.png";
	
	private final TLabel copyMsg = new TLabel();
	private final TLabel errorMsg = new TLabel();
	private final TLabel lblCreate = new TLabel();
	private final TLabel lblEdit = new TLabel();
	
	private  TLabel Icon;
	private  TTextField inputStorageName;
	private  TTextField inputLogin;
	private  TPasswordField inputPassword;
	private  TProgressBar strengthBar;
	private  TTextField inputWebsite;
	private  TCombo comboCategory;
	private  TTextArea inputMore;
	private  WebScrollPane scrollPane;
	
	private  TCheckBox checkWebsite;
	private  TCheckBox checkCategory;
	private  TCheckBox checkMore;
	private  String website = "null";
	private  String more = "null";
/*
 * FLAGS USED WHILE EDI MODE
 */
	String override = "";
	//boolean editable;
	boolean iconEdit = false;
	boolean storageEdit = false;
	boolean loginEdit = false;
	boolean passwordEdit = false;
	boolean websiteEdit = false;
	boolean categoryEdit = false;
	boolean moreEdit = false;

	boolean editMode = false;
	boolean createMode = false;
	boolean previewMode = false;
/*
 * BUTTONS
 */
	TButton btnChange = new TButton("change");;
	TButton btnAdd = new TButton("edit");
	TButton btnSave = new TButton("save");
	TButton btnCancel = new TButton("cancel");
	TButton btnDelete = new TButton("delete");
	TButton btnGenerate = new TButton("generate");
	TButton btnOpenURL = new TButton("open");

	/**
	 * Initialize CreatePanel.
	 * Form to input data for new Password.
	 * 
	 * @param parent Instance of MainFrame
	 */
	public CreatePanel(MainFrame parent) {
		
		setParentFrame(parent);
		
		contentPane.setLayout(null);
		contentPane.setLocation(0, 0);
		contentPane.setSize(this.getSize());
		contentPane.setBackground(UITheme.background);
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseClickEvent(e);
			}
		});
		add(contentPane);
		label_size = new Dimension(100 * this.getWidth() / 1000, 30 * this.getWidth() / 1000);
		field_size = new Dimension(220 * this.getWidth() / 1000, 30 * this.getWidth() / 1000);
		
		createMode = true;
		parent.setCreateState(createMode);
		
		initPanel();
		enableEditing(true);
		updateOptional();
	}

	/**
	 * Initialize CreatePanel for preview.
	 * 
	 * @param parent Instance of MainFrame
	 * @param pswdID ID of password [Integer]
	 */
	public CreatePanel(MainFrame parent, int pswdID) {
		
		setParentFrame(parent);
		contentPane.setLayout(null);
		contentPane.setLocation(0, 0);
		contentPane.setSize(this.getSize());
		contentPane.setBackground(UITheme.background);
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseClickEvent(e);
			}
		});
		add(contentPane);
		
		label_size = new Dimension(100 * this.getWidth() / 1000, 30 * this.getWidth() / 1000);
		field_size = new Dimension(220 * this.getWidth() / 1000, 30 * this.getWidth() / 1000);
		
		previewMode = true;
		pswd = PasswordManager.getPassword(pswdID);

		initPanel();
		loadValues();
		enableEditing(false);
	}
	
	private CreatePanel getCreatePanel() {
		return this;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////// MOUSE CLICK 
	private void mouseClickEvent(MouseEvent e) {
//		if (e.getSource().getClass().equals(TButton.class)) {
//
//		} else if (e.getSource().getClass().equals(TTextField.class)) {
//
//		} else if (e.getSource().getClass().equals(TTextArea.class)) {
//
//		} else if (e.getSource().getClass().equals(TPasswordField.class)) {
//
//		} else if (e.getSource().getClass().equals(TCombo.class)) {
//
//		} else {
//
//		}
		
		((MainFrame) getParentFrame()).setLastMouseActivity(System.currentTimeMillis());
		
		if (IconGalery.isGaleryVisible())
			IconGalery.hideGalery();
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////// CHECK INPUT 
	/**
	 * Validating user input.
	 * 
	 * @return
	 */
	private boolean checkInput() {
		boolean result = false;
		boolean primary = false;
		if (inputStorageName.getText().length() > 0 && inputStorageName.getText().length() < 47)
			if (inputLogin.getText().length() > 0 && inputLogin.getText().length() < 47)
				if (inputPassword.getPassword().length > 0 && inputPassword.getPassword().length < 70)
					primary = true;
				else
					new TimedMessage(errorMsg, "Password field is empty", 3);
			else
				new TimedMessage(errorMsg, "Login field is empty", 3);
		else
			new TimedMessage(errorMsg, "Name field is empty", 3);
		
		boolean flagSpecial = false;
		boolean flagWebsite = false;
		boolean flagMore = false;
		
		if (primary) {
			if(!inputStorageName.getText().matches("^\\W.*$")) {
				flagSpecial = true;
			}else {
				new TimedMessage(errorMsg, "Storage name cannot start with special character", 4);
				flagSpecial = false;
			}
			if (!checkWebsite.isSelected()) {
				flagWebsite = true;
			} else if (checkWebsite.isSelected()) {
				if (inputWebsite.getText().length() > 0)
					flagWebsite = true;
				else {
					flagWebsite = false;
					new TimedMessage(errorMsg, "Website is active but empty", 3);
				}
			}
			if (!checkMore.isSelected()) {
				flagMore = true;
			} else if (checkMore.isSelected()) {
				if (inputMore.getText().length() > 0)
					flagMore = true;
				else {
					flagMore = false;
					new TimedMessage(errorMsg, "More input is active but empty", 3);
				}
			}
		}
		
		if(primary)
			if(flagSpecial)
				if(flagWebsite)
					if(flagMore)
						result = true;
		
		if (inputStorageName.getText().length() > 0 && inputStorageName.getText().length() < 27 && !inputStorageName.getText().matches("^\\W.*$")) {
			inputStorageName.setBackground(UITheme.inputBackground);
		} else {
			inputStorageName.setBackground(UITheme.error);
		}
		if (inputLogin.getText().length() > 0 && inputLogin.getText().length() < 27) {
			inputLogin.setBackground(UITheme.inputBackground);
		} else {
			inputLogin.setBackground(UITheme.error);
		}
		if (inputPassword.getPassword().length > 0 && inputPassword.getPassword().length < 50) {
			inputPassword.setBackground(UITheme.inputBackground);
		} else {
			inputPassword.setBackground(UITheme.error);
		}
		if (checkWebsite.isSelected()) {
			if (inputWebsite.getText().length() > 0 && inputWebsite.getText().length() < 50) {
				inputWebsite.setBackground(UITheme.inputBackground);
				website = inputWebsite.getText();
			} else {
				//inputWebsite.setBackground(UITheme.error);
			}
		}else {
			inputWebsite.setBackground(UITheme.inputBackground);
		}
		if (checkMore.isSelected()) {
			if (inputMore.getText().length() > 0 && inputMore.getText().length() < 150) {
				//inputMore.setBackground(UITheme.inputBackground);
				more = inputMore.getText();
			} else {
				//inputMore.setBackground(UITheme.error);
			}
		}else {
			//inputMore.setBackground(UITheme.inputBackground);
		}
		
		return result;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////// UPDATEOPTIONAL 
	/**
	 * Loads optional values if given.
	 * 
	 */
	private void updateOptional() {
		inputWebsite.setEnabled(checkWebsite.isSelected());
		comboCategory.setEnabled(checkCategory.isSelected());
		inputMore.setEnabled(checkMore.isSelected());
		scrollPane.setEnabled(checkMore.isSelected());
		
		if (!checkWebsite.isSelected()) {
			website = "null";
		}
		if (!checkMore.isSelected()) {
			more = "null";
		}
		if (!checkCategory.isSelected()) {
			comboCategory.setSelectedIndex(0);
		}
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////// ENABLE EDITING 
	/**
	 * Changes view between preview and edit mode.
	 * 
	 * @param editable boolean flag to set edit state
	 */
	private void enableEditing(boolean editable) {
		inputStorageName.setEditable(editable);
		inputLogin.setEditable(editable);
		inputPassword.setEditable(editable);
		
		inputStorageName.setDrawFocus(editable);
		inputLogin.setDrawFocus(editable);
		inputPassword.setDrawFocus(editable);

		if (previewMode) {
			inputPassword.setEchoChar('\u00A4');
			
			checkWebsite.setEnabled(editable);
			checkCategory.setEnabled(editable);
			checkMore.setEnabled(editable);

			inputWebsite.setEnabled(editable);
			comboCategory.setEnabled(editable);
			inputMore.setEnabled(editable);
			scrollPane.setEnabled(editable);
			btnCancel.setText("back");
			
		} else if (editMode) {
			checkWebsite.setEnabled(editable);
			checkCategory.setEnabled(editable);
			checkMore.setEnabled(editable);

			inputWebsite.setEnabled(checkWebsite.isSelected());
			comboCategory.setEnabled(checkCategory.isSelected());
			inputMore.setEnabled(checkMore.isSelected());
			scrollPane.setEnabled(checkMore.isSelected());
			btnCancel.setText("cancel");
		}else {
		}
		btnDelete.setEnabled(!editable);
		repaint();
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////// GENERATE EDITS 
	/**
	 * Generates return based on the user edits.
	 * 
	 * @return String Parsed and Handled later 
	 */
	private String generatedEdits() {
		String result = "";

		if (iconEdit)
			result += "Icon ";
		if (storageEdit)
			result += "StorageName ";
		if (loginEdit)
			result += "Login ";
		if (passwordEdit)
			result += "Password ";
		if (websiteEdit)
			result += "Website ";
		if (categoryEdit)
			result += "Category ";
		if (moreEdit)
			result += "More ";
		
		return result;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////// EDIT PASSWORD
	private void editPassword() {
		if (iconEdit)
		pswd.setIconPath(iconPath);
		if (storageEdit)
			pswd.setStorageName(inputStorageName.getText());
		if (loginEdit)
			pswd.setLogin(inputLogin.getText());
		if (passwordEdit)
			pswd.setPassword(inputPassword.getPassword());
		if (websiteEdit)
			pswd.setWebsite(website);
		if (categoryEdit)
			pswd.setCategory(comboCategory.getSelectedCategory());
		if (moreEdit)
			pswd.setMore(more);

		pswd.setEditDate();
		PasswordManager.savePassword(pswd);
		PasswordManager.updateActiveList();
		((LibraryPanel) ((MainFrame) getParentFrame()).getLibraryPanel()).updateLibrary();
		showLibrary();
	}

	/**
	 * Initialize both columns
	 * 
	 */
	private void initPanel() {
		leftColumn();
		rightColumn();
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////// LOAD  
	/**
	 * Load values of the password
	 * 
	 */
	private void loadValues() {
		Icon.setIcon(Icons.load(pswd.getIconPath(), icon_size, 0.8));
		inputStorageName.setText(pswd.getStoragedName());
		inputLogin.setText(pswd.getLogin());
		inputPassword.setText(pswd.getPassword().substring(0, 20));
		//strengthBar.setString(pswd.getPasswordStrengthText());
		//strengthBar.setValue(pswd.getPasswordStrength() * 10);
		lblCreate.setText("Created:  "+pswd.getCreationDate());
		lblEdit.setText("Last Edit: "+pswd.getLastEditDate());
		
		if (!pswd.getWebsite().equals("null")) {
			checkWebsite.setSelected(true);
			inputWebsite.setText(pswd.getWebsite());
			if(inputWebsite.getText().length() > 5) btnOpenURL.setVisible(true);
		} else {
		}
		if (pswd.getCategory() != (PasswordCategory.valueOf("all"))) {
			checkCategory.setSelected(true);
			comboCategory.setSelectedItem(pswd.getCategory());
			comboCategory.getSelectedCategory();
		} else {
		}
		if (!pswd.getMore().equals("null")) {
			checkMore.setSelected(true);
			inputMore.setText(pswd.getMore());
		} else {
		}
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////// LEFT COLUMN  
	/**
	 * Initialize components of the left column of the Preview/Create Panel
	 * 
	 */
	private void leftColumn() {
		last_field_y = 10 * this.getHeight() / 100;
		left_column_x = 7 * this.getWidth() / 100;
		icon_size = 30 * this.getHeight() / 100;
		v_gap = 5 * this.getWidth() / 1000;
		Dimension button_size = new Dimension(icon_size, 30 * icon_size / 100);

		Icon = new TLabel("");
		Icon.setBorder(new TitledBorder(new LineBorder(UITheme.foreground, 1, true), "Icon", TitledBorder.LEADING, TitledBorder.TOP, UITheme.enlargedFont, UITheme.foreground));
		//Icon.setDrawShade(true);
		Icon.setHorizontalAlignment(SwingConstants.CENTER);
		Icon.setIcon(Icons.load(iconPath, icon_size, 0.8));
		Icon.setSize(icon_size, icon_size);
		Icon.setPreferredSize(icon_size, icon_size);
		Icon.setLocation(left_column_x, last_field_y);
		contentPane.add(Icon);

		// btn change icon
		btnChange.setVisible(false);
		btnChange.setSize(button_size.width, button_size.height / 2);
		btnChange.setPreferredSize(button_size.width, button_size.height / 2);
		btnChange.setLocation(left_column_x, Icon.getLocation().y + Icon.getHeight() + 2 * v_gap);
		btnChange.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!IconGalery.isGaleryVisible()) {
					IconGalery.showGalery(getCreatePanel(), new Point(btnChange.getLocationOnScreen().x + btnChange.getWidth() + v_gap,	btnChange.getLocationOnScreen().y));
				} else if (IconGalery.isGaleryVisible()) {
					IconGalery.hideGalery();
				}
			}
		});
		if (createMode || editMode) {	btnChange.setVisible(true);		}
		contentPane.add(btnChange);

		// btn add
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClickEvent(e);
				MouseEventAdd();
			}
		});
		btnAdd.setSize(button_size);
		btnAdd.setPreferredSize(button_size);
		btnAdd.setLocation(left_column_x, btnChange.getLocation().y + btnChange.getHeight() + Icon.getHeight() / 2 + v_gap);
		if (createMode) {	btnAdd.setText("add");		}
		contentPane.add(btnAdd);

		// btn save
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseClickEvent(e);
				MouseEventSave();
			}
		});
		btnSave.setSize(button_size);
		btnSave.setPreferredSize(button_size);
		btnSave.setLocation(left_column_x, btnChange.getLocation().y + btnChange.getHeight() + Icon.getHeight() / 2 + v_gap);
		if (previewMode || createMode) {btnSave.setVisible(false);		}
		contentPane.add(btnSave);

		// cancel btn
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseClickEvent(e);
				MouseEventCancel();
			}
		});
		btnCancel.setSize(button_size);
		btnCancel.setPreferredSize(button_size);
		btnCancel.setLocation(left_column_x, btnAdd.getLocation().y + btnAdd.getHeight() + v_gap);
		if (previewMode) {
			btnCancel.setText("back");
		}
		contentPane.add(btnCancel);
		
		// delete btn
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseClickEvent(e);
				MouseEventDelete();
			}
		});
		btnDelete.setForeground(UITheme.errorMsg);
		btnDelete.setSize(button_size.width, button_size.height/2);
		btnDelete.setPreferredSize(button_size.width, button_size.height/2);
		btnDelete.setLocation(left_column_x, btnCancel.getLocation().y + btnCancel.getHeight() + v_gap);
		if (editMode) {
			btnDelete.setEnabled(false);
		}
		if(createMode) {
			btnDelete.setVisible(false);
		}
		contentPane.add(btnDelete);
		
		
		lblCreate.setHorizontalAlignment(SwingConstants.LEADING);
		lblCreate.setSize(field_size);
		lblCreate.setPreferredSize(field_size);
		lblCreate.setLocation(left_column_x+5, this.getHeight()-3*field_size.height);
		contentPane.add(lblCreate);
		
		lblEdit.setHorizontalAlignment(SwingConstants.LEADING);
		lblEdit.setSize(field_size);
		lblEdit.setPreferredSize(field_size);
		lblEdit.setLocation(left_column_x+5, this.getHeight()-2*field_size.height);
		contentPane.add(lblEdit);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////// RIGHT COLUMN 
	/**
	 * Initialize components of the right column of the Preview/Create Panel
	 * 
	 */
	private void rightColumn() {
		last_field_y = 7 * this.getHeight() / 100;
		right_column_x = 40 * this.getWidth() / 100;
		v_gap = 5 * this.getWidth() / 1000;
		
		errorMsg.setFont(def);
		errorMsg.setForeground(UITheme.errorMsg);
		errorMsg.setHorizontalAlignment(SwingConstants.LEADING);
		errorMsg.setSize(field_size.width*2, field_size.height);
		errorMsg.setPreferredSize(field_size.width*2, field_size.height);
		errorMsg.setLocation(nextFieldLocation());
		contentPane.add(errorMsg);
		
		TLabel lblStorageName = new TLabel("Name");
		lblStorageName.setFont(def);
		lblStorageName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStorageName.setSize(label_size);
		lblStorageName.setPreferredSize(label_size);
		lblStorageName.setLocation(nextLabelLocation());
		if(createMode)lblStorageName.setToolTip("This will be displayed in the password library");
		contentPane.add(lblStorageName);

		inputStorageName = new TTextField();
		inputStorageName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (editMode) {
					storageEdit = true;
				}
			}
		});
		inputStorageName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClickEvent(e);
			}
		});
		inputStorageName.setFont(def);
		inputStorageName.setSize(field_size);
		inputStorageName.setPreferredSize(field_size);
		inputStorageName.setLocation(nextFieldLocation());
		contentPane.add(inputStorageName);

///////////////////////// IMPORTANT ///////////////////////// IMPORTANT ///////////////////////// IMPORTANT
		TLabel lblImportant = new TLabel("important");
		lblImportant.setFont(fontSection);
		lblImportant.setSize(label_size);
		lblImportant.setPreferredSize(label_size);
		Point temp = nextLabelLocation();
		lblImportant.setLocation(temp.x - 15 * v_gap, temp.y + 3 * v_gap);
		contentPane.add(lblImportant);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(UITheme.foreground);
		panel_1.setSize(40 * this.getWidth() / 100, 1);
		panel_1.setPreferredSize(new Dimension(35 * this.getWidth() / 100, 1));
		panel_1.setLocation(lblImportant.getLocation().x - v_gap,
				lblImportant.getLocation().y + lblImportant.getHeight() - 28 * lblImportant.getHeight() / 100);
		last_field_y = panel_1.getLocation().y + panel_1.getHeight() + v_gap;
		contentPane.add(panel_1);

		TLabel lblLogin = new TLabel("Login/Email");
		lblLogin.setFont(def);
		lblLogin.setSize(label_size);
		lblLogin.setPreferredSize(label_size);
		lblLogin.setLocation(nextLabelLocation());
		lblLogin.setHorizontalAlignment(SwingConstants.TRAILING);
		if(createMode)lblLogin.setToolTip("Type in required login or email");
		contentPane.add(lblLogin);

		inputLogin = new TTextField();
		inputLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (editMode) {
					loginEdit = true;
				}
			}
		});
		inputLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClickEvent(e);
			}
		});
		inputLogin.setFont(def);
		inputLogin.setSize(field_size);
		inputLogin.setPreferredSize(field_size);
		inputLogin.setLocation(nextFieldLocation());
		contentPane.add(inputLogin);

// coppy msg
		copyMsg.setSize(field_size.width, field_size.height);
		copyMsg.setPreferredSize(field_size.width, field_size.height);
		copyMsg.setLocation(inputLogin.getLocation().x + inputLogin.getWidth() + v_gap, inputLogin.getLocation().y);
		contentPane.add(copyMsg);

		TLabel lblPassword_1 = new TLabel("Password");
		lblPassword_1.setFont(def);
		lblPassword_1.setSize(label_size);
		lblPassword_1.setPreferredSize(label_size);
		lblPassword_1.setLocation(nextLabelLocation());
		lblPassword_1.setHorizontalAlignment(SwingConstants.TRAILING);
		if(createMode)lblPassword_1.setToolTip("Type in corresponding password");
		contentPane.add(lblPassword_1);

		inputPassword = new TPasswordField();
		inputPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseClickEvent(e);
				if(editMode) {
					if(!passwordEdit) {
						inputPassword.setText("");
						inputPassword.setEchoChar((char) 0);
					}
				}
			}
		});
		inputPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				strengthUpdate();
				
				if (editMode) {
					passwordEdit = true;
				}
			}
		});
		inputPassword.setFont(def);
		inputPassword.setEchoChar('\u00A4');
		inputPassword.setSize(field_size);
		inputPassword.setPreferredSize(field_size);
		inputPassword.setLocation(nextFieldLocation());
		if (createMode || editMode) {
			inputPassword.setEchoChar((char) 0);
		}
		if (previewMode) {
			inputPassword.setEchoChar('\u00A4');
		}
		contentPane.add(inputPassword);

// btn generate
		btnGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClickEvent(e);
				MouseEventGenerate();
			}
		});
		btnGenerate.setSize(field_size.width / 3, field_size.height);
		btnGenerate.setPreferredSize(field_size.width / 3, field_size.height);
		btnGenerate.setLocation(inputPassword.getLocation().x + inputPassword.getWidth() + v_gap,
				inputPassword.getLocation().y);
		if (previewMode) {
			btnGenerate.setText("copy");
		}
		contentPane.add(btnGenerate);

		TLabel lblStrngth = new TLabel();
		lblStrngth.setFont(def);
		lblStrngth.setSize(label_size);
		lblStrngth.setPreferredSize(label_size);
		lblStrngth.setLocation(nextLabelLocation());
		lblStrngth.setHorizontalAlignment(SwingConstants.TRAILING);
		contentPane.add(lblStrngth);

		strengthBar = new TProgressBar();
		strengthBar.setString("");
		strengthBar.setSize(field_size.width - 4, field_size.height - 4);
		strengthBar.setPreferredSize(field_size.width - 4, field_size.height - 4);
		temp = nextFieldLocation();
		strengthBar.setBarLocation(temp.x, temp.y);
		last_field_y = strengthBar.getLocation().y;
		if(createMode)strengthBar.setToolTip("This element shows You estimated strength of the given password");
		if(createMode)contentPane.add(strengthBar);

///////////////////////// OPTIONAL ///////////////////////// OPTIONAL ///////////////////////// OPTIONAL
		TLabel lblOptional = new TLabel("optional");
		lblOptional.setFont(fontSection);
		lblOptional.setSize(label_size);
		lblOptional.setPreferredSize(label_size);
		temp = nextLabelLocation();
		lblOptional.setLocation(temp.x - 15 * v_gap, temp.y + 3 * v_gap);
		contentPane.add(lblOptional);

		JPanel panel_3 = new JPanel();
		panel_3.setSize(40 * this.getWidth() / 100, 1);
		panel_3.setPreferredSize(new Dimension(35 * this.getWidth() / 100, 1));
		panel_3.setLocation(lblOptional.getLocation().x - v_gap,
				lblOptional.getLocation().y + lblOptional.getHeight() - 28 * lblOptional.getHeight() / 100);
		last_field_y = panel_3.getLocation().y + panel_3.getHeight() + v_gap;
		panel_3.setBackground(UITheme.foreground);
		contentPane.add(panel_3);

		TLabel lblWebsite_1 = new TLabel("Website");
		lblWebsite_1.setFont(def);
		lblWebsite_1.setSize(label_size);
		lblWebsite_1.setPreferredSize(label_size);
		lblWebsite_1.setLocation(nextLabelLocation());
		lblWebsite_1.setHorizontalAlignment(SwingConstants.TRAILING);
		if(createMode)lblWebsite_1.setToolTip("This field stores website URL");
		contentPane.add(lblWebsite_1);

		checkWebsite = new TCheckBox();
		checkWebsite.setIconWidth(750*label_size.height/1000);
		checkWebsite.setIconHeight(750*label_size.height/1000);
		checkWebsite.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateOptional();
				if (IconGalery.isGaleryVisible())
					IconGalery.hideGalery();
				if(editMode) {
					if(checkWebsite.isSelected()) { // on
					}
					else if(!checkWebsite.isSelected()) {// off
						if(inputWebsite.getText().length() > 0) {
							inputWebsite.setText("");
							websiteEdit = true;
						}
					}
				}
			}
		});
		checkWebsite.setSize(750*label_size.height/1000, 750*label_size.height/1000);
		checkWebsite.setLocation(lblWebsite_1.getLocation().x - 5*v_gap, lblWebsite_1.getLocation().y);
		contentPane.add(checkWebsite);

		inputWebsite = new TTextField();
		inputWebsite.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (editMode) {
					websiteEdit = true;
				}
				if (IconGalery.isGaleryVisible())
					IconGalery.hideGalery();
			}
		});
		inputWebsite.setFont(def);
		inputWebsite.setEnabled(false);
		inputWebsite.setSize(field_size);
		inputWebsite.setPreferredSize(field_size);
		inputWebsite.setLocation(nextFieldLocation());
		contentPane.add(inputWebsite);
		
//btnOpenURL
		if(inputWebsite.getText().length() > 5) {
			
		}
		btnOpenURL.setVisible(false);
		btnOpenURL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClickEvent(e);
				MouseEventOpenURL();
			}
		});
		btnOpenURL.setSize(field_size.width / 3, field_size.height);
		btnOpenURL.setPreferredSize(field_size.width / 3, field_size.height);
		btnOpenURL.setLocation(inputWebsite.getLocation().x + inputWebsite.getWidth() + v_gap,
				inputWebsite.getLocation().y);
		if (createMode) {
			btnOpenURL.setVisible(false);
		}
		contentPane.add(btnOpenURL);
		
		TLabel lblCategory_1 = new TLabel("Category");
		lblCategory_1.setFont(def);
		lblCategory_1.setSize(label_size);
		lblCategory_1.setPreferredSize(label_size);
		lblCategory_1.setLocation(nextLabelLocation());
		lblCategory_1.setHorizontalAlignment(SwingConstants.TRAILING);
		if(createMode)lblCategory_1.setToolTip("This values let You sort passwords with given category");
		contentPane.add(lblCategory_1);

		checkCategory = new TCheckBox();
		checkCategory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateOptional();
				if (IconGalery.isGaleryVisible())
					IconGalery.hideGalery();
				if(!checkCategory.isSelected()) {
					comboCategory.setSelectedIndex(0);
				}
			}
		});
		checkCategory.setIconWidth(750*label_size.height/1000);
		checkCategory.setIconHeight(750*label_size.height/1000);
		checkCategory.setSize(750*label_size.height/1000, 750*label_size.height/1000);
		checkCategory.setLocation(lblCategory_1.getLocation().x - 5*v_gap, lblCategory_1.getLocation().y);
		contentPane.add(checkCategory);

		comboCategory = new TCombo();
		comboCategory.setEnabled(false);
		comboCategory.setSize(field_size);
		comboCategory.setPreferredSize(field_size);
		comboCategory.setLocation(nextFieldLocation());
		comboCategory.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if (editMode) {
					categoryEdit = true;
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if (IconGalery.isGaleryVisible())
					IconGalery.hideGalery();
			}
		});
		contentPane.add(comboCategory);

		TLabel lblMore_1 = new TLabel("More");
		lblMore_1.setFont(def);
		lblMore_1.setSize(label_size);
		lblMore_1.setPreferredSize(label_size);
		lblMore_1.setLocation(nextLabelLocation());
		lblMore_1.setHorizontalAlignment(SwingConstants.TRAILING);
		if(createMode)lblMore_1.setToolTip("This field can store more information that did not fit");
		contentPane.add(lblMore_1);

		checkMore = new TCheckBox();
		checkMore.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateOptional();
				if (IconGalery.isGaleryVisible())
					IconGalery.hideGalery();
				if(editMode) {
					if(checkMore.isSelected()) { // on
					}
					else if(!checkMore.isSelected()) {// off
						if(inputMore.getText().length() > 0) {
							inputMore.setText("");
							moreEdit = true;
						}
					}
				}
			}
		});
		checkMore.setIconWidth(750*label_size.height/1000);
		checkMore.setIconHeight(750*label_size.height/1000);
		checkMore.setSize(750*label_size.height/1000, 750*label_size.height/1000);
		checkMore.setLocation(lblMore_1.getLocation().x - 5*v_gap, lblMore_1.getLocation().y);
		contentPane.add(checkMore);

		inputMore = new TTextArea();
		inputMore.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		inputMore.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (editMode) {
					moreEdit = true;
				}
				if (IconGalery.isGaleryVisible())
					IconGalery.hideGalery();
			}
		});
		inputMore.setEnabled(false);
		inputMore.setFont(def);

		scrollPane = new WebScrollPane(inputMore);
		scrollPane.setEnabled(false);
		scrollPane.setRound(4);
		scrollPane.setSize(field_size.width, field_size.height * 5);
		scrollPane.setPreferredSize(field_size.width, field_size.height * 5);
		scrollPane.setLocation(nextFieldLocation());
		scrollPane.setMargin(new Insets(1, 1, 1, 1));
		scrollPane.setShadeWidth(0);
		scrollPane.setDrawFocus(false);
		scrollPane.setPaintButtons(false);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////// EVENTS
	/**
	 * Adds new password to library (Create view) or enables password edit (Edit view).
	 * 
	 */
	private void MouseEventAdd() {
		if (previewMode) {
			editMode = true;
			previewMode = false;
			btnChange.setVisible(true);
			btnSave.setVisible(true);
			btnAdd.setVisible(false);
			enableEditing(true);
		}
		if (createMode) {
			if (checkInput()) {
				Password newPswd = new Password(
						iconPath, 
						inputStorageName.getText(),
						inputLogin.getText(),
						gishi.control.SecurityManager.encryptField(String.valueOf(inputPassword.getPassword())),
						website, 
						comboCategory.getSelectedCategory(), 
						more);
				PasswordManager.addNewPassword(newPswd);
				//PasswordManager.updateActiveList();
				((LibraryPanel) ((MainFrame) getParentFrame()).getLibraryPanel()).updateLibrary();
				// close panel move to library
				showLibrary();
				// clear create state for future input
				((MainFrame) getParentFrame()).setCreateState(false);
			}
		}
	}
	
	/**
	 * Save and possibly override 
	 * 
	 */
	private void MouseEventSave() {
		if(generatedEdits().length() > 0) {
			if (checkInput()) {
				String msg = "Are You sure to override values of this fields: \n" + generatedEdits();
				if (WebOptionPane.showConfirmDialog(getCreatePanel(), msg, "Confirm",	WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0) {
					
					previewMode = true;
					editMode = false;
					btnChange.setVisible(false);
					btnSave.setVisible(false);
					btnAdd.setVisible(true);
					enableEditing(false);
					editPassword();
				} else {
				}
			} else {
			}
		}else{
			previewMode = true;
			editMode = false;
			btnChange.setVisible(false);
			btnSave.setVisible(false);
			btnAdd.setVisible(true);
			enableEditing(false);
		}
	}
	
	/**
	 * Depending on the mode [preview / edit / create].
	 * Cancels the operation.
	 * 
	 */
	private void MouseEventCancel() {
		if (previewMode) {
			showLibrary();
			((LibraryPanel) ((MainFrame) getParentFrame()).getLibraryPanel()).updateLibrary();
		}
		if (editMode) {
			loadValues();
			previewMode = true;
			editMode = false;
			btnChange.setVisible(false);
			btnSave.setVisible(false);
			btnAdd.setVisible(true);
			enableEditing(false);
		}
		if (createMode) {
			((MainFrame) getParentFrame()).setCreateState(false);
			((MainFrame) getParentFrame()).swapRight(((MainFrame) getParentFrame()).getContentPane(),
					((MainFrame) getParentFrame()).getActivePanel(),
					((MainFrame) getParentFrame()).getWelcomePanel());
			this.setVisible(false);
		}
	}
	
	/**
	 * Deletes password from library.
	 * 
	 */
	private void MouseEventDelete() {
		String msg = "(This action is permanent)\nAre You sure to delete this password storage?\n";
		if (WebOptionPane.showConfirmDialog(getCreatePanel(), msg, "Delete",	WebOptionPane.YES_NO_OPTION, WebOptionPane.WARNING_MESSAGE) == 0) {
			try {
				PasswordManager.deletePassword(pswd);
				((LibraryPanel) ((MainFrame) getParentFrame()).getLibraryPanel()).updateLibrary();
				showLibrary();
			} catch (IOException ex) {
				ExceptionManager.manage(ex, CreatePanel.class, "Problem while deleting password");
				ex.printStackTrace();
			}
		}
		else {
			
		}
	}
	
	/**
	 * If supported, lets user open a web browser to given address.
	 * 
	 */
	private void MouseEventOpenURL() {
		String url = "";	//ftp? other?
		if(inputWebsite.getText().length() > 5)
		if(!inputWebsite.getText().matches("^www.*$")) {
			if(inputWebsite.getText().matches("^\\..*$")) {
				url = "www" + inputWebsite.getText().toString();
			}else {
				url = "www." + inputWebsite.getText().toString();
			}
		}
		else {
			url = "" + inputWebsite.getText().toString();
		}
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	/**
	 * Depending on the mode (preview / edit) copies password to clipboard or generates new password.
	 * </br>
	 * Generated password: ALPHANUMERIC + SYMBOL
	 * </br>
	 * Length: 10 + r.nextInt(12)
	 * 
	 */
	private void MouseEventGenerate() {
		if (previewMode || editMode) {
			new TimedMessage(copyMsg, "Copied to clipboard.", 1);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
					new StringSelection(gishi.control.SecurityManager.decryptField(pswd.getPassword())), null);
		} else if (createMode) {
			SecureRandom r = new SecureRandom();
			String generatedpswd = Generator.generateRandomPassword(Generator.CharacterTypes.ALPHANUMERICSYMBOL, 10 + r.nextInt(12));
			inputPassword.setText(generatedpswd);
			strengthUpdate();
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////// BAR UPDATE 
	/**
	 * Updates strengthBar to display current strength of password.
	 * 
	 */
	private void strengthUpdate() {
		String valueString = "";
		int value = (int) (1*PasswordManager.nbvcxz.estimate(String.copyValueOf(inputPassword.getPassword())).getEntropy());
		if(value < 37) {
			valueString = "weak";
		}else if(value < 45) {
			valueString = "mediocre";
		}else if(value < 53) {
			valueString = "semi-strong";
		}else if(value < 80) {
			valueString = "strong";
		}else if(value < 120) {
			valueString = "very strong";
		}else {
			valueString = "overkill";
		}
		strengthBar.setString(valueString);
		strengthBar.setValue(125*value/100);
	}
	
	/**
	 * Gets user back to Password Library view
	 * 
	 */
	private void showLibrary() {
		((MainFrame) getParentFrame()).swapRight(((MainFrame) getParentFrame()).getContentPane(),
				((MainFrame) getParentFrame()).getActivePanel(),
				((MainFrame) getParentFrame()).getLibraryPanel());
	}
	
	/**
	 * Calculate
	 * @return position of next Label Component
	 */
	private Point nextLabelLocation() {
		last_field_y += field_size.height + v_gap;
		return new Point(right_column_x, last_field_y);
	}
	
	/**
	 * Calculate
	 * @return position of next Field Component
	 */
	private Point nextFieldLocation() {
		// last_field_y += field_size.height;
		return new Point(right_column_x + label_size.width + 3*v_gap, last_field_y);
	}
	
	/**
	 * Loads icon from given path
	 * 
	 * @param iconPath name of the icon
	 */
	public void getSelectedIconPath(String iconPath) {
		this.iconPath = iconPath;
		iconEdit = true;
		Icon.setIcon(Icons.load(iconPath, icon_size, 0.8));
	}

}

//OLD METHOD
//if (createMode) {
//TButton btnGenerate2 = new TButton("+");
//btnGenerate2.setForeground(UITheme.foreground);
//btnGenerate2.setSize(field_size.width / 6, field_size.height);
//btnGenerate2.setPreferredSize(field_size.width / 4, field_size.height);
//btnGenerate2.setLocation(btnGenerate.getLocation().x + btnGenerate.getWidth(), btnGenerate.getLocation().y);
//contentPane.add(btnGenerate2);
//
//WebButtonPopup popup = new WebButtonPopup(btnGenerate2, PopupWay.rightDown);
//popup.setWebColoredBackground(false);
//popup.setShadeWidth(0);
//popup.setBackground(UITheme.background);
//popup.setForeground(UITheme.foreground);
//popup.addMouseListener(new MouseAdapter() {
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		mouseClickEvent(e);
//	}
//});
//// Popup content
//WebLabel label = new WebLabel(".............................", WebLabel.CENTER);
//label.setForeground(UITheme.background);
//JSpinner spinner1 = new JSpinner();
//spinner1.setModel(new SpinnerListModel(new String[] {"passphrase", "random"}));
//spinner1.setValue("random");
//JSpinner spinner = new JSpinner();
//spinner.setModel(new SpinnerListModel(new String[] {"passphrase", "medium", "strong", "very strong"}));
//spinner.setValue("strong");
//TButton ok = new TButton("ok");
//ok.addMouseListener(new MouseAdapter() {
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		popup.hidePopup();
//		setSpinnerValue(spinner.getValue().toString());
//	}
//});
//GroupPanel content = new GroupPanel(5, false, label, spinner1, spinner, ok);
//content.setMargin(5);
//popup.setContent(content);
//}
