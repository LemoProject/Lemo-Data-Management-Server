/**
 * File ./main/java/de/lemo/dms/connectors/Encoder.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.security.MessageDigest;

/**
 * 
 * MD5 operation for anonymity
 * @author Sebastian Schwarzrock
 *
 */
public class Encoder {

	private static MessageDigest md = null;

	/**
	 * Returns the MD5 value of the input-string.
	 * 
	 * @param in
	 *            String to encode
	 * @return MD5-encoded string
	 */
	public static String createMD5(String in)
	{
		try {
			Encoder.md = MessageDigest.getInstance("MD5");
			Encoder.md.update(in.getBytes());

			final byte byteData[] = Encoder.md.digest();

			final StringBuffer sb = new StringBuffer();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			in = sb.substring(0);
		} catch (final Exception e)
		{
		}
		return in;
	}
}
