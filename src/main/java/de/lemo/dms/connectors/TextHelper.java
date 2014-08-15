/**
 * File ./src/main/java/de/lemo/dms/connectors/TextHelper.java
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

package de.lemo.dms.connectors;


public class TextHelper {
	
	public static String replaceString(final String text)
	{
		String s;
		if(text != null)
		{
			
			s = text.replaceAll("[A-ZÜÄÖ]", "X");
			s = s.replaceAll("[a-züäö]", "x");
			s = s.replaceAll("[0-9]", "0");
		}
		else
			s = "empty";
		
		return s;
	}

}
