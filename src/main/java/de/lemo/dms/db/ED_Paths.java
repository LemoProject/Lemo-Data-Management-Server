package de.lemo.dms.db;

import java.util.*;

public interface ED_Paths {
	
	public List<List<ED_Activity>> getAprioriPaths();
	
	public int[] getAprioriSupports();
	
	public List<List<ED_Activity>> getPrefixspanPaths();
	
	public int[] getPrefixspanSupports();
	
}
