package gishi.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class UITheme {
	
/*
 * 
 * SIZE OF FRAME COMPONENTS
 */
	public static final int screenWidth 			= (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int screenHeight 			= (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int frameWidth 				= 55 * screenWidth /100;
	public static final int frameHeight 			= 61 * frameWidth  /100;
	public static final int animationSize 			= 256;
	public static final int controlMenuHeight 		= 37 * frameHeight / 1000;
	public static final int sideMenuWidth 			= 70 * frameWidth  / 1000;
	public static final Dimension frameDimension 	= new Dimension(frameWidth, frameHeight);
	
/*
 * PROGRAM FONTS
 */
	public static final Font defaultFont 			= new Font("Tahoma", Font.PLAIN, 20 * frameHeight / 1000);
	public static final Font boldFont 				= new Font("Tahoma", Font.BOLD,  20 * frameHeight / 1000);
	public static final Font enlargedFont 			= new Font("Tahoma", Font.PLAIN, 25 * frameHeight / 1000);
	public static final Font boldEnlargedFont 		= new Font("Tahoma", Font.BOLD,  25 * frameHeight / 1000);

/*
 * VARIABLES DEPENDANT ON USER SETTINGS	
 */
	public static String theme 			= "dark";
	public static String themeIconPath;
	
	public static Color foreground; 			
	public static Color inputBackground;
	public static Color background 					= new Color(20, 	20, 	20			);
	public static Color background2;
	public static Color tileBackground;
	public static Color tileHighlight;
	public static Color tileBorderColor;
	public static Color tileBorderColorHighlight;
	public static Color buttonShine;
	public static Color menuBackground				= new Color(25, 	25, 	25			);
	public static Color menuButtonHighlight;
	
	public static final Color transparent			= new Color(0, 		0, 		0, 		0	);
	public static final Color white		 			= new Color(255, 	255, 	255, 	255	);
	public static final Color error 	 			= new Color(250, 	70, 	120,	120	);
	public static final Color errorMsg 	 			= new Color(250,	70,		120			);
/*
 * FUNCTION TO LOAD THEME RELATED VALUES	
 */
	public static void loadTheme() {
		theme = SettingsManager.getTheme();
		if(theme.equals("light")) {
			themeIconPath = "/icons/dark/";
			
			foreground 					= new Color(25, 	25, 	25			);
			inputBackground 			= new Color(243, 	243, 	243			);
			background 					= new Color(235, 	235, 	235			);
			background2 				= new Color(200, 	190, 	200			);

			tileBackground 				= new Color(235, 	235, 	235			);
			tileHighlight 				= new Color(00, 	200, 	120, 	20	);
			tileBorderColor 			= new Color(100, 	100, 	100			);
			tileBorderColorHighlight 	= new Color(00, 	200, 	120			);

			buttonShine					= new Color(50, 	255, 	100,	40	);
			menuBackground 				= new Color(25, 	25, 	25			);
			menuButtonHighlight 		= new Color(50, 	50, 	50 		);
		}
		else if(theme.equals("dark_blue")) {
			themeIconPath = "/icons/light/";
			
			foreground 					= new Color(235,	235,	235			);
			background 					= new Color(25, 	25, 	25			);
			background2 				= new Color(50, 	50, 	50			);
			inputBackground				= new Color(50, 	50, 	50			);

			tileBackground 				= new Color(30, 	30, 	30			);
			tileHighlight 				= new Color(30, 	70, 	150, 	75	);
			tileBorderColor 			= new Color(50, 	50, 	50			);
			tileBorderColorHighlight 	= new Color(40, 	90, 	130			);

			buttonShine					= new Color(25, 	70, 	150, 	125	);
			menuBackground 				= new Color(24, 	24, 	24			);
			menuButtonHighlight 		= new Color(40, 	60, 	93, 	255	);
			//menuButtonHighlight 		= new Color(30, 	50, 	70, 	95	);
		}
		else if(theme.equals("dark_red")) {
			themeIconPath = "/icons/light/";
			
			foreground 					= new Color(245,	245,	245			);
			background 					= new Color(20, 	20, 	20			);
			background2 				= new Color(50,	 	50, 	50			);
			inputBackground				= new Color(30,	 	30, 	30			);

			tileBackground 				= new Color(30, 	30, 	30			);
			tileHighlight 				= new Color(255, 	70, 	120, 	120	);
			tileBorderColor 			= new Color(50, 	50, 	50			);
			tileBorderColorHighlight 	= new Color(255, 	70, 	120			);

			buttonShine					= new Color(95,		30,		70, 	90	);
			menuBackground 				= new Color(25, 	25, 	25			);
			menuButtonHighlight 		= new Color(45, 	25, 	35,		255	);
			//menuButtonHighlight 		= new Color(85, 	25, 	50,		90	);
		}
		else {
			if(SettingsManager.DEBUG)System.out.println("Couldn't load such theme");
		}
	}//LOAD THEME

}//CLASS END
