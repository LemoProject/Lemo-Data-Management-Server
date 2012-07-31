package de.lemo.dms.test;

public class Test {

	
	public static void main(String[] args)
	{
		ContentGenerator conGen = new ContentGenerator();
		
		conGen.generateMiningDB(5, 2, 2, 0L);
	}
}
