package de.lemo.dms.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FrequentPath {

	private static File TESTFILE = new File(
			"/Users/forte/eclipse/SPM/data/bwl.csv");
	private static List<Long> TIMESTAMPS = new ArrayList<Long>();

	private int[] _p; // path as array of int values
	private FrequentPath _left;
	private FrequentPath _right;

	private FrequentPath(int[] p, FrequentPath left, FrequentPath right) {
		_p = p;
		_left = left;
		_right = right;
	}

	/**
	 * compute frequent paths using the apriori algorithm
	 * 
	 * @param userPaths
	 *            list of users paths
	 * @param support
	 * @return list of lists of frequent paths, starting with length 1
	 */
	public static List<List<List<Integer>>> apriori(
			List<List<Integer>> userPaths, double support) {
		TIMESTAMPS.add(new Long(System.currentTimeMillis()));
		// number of required user paths
		int minpaths = (int) Math.ceil(support * userPaths.size());
		// list of FrequentPath objects of length k
		List<FrequentPath> fps;
		// alternative representation of user paths as int[]
		List<int[]> ups;
		List<List<List<Integer>>> results = new ArrayList<List<List<Integer>>>();
		// initialize
		ups = aprioriInitialize(userPaths);
		TIMESTAMPS.add(new Long(System.currentTimeMillis()));
		// frequent paths of length 1
		fps = aprioriLength1(userPaths, minpaths);
		// iteration: compute paths of length k+1 from paths of length k
		while (aprioriNewPaths(fps, results)) {
			TIMESTAMPS.add(new Long(System.currentTimeMillis()));
			fps = aprioriLengthK(fps, ups, minpaths);
		}
		TIMESTAMPS.add(new Long(System.currentTimeMillis()));
		return results;
	}

	/**
	 * transform path representation from List<Integer> to int[]
	 */
	private static List<int[]> aprioriInitialize(List<List<Integer>> userPaths) {
		List<int[]> ups = new ArrayList<int[]>();
		for (List<Integer> path : userPaths) {
			int k = path.size();
			int[] p = new int[k];
			int i = 0;
			for (Integer x : path) {
				p[i] = x.intValue();
				i++;
			}
			ups.add(p);
		}
		return ups;
	}

	/**
	 * create frequent paths of length 1 first compute set of values for
	 * learning objects then, create a frequent path for each value, if support
	 * is sufficient
	 */
	private static List<FrequentPath> aprioriLength1(
			List<List<Integer>> userPaths, int minpaths) {
		List<FrequentPath> fps = new ArrayList<FrequentPath>();
		Set<Integer> values = new HashSet<Integer>();
		for (List<Integer> path : userPaths) {
			values.addAll(path);
		}
		for (Integer x : values) {
			if (isSupported(x, userPaths, minpaths)) {
				int[] p = new int[1];
				p[0] = x.intValue();
				fps.add(new FrequentPath(p, null, null));
			}
		}
		return fps;
	}

	private static boolean isSupported(Integer x,
			List<List<Integer>> userPaths, int minpaths) {
		int npaths = 0;
		for (List<Integer> path : userPaths) {
			if (path.contains(x)) {
				npaths++;
				if (npaths == minpaths) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * given frequent paths of length k, create frequent paths of length k+1 if
	 * the rightmost k-1 values of a frequent path fp1 (left) coincide with the
	 * leftmost k-1 values of fp2 (right), then an overlay of fp1 with fp2 is a
	 * new candidate for a frequent path of length k+1 this check can be done by
	 * comparing object references (left._right == right._left) then, to
	 * determine if the new path is supported by user path upi, it is necessary
	 * that left._p is supported by upi AND right._p is supported by upi if this
	 * condition is true, it has to be tested if the new path is contained in
	 * upi this reduces the number of tests dramatically
	 */
	private static List<FrequentPath> aprioriLengthK(List<FrequentPath> fps,
			List<int[]> ups, int minpaths) {
		List<FrequentPath> next = new ArrayList<FrequentPath>();
		for (FrequentPath left : fps) {
			int k = left._p.length;
			for (FrequentPath right : fps) {
				if (left._right == right._left) {
					int[] p = new int[k + 1];
					for (int i = 0; i < k; i++) {
						p[i] = left._p[i];
					}
					p[k] = right._p[k - 1];
					if (isSupported(p, ups, minpaths)) {
						next.add(new FrequentPath(p, left, right));
					}
				}
			}
		}
		return next;
	}

	private static boolean isSupported(int[] p1, List<int[]> ups, int minpaths) {
		int npaths = 0;
		for (int[] p2 : ups) {
			int i1 = p1.length - 1;
			int i2 = p2.length - 1;
			while (i2 >= i1) {
				if (i1 < 0) {
					npaths++;
					if (npaths == minpaths) {
						return true;
					}
					break;
				}
				if (p1[i1] == p2[i2]) {
					i1--;
					i2--;
				} else {
					i2--;
				}
			}
		}
		return false;
	}

//	private static boolean isSupported(int[] p1, List<int[]> ups, int minpaths) {
//		int npaths = 0;
//		for (int[] p2 : ups) {
//			int i1 = p1.length-1;
//			int i2 = p2.length-1;
//			boolean alive = i1 <= i2;
//			while (alive && (i1 >= 0)) {
//				if (p1[i1] != p2[i2]) {
//					i2--;
//					if (i1 > i2) alive = false;
//				} else {
//					i1--; i2--;
//				}
//			}
//			if (alive) npaths++;
//			if (npaths == minpaths) return true;
//		}
//		return false;
//	}
		
	/**
	 * transforms the representation of a FrequentPath object to a List<Integer>
	 */
	private static boolean aprioriNewPaths(List<FrequentPath> fps,
			List<List<List<Integer>>> results) {
		if (fps.isEmpty())
			return false;
		List<List<Integer>> paths = new ArrayList<List<Integer>>();
		for (FrequentPath fp : fps) {
			int k = fp._p.length;
			List<Integer> path = new ArrayList<Integer>();
			for (int i = 0; i < k; i++) {
				path.add(new Integer(fp._p[i]));
			}
			paths.add(path);
		}
		results.add(paths);
		return true;
	}

	/**
	 * TESTING
	 * 
	 * @param args
	 *            support value (in %)
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			throw new Exception(
					"Usage: java spm.FrequentPathsBooleanArray <support>");
		}
		double support = Double.parseDouble(args[0]) / 100;
		List<List<Integer>> userPaths = readUserPaths();
		List<List<List<Integer>>> frequentPaths = apriori(userPaths, support);
		printStatistics(support, userPaths, frequentPaths);
		// printPaths(userPaths, userPaths.size() + " user paths");
		// int k = 1;
		// for (List<List<Integer>> paths : frequentPaths) {
		// printPaths(paths, paths.size() + " frequent paths of length " + k);
		// k++;
		// }
	}

	private static List<List<Integer>> readUserPaths() throws Exception {
		List<List<Integer>> userPaths = new ArrayList<List<Integer>>();
		BufferedReader br = new BufferedReader(new FileReader(TESTFILE));
		String str;
		String[] values;
		str = br.readLine();
		while (str != null) {
			values = str.split(",");
			List<Integer> path = new ArrayList<Integer>();
			for (String x : values) {
				path.add(new Integer(Integer.parseInt(x)));
			}
			userPaths.add(path);
			str = br.readLine();
		}
		br.close();
		return userPaths;
	}

	private static void printStatistics(double support,
			List<List<Integer>> userPaths,
			List<List<List<Integer>>> frequentPaths) {
		System.out.println(userPaths.size() + " user paths");
		System.out.println("support " + (support * 100) + " %");
		int i = 0;
		Long t1, t2;
		long t, total = 0;
		t1 = TIMESTAMPS.get(i++);
		t2 = TIMESTAMPS.get(i++);
		t = t2.longValue() - t1.longValue();
		total += t;
		t1 = t2;
		System.out.println("time for initialization: " + t + " ms");
		int k = 1;
		for (List<List<Integer>> paths : frequentPaths) {
			t2 = TIMESTAMPS.get(i++);
			t = t2.longValue() - t1.longValue();
			total += t;
			t1 = t2;
			System.out.println("time for " + paths.size()
					+ " frequent paths of length " + k + ": " + t + " ms");
			k++;
		}
		System.out.println("total time: " + total + " ms");
	}

	private static void printPaths(List<List<Integer>> userPaths, String str) {
		System.out.println(str);
		for (List<Integer> path : userPaths) {
			for (Integer x : path) {
				System.out.print(" " + x.intValue());
			}
			System.out.println();
		}
	}

	private static void printPath(int[] p) {
		int n = p.length;
		for (int i = 0; i < n - 1; i++) {
			System.out.print(p[i] + ",");
		}
		System.out.println(p[n - 1]);
	}

	private static void printBitSet(BitSet bs, int n) {
		for (int i = 0; i < n; i++) {
			if (bs.get(i)) {
				System.out.print("1");
			} else {
				System.out.print("0");
			}
		}
		System.out.println(" " + bs.cardinality());
	}

}

