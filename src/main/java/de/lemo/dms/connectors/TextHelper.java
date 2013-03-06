package de.lemo.dms.connectors;


public class TextHelper {
	
	public static String replaceString(String text)
	{
		String s = new String(text);
		text.replaceAll("[^a-z]", "x");
		text.replaceAll("[^A-Z]", "X");
		text.replaceAll("[^0-9]", "0");
		
		return text;
	}

}
