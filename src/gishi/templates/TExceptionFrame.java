package gishi.templates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebTextArea;

import gishi.control.SettingsManager;
import gishi.control.UITheme;

@SuppressWarnings("serial")
public class TExceptionFrame extends WebFrame {
	
	private JPanel contentPane;
	
	private WebLabel lblTitle;
	private JTextArea txtrErrorText;
	private WebButton btnOk;
	
	private String title = "";
	private Point initialClick = null;
	
	private final Color foreground 		= new Color(250,250,250);
	private final Color background 		= new Color(25,25,25);

	
	public TExceptionFrame(String exceptionText, String other) {
		init();
		
		if (SettingsManager.DEBUG) {
			lblTitle.setText("   "+exceptionText);
		}
		
			lblTitle.setText("   "+exceptionText);
		
		txtrErrorText.setText(other);
	}

	/**
	 * Initialize Exception Dialog.
	 * 
	 */
	private void init() {
		setUndecorated(true);
		setSize(40 * UITheme.frameWidth / 100, 30 * UITheme.frameHeight / 100);
		setLocation(UITheme.screenWidth / 2 - getWidth() / 2, UITheme.screenHeight / 2 - getHeight() / 2);
		
		contentPane = new JPanel();
		contentPane.setBackground(background);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		control();

		txtrErrorText = new WebTextArea();
		txtrErrorText.setSize(getWidth() - 2*UITheme.controlMenuHeight, getHeight()/3);
		txtrErrorText.setLocation(UITheme.controlMenuHeight, 2*UITheme.controlMenuHeight);
		
		txtrErrorText.setBackground(background);
		txtrErrorText.setForeground(foreground);
		txtrErrorText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		txtrErrorText.setLineWrap(true);
		txtrErrorText.setEnabled(false);
		txtrErrorText.setEditable(false);
		contentPane.add(txtrErrorText);
		
		btnOk = new WebButton("Close");
		btnOk.setSize(getWidth() / 2, getHeight() / 7);
		btnOk.setLocation(getWidth() / 2 - btnOk.getWidth()/2, getHeight() - btnOk.getHeight()*2);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnOk.setDrawShade(false);
		btnOk.setDrawFocus(false);
		btnOk.setDrawSides(true, true, true, true);
		btnOk.setFont(UITheme.boldFont);
		btnOk.setRolloverShine(true);
		btnOk.setShineColor(new Color(55, 20, 40, 155));
		btnOk.setForeground(foreground);
		btnOk.setTopBgColor(background);
		btnOk.setBottomBgColor(background);
		btnOk.setTopSelectedBgColor(background);
		btnOk.setBottomSelectedBgColor(background);
		btnOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnOk);
	}

	private void control() {
		WebPanel control = new WebPanel();
		control.setLocation(0, 0);
		control.setLayout(new BorderLayout(0, 0));
		control.setBackground(UITheme.error);
		control.setSize(getWidth(), UITheme.controlMenuHeight);
		control.setPreferredSize(new Dimension(getWidth(), UITheme.controlMenuHeight));
		getContentPane().add(control);
		
		WebPanel panelWest = new WebPanel();
		panelWest.setBackground(UITheme.transparent);
		control.add(panelWest, BorderLayout.WEST);
		
		lblTitle = new WebLabel();
		lblTitle.setHorizontalAlignment(SwingConstants.LEADING);
		lblTitle.setText(title);
		lblTitle.setBackground(UITheme.transparent);
		lblTitle.setForeground(foreground);
		panelWest.add(lblTitle);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				// get location of Window
				int thisX = getLocationOnScreen().x;
				int thisY = getLocationOnScreen().y;

				// Determine how much the mouse moved since the initial click
				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				// Move window to this position
				int X = thisX + xMoved;
				int Y = thisY + yMoved;

				setLocation(X, Y);
			}
		});
	}
	
	
	public static void showExceptionFrame(String exception) {
		try {
			TExceptionFrame frame = new TExceptionFrame(exception, "");
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showExceptionFrame(String exception, String comment) {
		try {
			TExceptionFrame frame = new TExceptionFrame(exception, comment);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showExceptionFrame(String exception, Class<?> cls) {
		try {
			TExceptionFrame frame = new TExceptionFrame(exception, cls.toString());
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
