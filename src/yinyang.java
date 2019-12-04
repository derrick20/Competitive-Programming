import java.util.*;

import java.io.*;
import java.awt.Point;
import java.math.BigInteger;

public class yinyang {
	
	static int N;
	static int numNodes;
	static Node[] nodes;

	public static void main(String[] args) throws Exception {
		FastIO sc = new FastIO("yinyang.in");
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("yinyang.out")));
		
		N = numNodes = sc.nextInt();
		
		nodes = new Node[N];
		for(int i = 0; i < N; i++) nodes[i] = new Node(i);
		for(int i = 0; i < N-1; i++) {
			int a = sc.nextInt()-1;
			int b = sc.nextInt()-1;
			int t = sc.nextInt();
			if(t == 0) t = -1;
			
			nodes[a].adjacent.add(nodes[b]);
			nodes[a].edgeWeight.add(t);
			
			nodes[b].adjacent.add(nodes[a]);
			nodes[b].edgeWeight.add(t);
		}
		
		centroidD(nodes[0]);
		
		pw.println(totalPaths);
		pw.close();
	}
	
	static long totalPaths = 0;
	
	static class Node {
		
		Node centroidParent;
		int centroidDepth;
		
		int size;
		int index;
		
		boolean madeCentroid;
		
		ArrayList<Node> adjacent;
		ArrayList<Integer> edgeWeight;
		
		public Node(int index) {
			this.index = index;
			this.adjacent = new ArrayList<Node>();
			edgeWeight = new ArrayList<Integer>();
		}
		
		public void process() {
			//System.out.println(index);
			HashMap<Integer, Integer> totalWithPrefix = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> totalWithoutPrefix = new HashMap<Integer, Integer>();
			
			//totalWithoutPrefix.put(0, 1);
			
			for(int i = 0; i < adjacent.size(); i++) {
				Node n = adjacent.get(i);
				//System.out.println("\t" + n.index);
				if(!n.madeCentroid) {
					HashMap<Integer, Integer> stepWithPrefix = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> stepWithoutPrefix = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> prevWeights = new HashMap<Integer, Integer>();
					//prevWeights.put(0, 1);
					
					dfs_process(n.index, index, edgeWeight.get(i), stepWithPrefix, stepWithoutPrefix, totalWithPrefix, totalWithoutPrefix, prevWeights);
					
					for(int key : stepWithPrefix.keySet()) {
						if(!totalWithPrefix.containsKey(key)) totalWithPrefix.put(key, 0);
						totalWithPrefix.put(key, totalWithPrefix.get(key)+stepWithPrefix.get(key));
					}
					for(int key : stepWithoutPrefix.keySet()) {
						if(!totalWithoutPrefix.containsKey(key)) totalWithoutPrefix.put(key, 0);
						totalWithoutPrefix.put(key, totalWithoutPrefix.get(key)+stepWithoutPrefix.get(key));
					}
					//System.out.println("\t" + total);
					//System.out.println("\t as of now: " + totalPaths);
				}
				
			}
		}
		
		public void dfs_process(int cur, int parent, int weight, HashMap<Integer, Integer> currentWithPrefix, HashMap<Integer, Integer> currentWithoutPrefix, HashMap<Integer, Integer> totalWithPrefix, HashMap<Integer, Integer> totalWithoutPrefix, HashMap<Integer, Integer> prevWeight) {
			if(prevWeight.containsKey(weight) && prevWeight.get(weight) != 0) {
				if(totalWithPrefix.containsKey(-weight)) totalPaths += totalWithPrefix.get(-weight);
				if(totalWithoutPrefix.containsKey(-weight)) totalPaths += totalWithoutPrefix.get(-weight);
				if(weight == 0) totalPaths++;
				
				if(!currentWithPrefix.containsKey(weight)) currentWithPrefix.put(weight, 0);
				currentWithPrefix.put(weight, currentWithPrefix.get(weight)+1);
			} else {
				if(totalWithPrefix.containsKey(-weight)) totalPaths += totalWithPrefix.get(-weight);
				if(weight == 0) if(totalWithoutPrefix.containsKey(-weight)) totalPaths += totalWithoutPrefix.get(-weight);
				
				if(!currentWithoutPrefix.containsKey(weight)) currentWithoutPrefix.put(weight, 0);
				currentWithoutPrefix.put(weight, currentWithoutPrefix.get(weight)+1);
			}
			
			if(!prevWeight.containsKey(weight)) prevWeight.put(weight, 0);
			prevWeight.put(weight, prevWeight.get(weight)+1);
			
			for(int i = 0; i < nodes[cur].adjacent.size(); i++) {
				Node n = nodes[cur].adjacent.get(i);
				if(!n.madeCentroid && n.index != parent) {
					dfs_process(n.index, cur, weight+nodes[cur].edgeWeight.get(i), currentWithPrefix, currentWithoutPrefix, totalWithPrefix, totalWithoutPrefix, prevWeight);
				}
			}
			
			prevWeight.put(weight, prevWeight.get(weight)-1);
		}
		
		public void dfs_size(int parent) {
			size = 1;
			
			for(Node n : adjacent) {
				if(n.index != parent && !n.madeCentroid) {
					n.dfs_size(index);
					size += n.size;
				}
			}
		}
		
		public Node getCentroid(int parent, int halfBarns) {
			Node path = null;
			
			for(Node n : adjacent) {
				if(n.index == parent || n.madeCentroid) continue;
				if(n.size > halfBarns) {
					path = n;
					break;
				}
			}
			
			if(path == null) {
				centroidDepth = depth;
				return this;
			}
			return path.getCentroid(index, halfBarns);
		}
		
		public String toString() {
			return index+"";
		}
	}
	
	static int depth = 0;
	
	static void centroidD(Node root) {
		root.dfs_size(-1);
		
		ArrayList<Node> centroids = new ArrayList<Node>(1);
		centroids.add(root.getCentroid(-1, numNodes/2));
		
		while(!centroids.isEmpty()) {
			depth++;
			ArrayList<Node> nextCentroids = new ArrayList<Node>();
			for(Node n : centroids) {
				n.madeCentroid = true;
			}

			for(Node n : centroids) {
				for(Node m : n.adjacent) {
					if(!m.madeCentroid) {
						m.dfs_size(n.index);
						Node centroid = m.getCentroid(n.index, m.size/2);
						nextCentroids.add(centroid);
						centroid.centroidParent = n;
					}
				}
				
				n.process();
			}
			
			centroids.clear();
			centroids = nextCentroids;
		}
	}
	
	static class FastIO {

		//Is your Fast I/O being bad?

		InputStream dis;
		byte[] buffer = new byte[1 << 17];
		int pointer = 0;

		public FastIO(String fileName) throws Exception {
			dis = new FileInputStream(fileName);
		}

		public FastIO(InputStream is) throws Exception {
			dis = is;
		}

		int nextInt() throws Exception {
			int ret = 0;

			byte b;
			do {
				b = nextByte();
			} while (b <= ' ');
			boolean negative = false;
			if (b == '-') {
				negative = true;
				b = nextByte();
			}
			while (b >= '0' && b <= '9') {
				ret = 10 * ret + b - '0';
				b = nextByte();
			}

			return (negative) ? -ret : ret;
		}

		long nextLong() throws Exception {
			long ret = 0;

			byte b;
			do {
				b = nextByte();
			} while (b <= ' ');
			boolean negative = false;
			if (b == '-') {
				negative = true;
				b = nextByte();
			}
			while (b >= '0' && b <= '9') {
				ret = 10 * ret + b - '0';
				b = nextByte();
			}

			return (negative) ? -ret : ret;
		}

		byte nextByte() throws Exception {
			if (pointer == buffer.length) {
				dis.read(buffer, 0, buffer.length);
				pointer = 0;
			}
			return buffer[pointer++];
		}

		String next() throws Exception {
			StringBuffer ret = new StringBuffer();

			byte b;
			do {
				b = nextByte();
			} while (b <= ' ');
			while (b > ' ') {
				ret.appendCodePoint(b);
				b = nextByte();
			}

			return ret.toString();
		}

	}
}

/*
 17
1 2 0
2 4 1
4 6 0
4 7 1
6 10 1
6 11 1
7 12 0
7 13 0
1 3 0
3 5 1
5 8 1
8 14 0
8 15 0
5 9 1
9 16 0
9 17 0
*/
