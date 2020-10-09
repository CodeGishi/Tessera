package gishi.util;

import java.util.Timer;
import java.util.TimerTask;

import com.alee.laf.label.WebLabel;

public class TimedMessage {

	private Timer timer;
	private boolean enabled = true;
	private int time = 1;
	
	/**
	 * Display a timed message on given Label.
	 * 
	 * @param label Label Component 
	 * @param msg Message to display
	 */
	public TimedMessage(final WebLabel label, final String msg) {
		showMessage(msg, label);
	}
	
	/**
	 * Display a timed message on given Label.
	 * 
	 * @param label Label Component 
	 * @param msg Message to display
	 * @param seconds Duration of display
	 */
	public TimedMessage(final WebLabel label, final String msg, final int seconds) {
		this.time = seconds;
		showMessage(msg, label);
	}
	
	private void showMessage(final String msg, final WebLabel label) {
		label.setText(msg);
		if (enabled) {
			startTiming(label);
		}
	}
	
	private void startTiming(final WebLabel label) {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			int counter = 0;
			public void run() {
				if (counter == time) {
					label.setText("");
					enabled = false;
					pause();
				}
				counter++;
			}
		}, 0, 1000);
	}
	
	private void pause() {
		timer.cancel();
		enabled = true;
	}
}
