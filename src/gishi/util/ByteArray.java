package gishi.util;

public class ByteArray {

	/**
	 * Checks if given ByteArray is Empty.
	 * 
	 * @return FALSE if any byte is not 0
	 * 
	 */
	public static boolean isEmpty(byte[] array) {
		for (byte b : array) {
		    if (b != 0) {
		        return false;
		    }
		}
		return true;
	}
}
