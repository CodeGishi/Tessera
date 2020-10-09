package gishi.data;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("serial")
public class PasswordList extends ArrayList<Password>{

	public static boolean initialized = false;
 
	public PasswordList reverse() {
		Collections.reverse(this);
		return (this);
	}
	
}
