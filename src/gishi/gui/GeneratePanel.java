package gishi.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.slider.WebSlider;
import com.alee.utils.swing.UnselectableButtonGroup;

import gishi.control.PasswordManager;
import gishi.control.UITheme;
import gishi.templates.ContentPanel;
import gishi.templates.TButton;
import gishi.templates.TCheckBox;
import gishi.templates.TCombo;
import gishi.templates.TLabel;
import gishi.templates.TTextField;
import nbvcxz.resources.Generator;
import nbvcxz.resources.Generator.CharacterTypes;

@SuppressWarnings("serial")
public class GeneratePanel extends ContentPanel{

	int v_gap;
	private int left_column_x;
	private Dimension label_size;
	private Dimension field_size;
	private Font fontGenerated = new Font("Tahoma", Font.BOLD, 30 * UITheme.frameHeight / 1000);
	private Font fontLabel = new Font("Tahoma", Font.PLAIN, 25 * UITheme.frameHeight / 1000);

	private TCombo randomType;
	private TCombo separator;
	private WebPanel contentPane;
	private TButton buttonGenerate;
	private TButton buttonCopy;
	private WebSlider characterSlider;
	private TTextField generatedPassword;
	
	private TLabel entropy;
	private TLabel count = new TLabel();
	
    private TCheckBox chckbxPassphrase;
    private TCheckBox chckbxRandom;
   
	private String type = "number of characters   ";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	
	/**
	 * Initialize GeneratePanel.
	 * Lets user tweak parameters and generate randomized password.
	 * 
	 * @param parent
	 */
	public GeneratePanel(MainFrame parent) {
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
		
		label_size = new Dimension(10*getWidth() / 100, 4*getHeight()/100);
		field_size = new Dimension(60*getWidth()/100, 9*getHeight()/100);
		v_gap = 50 * getHeight() / 1000;
		left_column_x = 50*getWidth()/100 - 40*getWidth()/100/2;
		
		entropy = new TLabel();
		entropy.setFont(fontLabel);
		entropy.setSize(field_size.width, field_size.height/3);
		entropy.setLocation(50*getWidth()/100 - field_size.width/2, 25*getHeight()/100);
		contentPane.add(entropy);
		
		generatedPassword = new TTextField();
		generatedPassword.setEditable(false);
		generatedPassword.setHorizontalAlignment(SwingConstants.CENTER);
		generatedPassword.setText("Tweak options and generate !");
		generatedPassword.setFont(fontGenerated);
		generatedPassword.setSize(field_size);
		generatedPassword.setLocation(50*getWidth()/100 - field_size.width/2, 30*getHeight()/100);
		contentPane.add(generatedPassword);
		
		characterSlider = new WebSlider();
		characterSlider.addChangeListener(new ChangeListener() {
	    	public void stateChanged(ChangeEvent e) {
	    		count.setText(type + characterSlider.getValue());
	    	}
	    });
		characterSlider.setSize(40*getWidth()/100, 9*getHeight()/100);
		characterSlider.setLocation(left_column_x, generatedPassword.getLocation().y + 2 * v_gap);
		characterSlider.setValue(15);
		characterSlider.setMaximum(35);
		characterSlider.setMinimum(5);
		characterSlider.setProgressTrackBgTop(UITheme.foreground);
		characterSlider.setProgressTrackBgBottom(UITheme.foreground);
		characterSlider.setTrackBgTop(UITheme.inputBackground);
		characterSlider.setTrackBgBottom(UITheme.inputBackground);
		contentPane.add(characterSlider);

		count.setFont(fontLabel);
		count.setSize(2*label_size.width, label_size.height);
		count.setHorizontalAlignment(SwingConstants.TRAILING);
		count.setLocation(left_column_x + characterSlider.getWidth() - 2*label_size.width - 5, characterSlider.getLocation().y + 11*v_gap/10);
		contentPane.add(count);
		
		chckbxRandom = new TCheckBox("Random");
		chckbxRandom.setSelected(true);
		chckbxRandom.setFont(fontLabel);
		chckbxRandom.setForeground(UITheme.foreground);
		chckbxRandom.setLocation(left_column_x, characterSlider.getLocation().y + characterSlider.getHeight() + v_gap);
		chckbxRandom.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateGroup();
			}
		});
		chckbxRandom.setSize(label_size);
		contentPane.add(chckbxRandom);
		
		randomType = new TCombo();
		randomType.setModel(new DefaultComboBoxModel(CharacterTypes.values()));
		randomType.setSize(2 * label_size.width , label_size.height);
		randomType.setLocation(left_column_x + chckbxRandom.getWidth() + v_gap, chckbxRandom.getLocation().y);
	    contentPane.add(randomType);
		
		chckbxPassphrase = new TCheckBox("Passphrase");
		chckbxPassphrase.setFont(fontLabel);
		chckbxPassphrase.setForeground(UITheme.foreground);
		chckbxPassphrase.setSize(label_size);
		chckbxPassphrase.setLocation(left_column_x, chckbxRandom.getLocation().y + v_gap);
		chckbxPassphrase.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateGroup();
			}
		});
		contentPane.add(chckbxPassphrase);
		
		separator = new TCombo();
		separator.setModel(new DefaultComboBoxModel(new String[] {"-", "+", "=", "@", "&", "#", "/"}));
		separator.setSize(label_size);
		separator.setLocation(left_column_x + chckbxPassphrase.getWidth() + v_gap, chckbxPassphrase.getLocation().y);
		contentPane.add(separator);
		
		UnselectableButtonGroup.group(chckbxPassphrase, chckbxRandom);
	    
	    buttonGenerate = new TButton("generate");
	    buttonGenerate.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		generateAction();
	    	}
	    });
	    buttonGenerate.setSize(field_size.width/2, field_size.height);
	    buttonGenerate.setLocation(getWidth()/2 - buttonGenerate.getWidth()/2, separator.getLocation().y + 3*v_gap);
	    contentPane.add(buttonGenerate);
	    
	    int spaceLeft = ( generatedPassword.getLocation().x + generatedPassword.getWidth() ) 
	    		- (buttonGenerate.getLocation().x + buttonGenerate.getWidth()) - v_gap;
	    
	    buttonCopy = new TButton("copy");
	    buttonCopy.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
						new StringSelection(generatedPassword.getText().toString())
						, null);
	    	}
	    });
	    buttonCopy.setSize(spaceLeft, field_size.height);
	    buttonCopy.setLocation(buttonGenerate.getLocation().x + buttonGenerate.getWidth() + v_gap, buttonGenerate.getLocation().y);
	    contentPane.add(buttonCopy);
	    
	}
	/**
	 * Updates view based on selected option. chckbxPassphrase.isSelected()
	 * 
	 */
	private void updateGroup() {
		if(chckbxRandom.isSelected()) {
			randomType.setForeground(UITheme.foreground);
			separator.setForeground(UITheme.inputBackground);
			characterSlider.setMaximum(35);
			characterSlider.setMinimum(5);
			characterSlider.setValue(15);
			type = "number of characters   ";
			count.setText(type + characterSlider.getValue());
		}
		else if(chckbxPassphrase.isSelected()) {
			separator.setForeground(UITheme.foreground);
			randomType.setForeground(UITheme.inputBackground);
			characterSlider.setMaximum(10);
			characterSlider.setMinimum(2);
			characterSlider.setValue(3);
			type = "number of words   ";
			count.setText(type + characterSlider.getValue());
		}
	}
	
	/**
	 * Generates Randomized Password and Calculates it's Entropy based on user settings.
	 * 
	 */
	private void generateAction() {
		String generatedPasswordString = "";
		if(chckbxRandom.isSelected()) {
			generatedPasswordString = Generator.generateRandomPassword((CharacterTypes) randomType.getSelectedItem(), characterSlider.getValue());
		}
		else if(chckbxPassphrase.isSelected()) {
			generatedPasswordString = Generator.generatePassphrase((String) separator.getSelectedItem(), characterSlider.getValue());
		}
		entropy.setText("Password entropy: "+ PasswordManager.nbvcxz.estimate(generatedPasswordString).getEntropy().toString().substring(0, 6));
		generatedPassword.setText(generatedPasswordString);
	}
}
