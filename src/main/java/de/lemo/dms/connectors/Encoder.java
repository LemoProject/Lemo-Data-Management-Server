/**
 * File ./src/main/java/de/lemo/dms/connectors/Encoder.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/Encoder.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 operation for anonymity
 * 
 * @author Sebastian Schwarzrock
 * @author Leonard Kappe
 */
public final class Encoder {
	
	private final static String salt = "5f4$LtW9,M-§278GSp17";

	private Encoder() {

	}

	private static MessageDigest md;
	static {
		try {
			md = MessageDigest.getInstance("SHA");
			md.update(salt.getBytes());
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
		Encoder.md.update((in + salt).getBytes());
		String encrypted = (new BigInteger (md.digest())).toString(16);
	
		return encrypted;
	}
}
