package gishi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import com.alee.extended.statusbar.WebMemoryBar;

public class Console extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8394668422104062102L;
	private JPanel contentPane;
	private static JTextArea textField;
	public static boolean isVisible = false;
	/**
	 * Create the frame.
	 */
	public Console() {
		isVisible = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		WebMemoryBar memoryBar1 = new WebMemoryBar();
		memoryBar1.setSize(100,50);
		memoryBar1.setPreferredSize(memoryBar1.getSize());
		memoryBar1.drawBorder = false;
		memoryBar1.setDrawBorder(false);
		memoryBar1.setForeground(Color.PINK);
		memoryBar1.setDrawShade(false);
		memoryBar1.setFocusable(false);
		memoryBar1.setFillBackground(false);
		memoryBar1.fillBackground = false;
		memoryBar1.setBorder(null);
		memoryBar1.setBackground(Color.BLACK);
		memoryBar1.setAllocatedDisabledBorderColor(Color.BLACK);
		memoryBar1.setAllocatedBorderColor(Color.BLACK);
		memoryBar1.setShowTooltip(false);
		memoryBar1.setShowMaximumMemory(false);
		contentPane.add(memoryBar1, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textField = new JTextArea();
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		textField.setLineWrap(true);
		scrollPane.setViewportView(textField);
		textField.setColumns(7);
		DefaultCaret caret = (DefaultCaret) textField.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public static void logConsole(String input) {
		if(isVisible) {
			textField.setText(textField.getText() + "\n"+ input);
		}
	}
}