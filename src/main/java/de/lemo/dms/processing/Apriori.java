package de.lemo.dms.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Apriori {
	
	static File TESTFILE = new File("/Users/forte/projekte/lemo/spm/30.csv");
	static List<Long> TIMINGS = new ArrayList<Long>();
	
	private Integer[] _path;
	private Apriori _left;
	private Apriori _right;
	
	private Apriori(Integer[] path, Apriori left, Apriori right) {
		_path = path;
		_left = left;
		_right = right;
	}


	
	private static void printStatistics(int s, int support, long t, List<int[]> userpaths, List<List<int[]>> frequentpaths) {
		System.out.println(userpaths.size() + " user paths");
		System.out.println("support " + s + "%, " + support + " users");
		System.out.println("time for apriori algorithm: " + t + "ms");
		int k = 1;
		for (Long x : TIMINGS) {
			System.out.println(frequentpaths.get(k-1).size() + " frequent paths of length " + k + ": " + x.longValue() + "ms");
			k++;
		}
	}
	
	private static void printUserPaths(List<int[]> userpaths) {
			System.out.println(userpaths.size() + " user paths");
			for (int[] up : userpaths) {
				for (int k : up) {
					System.out.print("  " + k);
				}
				System.out.println();
			}
	}
	
	private static void printFrequentPaths(List<List<int[]>> frequentpaths) {
		int i = 1;
		for (List<int[]> fps : frequentpaths) {
			System.out.println(fps.size() + " frequent paths of length " + i++);
			for (int[] fp : fps) {
				for (int k : fp) {
					System.out.print("  " + k);
				}
				System.out.println();
			}
		}
	}
	
	/**
	 * 
	 * @param userpaths: List of users paths
	 * @param support number of required users
	 * @return lists of lists of frequent paths, starting with length 1, or null
	 */
	public static List<List<Integer[]>> apriori(List<Integer[]> userpaths, int support, int maxId) {
		long t1, t2;
		List<Apriori> as = initApriori(userpaths, support, maxId);
		if (as == null) return new ArrayList<List<Integer[]>>();
		List<List<Integer[]>> frequentpaths = new ArrayList<List<Integer[]>>();
		int k = 1;
		while (!as.isEmpty()) {
//			System.out.println(as.size() + " paths of length " + k);
			t1 = System.currentTimeMillis();
			List<Integer[]> fps = new ArrayList<Integer[]>();
			for (Apriori a : as) {
				fps.add(a._path);
			}
			frequentpaths.add(fps);
			as = newpaths(as, k++, userpaths, support);
			t2 = System.currentTimeMillis();
			if (!as.isEmpty()) TIMINGS.add(new Long(t2-t1));
		}
		return frequentpaths;
	}
	
	/**
	 * k --> k+1
	 * generate candidates and check for support
	 * @param as
	 * @param k length of paths in as
	 * @return
	 */
	private static List<Apriori> newpaths(List<Apriori> as, int k, List<Integer[]> userpaths, int support) {
		List<Apriori> newas = new ArrayList<Apriori>();
		for (Apriori a1 : as) {
			for (Apriori a2 : as) {
				if (a1._right == a2._left) {
					Integer[] p = new Integer[k+1];
					for (int i=0; i<k; i++)	{
						p[i] = a1._path[i];
					}
					p[k] = a2._path[k-1];
					if (supported(p, userpaths, support)) {
						newas.add(new Apriori(p, a1, a2));
					}
				}
			}
		}
		return newas;
	}
	
	private static boolean supported(Integer[] path, List<Integer[]> userpaths, Integer support) {
		int k = 0;
		for (Integer[] p : userpaths) {
			if (contained(path, p)) k++;
			if (k == support) return true;
		}
		return false;
	}
		
	private static boolean contained(Integer[] p1, Integer[] p2) {
		int i1 = p1.length-1;
		int i2 = p2.length-1;
		if (i1 > i2) return false;
		while (i1 >= 0) {
			while (p1[i1] != p2[i2]) {
				i2--;
				if (i1 > i2) return false;
			}
			i1--; i2--;
		}
		return true;
		
//		int i1 = 0;
//		int i2 = 0;
//		int n1 = p1.length;
//		int n2 = p2.length;
//		while (n1 <= n2) {
//			if (p1[i1] == p2[i2]) {
//				i1++; i2++; n1--; n2--;
//				if (n1 == 0) return true;
//			} else {
//				i2++; n2--;
//			}
//		}
//		return false;
		
//		int k1 = 0;
//		int k2 = 0;
//		for (; k1<p1.length; k1++) {
//			if (k2 >= p2.length) return false;
//			while (p1[k1] != p2[k2]) {
//				k2++;
//				if (k2 >= p2.length) return false;
//			}
//			k2++;
//		}
//		return true;
	}
	
	/**
	 * @param userpaths
	 * @param support
	 * @return paths of length 1, or null
	 */
	private static List<Apriori> initApriori(List<Integer[]> userpaths, int support, int max) {
		long t1, t2;
		t1 = System.currentTimeMillis();
		List<boolean[]> frequencies = new ArrayList<boolean[]>();	
		for (Integer[] p : userpaths) {
			boolean[] f = new boolean[max+1];
			frequencies.add(f);
			for (int k : p) {
				f[k] = true;
			}
		}
		List<Apriori> fps = new ArrayList<Apriori>();
		for (int k=0; k<=max; k++) {
			int sum = 0;
			for (boolean[] f : frequencies) {
				if (f[k]) sum++;
			}
			if (sum >= support) {
				Integer[] node = new Integer[1];
				node[0] = k;
				fps.add(new Apriori(node, null, null));
			}
		}
		if (fps.isEmpty()) return null;
		t2 = System.currentTimeMillis();
		TIMINGS.add(new Long(t2-t1));
		return fps;
	}
	
}
