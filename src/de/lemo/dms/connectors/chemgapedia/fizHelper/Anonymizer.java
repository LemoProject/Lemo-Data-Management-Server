package de.lemo.dms.connectors.chemgapedia.fizHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Anonymizer {
	
	
	public void copy(String inFile, String outFile)
	{
		
    	try 
    	{
    		BufferedReader input =  new BufferedReader(new FileReader(inFile));
    		FileWriter outFiles = new FileWriter(outFile);
			PrintWriter out = new PrintWriter(outFiles);
			boolean isInUserBlock = false;
			boolean isInLogBlock = false;
			boolean isInForumPost = false;
			boolean isInCMess = false;
			boolean isInDatCon = false;
			boolean isInLessAn = false;
			
    		String line = null;
    		while (( line = input.readLine()) != null)
    		{
    			String s ="";
    			if(line.startsWith("INSERT INTO `user`"))
    				isInUserBlock = true;
    			if(line.startsWith("INSERT INTO `log`"))
    				isInLogBlock = true;
    			if(line.startsWith("INSERT INTO `forum_posts`"))
    				isInForumPost = true;
    			if(line.startsWith("INSERT INTO `chat_messages`"))
    				isInCMess = true;
    			if(line.startsWith("INSERT INTO `data_content`"))
    				isInDatCon = true;
    			if(line.startsWith("INSERT INTO `lesson_answers`"))
    				isInLessAn = true;
    			
    			
    			if(isInUserBlock && line.startsWith("("))
    			{
    				
    				String[] nLine = line.split(",");
    				nLine[8] = "mnethostid";
    				nLine[6] = "username";
    				nLine[7] = "password";
    				nLine[9] = "name";
    				nLine[10] = "surname";
    				nLine[11] = "email";
    				nLine[13] = "icq";
    				nLine[14] = "skype";
    				nLine[15] = "yahoo";
    				nLine[16] = "aim";
    				nLine[17] = "msn";
    				nLine[18] = "phone";
    				nLine[19] = "phone";
    				nLine[22] = "address";
    				nLine[32] = "lastip";
    				nLine[35] = "url";
    				for(int i = 0; i < nLine.length; i++)
    				{
    					s += nLine[i];
    					if(i != nLine.length-1)
    						s += ",";
    				}
    			}else if(isInLogBlock && line.startsWith("("))
    			{
    				
    				String[] nLine = line.split(",");
    				nLine[3] = "ip";
    				for(int i = 0; i < nLine.length; i++)
    				{
    					s += nLine[i];
    					if(i != nLine.length-1)
    						s += ",";
    				}
    				
    			}else if(isInCMess && line.startsWith("("))
    			{
    				
    				String[] nLine = line.split(",");
    				nLine[5] = "message";
    				for(int i = 0; i < nLine.length; i++)
    				{
    					s += nLine[i];
    					if(i != nLine.length-1)
    						s += ",";
    				}
    				
    			}else if(isInDatCon && line.startsWith("("))
    			{
    				
    				String[] nLine = line.split(",");
    				nLine[3] = "content";
    				for(int i = 0; i < nLine.length; i++)
    				{
    					s += nLine[i];
    					if(i != nLine.length-1)
    						s += ",";
    				}
    				
    			}else if(isInLessAn && line.startsWith("("))
    			{
    				String nLine[] = line.split(",");
    				nLine[9] = "answer";
    				for(int i = 0; i < nLine.length; i++)
    				{
    					s += nLine[i];
    					if(i != nLine.length-1)
    						s += ",";
    				}
    			}else if(isInForumPost && line.startsWith("("))
    			{
    				String nLine[] = line.split(",");
    				nLine[8] = "message";
    				for(int i = 0; i < nLine.length; i++)
    				{
    					s += nLine[i];
    					if(i != nLine.length-1)
    						s += ",";
    				}
    			}
    			else
    				s = line;
    			if(line.contains(");") && (isInUserBlock || isInLogBlock)) 	
    			{
    				isInUserBlock = false;
    				isInLogBlock = false;
    				isInDatCon = false;
    				isInLessAn = false;
    				isInCMess = false;
    				isInForumPost = false;
    			}
    			out.println(s);
    		}	    	
			out.close();
    	} 
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	public static void main(String[] args)
	{
		if(args.length == 2)
		{
			Anonymizer an = new Anonymizer();
			an.copy(args[0], args[1]);
		}
	}

}
