package gishi.util;

import javax.swing.ImageIcon;

public class Icons {

	/**
	 * Returns scaled ImageIcon instance of a given image
	 * 
	 * @param String path to file
	 * @param Integer size in pixels
	 * @return ImageIcon
	 */
	public static ImageIcon load(String path, int size) {
		return new ImageIcon(new ImageIcon(Icons.class.getResource(path)).getImage().getScaledInstance((int) (size * 1),
				(int) (size * 1), java.awt.Image.SCALE_SMOOTH));
	}
	
	/**
	 * Returns scaled ImageIcon instance of a given image
	 * 
	 * @param path to file [String]
	 * @param size in pixels [Integer]
	 * @param scale [Double]
	 * @return ImageIcon
	 */
	public static ImageIcon load(String path, int size, double scale) {
		return new ImageIcon(new ImageIcon(Icons.class.getResource(path)).getImage().getScaledInstance((int) (size * scale),
				(int) (size * scale), java.awt.Image.SCALE_SMOOTH));
	}
	
}
