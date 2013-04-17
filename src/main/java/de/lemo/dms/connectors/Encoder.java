/**
 * File ./main/java/de/lemo/dms/connectors/Encoder.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 operation for anonymity
 * 
 * @author Sebastian Schwarzrock
 * @author Leonard Kappe
 */
public final class Encoder {

	private Encoder() {

	}

	private static MessageDigest md;
	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// md5 is always available, it's a java platform requirement
		}
	}

	/**
	 * Returns the MD5 value of the input-string.
	 * 
	 * @param in
	 *            String to encode
	 * @return MD5-encoded string
	 */
	public static String createMD5(String in) {
		final byte byteData[] = Encoder.md.digest(in.getBytes());
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toHexString((byteData[i] & 0xff) + 0x100).substring(1));
		}
		return sb.toString();
	}
}
