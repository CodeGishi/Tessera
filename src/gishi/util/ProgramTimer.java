package gishi.util;

import java.util.Timer;
import java.util.TimerTask;

public class ProgramTimer {

	
	public static void runTimer() {
	    TimerTask task = new TimerTask() {
	    	int counter = 0;
	        public void run() {
	            //System.out.println("TTL: " + (10-counter));
	            if(counter == 10) {
	            	System.exit(0);
	            }
	            counter ++;
	        }
	    };
	    Timer timer = new Timer("Timer");
	     
	    double delay = 5000;
	    timer.schedule(task, 0, (long) delay);
	}
	
	public static void runTimer(int t) {
	    TimerTask task = new TimerTask() {
	    	int counter = 0;
	        public void run() {
	            //System.out.println("TTL: " + (10-counter));
	            if(counter == 10) {
	            	System.exit(0);
	            }
	            counter ++;
	        }
	    };
	    Timer timer = new Timer("Timer");
	     
	    double delay = t;
	    timer.schedule(task, 0, (long) delay);
	}
	
}
