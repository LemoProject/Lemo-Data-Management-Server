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
