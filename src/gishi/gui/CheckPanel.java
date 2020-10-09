package gishi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ResourceBundle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.panel.WebPanel;

import gishi.control.PasswordManager;
import gishi.control.UITheme;
import gishi.templates.ContentPanel;
import gishi.templates.TCheckBox;
import gishi.templates.TLabel;
import gishi.templates.TPasswordField;
import gishi.templates.TTextArea;
import nbvcxz.matching.match.DictionaryMatch;
import nbvcxz.matching.match.Match;
import nbvcxz.resources.Feedback;
import nbvcxz.scoring.Result;
import nbvcxz.scoring.TimeEstimate;

@SuppressWarnings("serial")
public class CheckPanel extends ContentPanel {

	int v_gap;
	int last_field_y;
	private int left_column_x;
	private int right_column_x;
	private Dimension label_size;
	private Dimension field_size;
	private Font fontSection = new Font("Tahoma", Font.BOLD, 25 * UITheme.frameHeight / 1000);
	private Font fontLabel = new Font("Tahoma", Font.PLAIN, 25 * UITheme.frameHeight / 1000);
	
	ResourceBundle resourceBundle = ResourceBundle.getBundle("main", PasswordManager.nbvcxz.getConfiguration().getLocale());
	
	private WebPanel contentPane;
	private TPasswordField passwordField;
	
	private String warningString = "";
	private String suggestionString = "";
	private TTextArea warning;
	private TTextArea suggestion;
	
	private TLabel lenght;
	private TLabel lower;
	private TLabel capital;
	private TLabel number;
	private TLabel special;

	private TLabel entropy;
	
	private TLabel crackTime1;
	private TLabel crackTime2;
	private TLabel crackTime3;
	
	private String attackString = "";
	private TTextArea attack;

	/**
	 * Initialize CheckPanel.
	 * Contains view that lets user check strength of a password.
	 * 
	 * @param parent
	 */
	public CheckPanel(MainFrame parent) {

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
		add(contentPane);
		
		label_size = new Dimension(200 * getWidth() / 1000, 25 * getWidth() / 1000);
		field_size = new Dimension(250 * getWidth() / 1000, 25 * getWidth() / 1000);
		v_gap = 7 * this.getWidth() / 1000;

		last_field_y = 1 * getHeight() / 100;
		left_column_x = 50 * getWidth() / 100;
		right_column_x = left_column_x + label_size.width + v_gap;

		passwordSection();
		statisticSection();
	}
	
	Result result;
	Feedback feedback;
	long start;
	long end;
	
	/**
	 * Initialize left side components of ChackPanel.
	 * 
	 */
	private void passwordSection() {
		
		passwordField = new TPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(passwordField.getPassword().length > 0) {
					start = System.currentTimeMillis();
			        result = PasswordManager.nbvcxz.estimate(new String(passwordField.getPassword()));
			        feedback = result.getFeedback();
			        end = System.currentTimeMillis();
			        
					updateStatistics();
				}
			}
		});
		passwordField.setEchoChar('\u00A4');
		passwordField.setFont(UITheme.boldEnlargedFont);
		passwordField.setSize(30*getWidth()/100, 5*getHeight()/100);
		passwordField.setLocation(10*getWidth()/100, 30*getHeight()/100);
		contentPane.add(passwordField);
		
		TCheckBox showChBox = new TCheckBox("Show input");
		showChBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(showChBox.isSelected()){
					passwordField.setEchoChar((char) 0);
				}else {
					passwordField.setEchoChar('\u00A4');
				}
			}
		});
		showChBox.setBounds(98, 224, 97, 23);
		showChBox.setFont(UITheme.defaultFont);
		showChBox.setForeground(UITheme.foreground);
		showChBox.setRequestFocusEnabled(false);
		showChBox.setFocusable(false);
		showChBox.setSelected(false);
		contentPane.add(showChBox);
		
		TLabel lblPassword = new TLabel("check password:");
		lblPassword.setFont(fontSection);
		lblPassword.setSize(passwordField.getSize());
		lblPassword.setLocation(passwordField.getLocation().x, passwordField.getLocation().y - passwordField.getHeight() - v_gap);
		contentPane.add(lblPassword);
		
		warning = new TTextArea();
		warning.setEditable(false);
		warning.setFont(fontLabel);
		warning.setBackground(UITheme.background);
		warning.setSize(40*getWidth()/100, 15*getHeight()/100);
		warning.setLocation(5*getWidth()/100, passwordField.getLocation().y + passwordField.getHeight() + 7*v_gap);
		contentPane.add(warning);
		
		suggestion = new TTextArea();
		suggestion.setEditable(false);
		suggestion.setFont(fontLabel);
		suggestion.setBackground(UITheme.background);
		suggestion.setSize(40*getWidth()/100, 35*getHeight()/100);
		suggestion.setLocation(5*getWidth()/100, warning.getLocation().y + warning.getHeight());
		contentPane.add(suggestion);
	}
	
	/**
	 * Initialize right side components of ChackPanel.
	 * Contains Password strength statistics.
	 * 
	 */
	private void statisticSection() {
		WebPanel panel = new WebPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(475, 11, 1, 599);
		contentPane.add(panel);

		TLabel lblStatistics = new TLabel("");
		lblStatistics.setFont(fontLabel);
		lblStatistics.setSize(label_size);
		lblStatistics.setPreferredSize(label_size);
		lblStatistics.setLocation(nextLabelLocation());
		contentPane.add(lblStatistics);
		
		TLabel lblLenght = new TLabel("password lenght");
		lblLenght.setFont(fontLabel);
		lblLenght.setSize(label_size);
		lblLenght.setPreferredSize(label_size);
		lblLenght.setLocation(nextLabelLocation());
		contentPane.add(lblLenght);
		
		lenght = new TLabel();
		lenght.setFont(fontLabel);
		lenght.setSize(field_size);
		lenght.setPreferredSize(field_size);
		lenght.setLocation(nextFieldLocation());
		contentPane.add(lenght);
		
		TLabel lblEntropy = new TLabel("password entropy");
		lblEntropy.setFont(fontLabel);
		lblEntropy.setSize(label_size);
		lblEntropy.setPreferredSize(label_size);
		lblEntropy.setLocation(nextLabelLocation());
		contentPane.add(lblEntropy);
		
		entropy = new TLabel();
		entropy.setFont(fontLabel);
		entropy.setSize(field_size);
		entropy.setPreferredSize(field_size);
		entropy.setLocation(nextFieldLocation());
		contentPane.add(entropy);
		
		nextLabelLocation();
		TLabel lblCharacters = new TLabel("used characters");
		lblCharacters.setFont(fontSection);
		lblCharacters.setSize(label_size);
		lblCharacters.setPreferredSize(label_size);
		lblCharacters.setLocation(nextLabelLocation());
		contentPane.add(lblCharacters);
		
		TLabel lblLower = new TLabel("a - z");
		lblLower.setFont(fontLabel);
		lblLower.setSize(label_size);
		lblLower.setPreferredSize(label_size);
		lblLower.setLocation(nextLabelLocation());
		contentPane.add(lblLower);
		
		lower = new TLabel();
		lower.setFont(fontLabel);
		lower.setSize(field_size);
		lower.setPreferredSize(field_size);
		lower.setLocation(nextFieldLocation());
		contentPane.add(lower);
		
		TLabel lblCapital = new TLabel("A - Z");
		lblCapital.setFont(fontLabel);
		lblCapital.setSize(label_size);
		lblCapital.setPreferredSize(label_size);
		lblCapital.setLocation(nextLabelLocation());
		contentPane.add(lblCapital);
		
		capital = new TLabel();
		capital.setFont(fontLabel);
		capital.setSize(label_size);
		capital.setPreferredSize(label_size);
		capital.setLocation(nextFieldLocation());
		contentPane.add(capital);
		
		TLabel lblNumber = new TLabel("0 - 9");
		lblNumber.setFont(fontLabel);
		lblNumber.setSize(label_size);
		lblNumber.setPreferredSize(label_size);
		lblNumber.setLocation(nextLabelLocation());
		contentPane.add(lblNumber);
		
		number = new TLabel();
		number.setFont(fontLabel);
		number.setSize(label_size);
		number.setPreferredSize(label_size);
		number.setLocation(nextFieldLocation());
		contentPane.add(number);
		
		TLabel lblSpecial = new TLabel("#%.*");
		lblSpecial.setFont(fontLabel);
		lblSpecial.setSize(label_size);
		lblSpecial.setPreferredSize(label_size);
		lblSpecial.setLocation(nextLabelLocation());
		contentPane.add(lblSpecial);
		
		special = new TLabel();
		special.setFont(fontLabel);
		special.setSize(label_size);
		special.setPreferredSize(label_size);
		special.setLocation(nextFieldLocation());
		contentPane.add(special);
		
		nextLabelLocation();
		TLabel lblCrackTime = new TLabel("time to crack");
		lblCrackTime.setFont(fontSection);
		lblCrackTime.setSize(label_size);
		lblCrackTime.setPreferredSize(label_size);
		lblCrackTime.setLocation(nextLabelLocation());
		contentPane.add(lblCrackTime);
		
		TLabel lblCrackTime1 = new TLabel("online");
		lblCrackTime1.setFont(fontLabel);
		lblCrackTime1.setSize(label_size);
		lblCrackTime1.setPreferredSize(label_size);
		lblCrackTime1.setLocation(nextLabelLocation());
		contentPane.add(lblCrackTime1);
		
		crackTime1 = new TLabel();
		crackTime1.setFont(fontLabel);
		crackTime1.setSize(field_size);
		crackTime1.setPreferredSize(field_size);
		crackTime1.setLocation(nextFieldLocation());
		contentPane.add(crackTime1);
		
		TLabel lblCrackTime2 = new TLabel("offline SHA512");
		lblCrackTime2.setFont(fontLabel);
		lblCrackTime2.setSize(label_size);
		lblCrackTime2.setPreferredSize(label_size);
		lblCrackTime2.setLocation(nextLabelLocation());
		contentPane.add(lblCrackTime2);
		
		crackTime2 = new TLabel();
		crackTime2.setFont(fontLabel);
		crackTime2.setSize(field_size);
		crackTime2.setPreferredSize(field_size);
		crackTime2.setLocation(nextFieldLocation());
		contentPane.add(crackTime2);
		
		TLabel lblCrackTime3 = new TLabel("offline MD5");
		lblCrackTime3.setFont(fontLabel);
		lblCrackTime3.setSize(label_size);
		lblCrackTime3.setPreferredSize(label_size);
		lblCrackTime3.setLocation(nextLabelLocation());
		contentPane.add(lblCrackTime3);
		
		crackTime3 = new TLabel();
		crackTime3.setFont(fontLabel);
		crackTime3.setSize(field_size);
		crackTime3.setPreferredSize(field_size);
		crackTime3.setLocation(nextFieldLocation());
		contentPane.add(crackTime3);
		
		nextLabelLocation();
		TLabel lblAtack = new TLabel("applicable attacks");
		lblAtack.setFont(fontSection);
		lblAtack.setSize(label_size);
		lblAtack.setPreferredSize(label_size);
		lblAtack.setLocation(nextLabelLocation());
		contentPane.add(lblAtack);
		
		attackString = "";
		attack = new TTextArea();
		attack.setEditable(false);
		attack.setFont(fontLabel);
		attack.setBackground(UITheme.background);
		attack.setSize(label_size.width*2, label_size.height*7);
		attack.setPreferredSize(attack.getSize());
		attack.setLocation(nextLabelLocation());
		contentPane.add(attack);
		
		
	}
	
	
	private void updateStatistics(){
		lenght.setText(passwordField.getPassword().length+"");
		entropy.setText(""+result.getEntropy().toString().substring(0, 5));
		checkCharacters();
/*
 *		"OFFLINE_MD5"        "OFFLINE_SHA1"        "OFFLINE_SHA512"        "OFFLINE_BCRYPT_5"        "OFFLINE_BCRYPT_10"
 *        "OFFLINE_BCRYPT_12"        "OFFLINE_BCRYPT_14"        "ONLINE_UNTHROTTLED"        "ONLINE_THROTTLED"
 */
		if(TimeEstimate.getTimeToCrackFormatted(result, "ONLINE_UNTHROTTLED").equals("instant")) {
			crackTime1.setText(""+TimeEstimate.getTimeToCrackFormatted(result, "ONLINE_UNTHROTTLED"));
		}else {
			if(TimeEstimate.getTimeToCrackFormatted(result, "ONLINE_UNTHROTTLED").substring(0, 3).equals("inf"))
				crackTime1.setText(""+TimeEstimate.getTimeToCrackFormatted(result, "ONLINE_UNTHROTTLED"));
			else
				crackTime1.setText("~ "+TimeEstimate.getTimeToCrackFormatted(result, "ONLINE_UNTHROTTLED"));
		}
		if(TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_SHA512").equals("instant")) {
			crackTime2.setText(""+TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_SHA512"));
		}else {
			if(TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_SHA512").substring(0, 3).equals("inf"))
				crackTime2.setText(""+TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_SHA512"));
			else
				crackTime2.setText("~ "+TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_SHA512"));
		}
		if(TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_SHA512").equals("instant")) {
			crackTime3.setText(""+TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_MD5"));
		}else {
			if(TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_SHA512").substring(0, 3).equals("inf"))
				crackTime3.setText(""+TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_MD5"));
			else
				crackTime3.setText("~ "+TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_MD5"));
		}
		
        attackString = "";
        boolean bruteforce 	= false;
        boolean dictionary 	= false;
        boolean spacial   	= false;
        boolean date   		= false;
        boolean pattern   	= false;
        for (Match match : result.getMatches())
        {
        	if(match.getClass() == nbvcxz.matching.match.BruteForceMatch.class && !bruteforce) {
        		 bruteforce = true;
        		 attackString += "\n bruteForce";
        	}
        	else if(match.getClass() == nbvcxz.matching.match.DictionaryMatch.class && !dictionary) {
        		dictionary = true;
       		 	attackString += "\n dictionary";
       		 	if(((DictionaryMatch) match).isLeet()) {
       		 	attackString += " (with L33t substitution)";
       		 	}
        	}
        	else if(match.getClass() == nbvcxz.matching.match.SpacialMatch.class && !spacial) {
         		 spacial = true;
         		 attackString += "\n spacial recognition";
          	}
        	else if((match.getClass() == nbvcxz.matching.match.DateMatch.class 
        			|| match.getClass() == nbvcxz.matching.match.YearMatch.class) && !date) {
          		 date = true;
          		 attackString += "\n date match";
           	}
        	else if((match.getClass() == nbvcxz.matching.match.RepeatMatch.class 
        			||match.getClass() == nbvcxz.matching.match.SequenceMatch.class) && !pattern) {
        		 pattern = true;
          		 attackString += "\n pattern recognition";
           	}
        }
        attack.setText(attackString);
        
        updateWarnings();
	}
	
	/**
	 * Shows user warnings and suggestions accordingly to input.
	 * 
	 */
	private void updateWarnings() {
		warningString = "";
		if (feedback.getWarning() != null)
        {
			warningString = "Warnings:";
			warningString += "\n" + feedback.getWarning();
        }
		warning.setText(warningString);
		boolean counter = false;
		suggestionString = "";
        for (String suggestions : feedback.getSuggestion())
        {
        	if(!counter) {
        		suggestionString = "Suggestions:";
        		counter = true;
        	}
        	suggestionString += "\n" + suggestions;
        }
        suggestion.setText(suggestionString);
	}
	
	
	private void checkCharacters() {

		String pswd = new String(passwordField.getPassword());
		
		if (pswd.matches("(?=.*[0-9].*[0-9].*[0-9]).*")) {
			number.setText("\u2713\u2713\u2713");
		}else if (pswd.matches("(?=.*[0-9].*[0-9]).*")) {
			number.setText("\u2713\u2713");
		}else if (pswd.matches("(?=.*[0-9]).*")) {
			number.setText("\u2713");
		}else {
			number.setText("");
		}
		
		if (pswd.matches("(?=.*[a-z].*[a-z].*[a-z]).*")) {
			lower.setText("\u2713\u2713\u2713");
		}else if (pswd.matches("(?=.*[a-z].*[a-z]).*")) {
			lower.setText("\u2713\u2713");
		}
		else if (pswd.matches("(?=.*[a-z]).*")) {
			lower.setText("\u2713");
		}else {
			lower.setText("");
		}
		
		if (pswd.matches("(?=.*[A-Z].*[A-Z].*[A-Z]).*")) {
			capital.setText("\u2713\u2713\u2713");
		}else if (pswd.matches("(?=.*[A-Z].*[A-Z]).*")) {
			capital.setText("\u2713\u2713");
		}
		else if (pswd.matches("(?=.*[A-Z]).*")) {
			capital.setText("\u2713");
		}else {
			capital.setText("");
		}
		
		if (pswd.matches("(?=.*[~!@#$%^&*()_-].*[~!@#$%^&*()_-].*[~!@#$%^&*()_-]).*")) {
			special.setText("\u2713\u2713\u2713");
		}else if (pswd.matches("(?=.*[~!@#$%^&*()_-].*[~!@#$%^&*()_-]).*")) {
			special.setText("\u2713\u2713");
		}
		else if (pswd.matches("(?=.*[~!@#$%^&*()_-]).*")) {
			special.setText("\u2713");
		}else {
			special.setText("");
		}
	}
	
	
	private Point nextLabelLocation() {
		last_field_y += label_size.height;
		return new Point(left_column_x, last_field_y);
	}

	private Point nextFieldLocation() {
		// last_field_y += field_size.height;
		return new Point(right_column_x, last_field_y);
	}
}
