import java.util.*;
import java.io.*;
public class shortcut_bom {
	public static long[] timesaves;

	public static void main(String[] args) throws IOException{
		BufferedReader f = new BufferedReader(new FileReader("shortcut.in"));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shortcut.out")));
	    StringTokenizer st = new StringTokenizer(f.readLine());
	    int n = Integer.parseInt(st.nextToken());
	    int m = Integer.parseInt(st.nextToken());
	    int t = Integer.parseInt(st.nextToken());
	    LinkedList<Edge>[] edges = new LinkedList[n+1];
	    timesaves = new long[n+1];
	    int[] dists = new int[n+1];
	    boolean[] visited = new boolean[n+1];
	    int[] cows = new int[n+1];
	    int[] prev = new int[n+1];
	    for(int i = 1; i <= n; i++) {
	    	edges[i] = new LinkedList<Edge>();
	    	dists[i] = Integer.MAX_VALUE;
	    	prev[i] = Integer.MAX_VALUE;
	    	visited[i] = false;
	    }
	    st = new StringTokenizer(f.readLine());
	    for(int i = 1; i <= n; i++) {
	    	cows[i] = Integer.parseInt(st.nextToken());
	    }
	    for(int i = 0; i < m; i++) {
	    	st = new StringTokenizer(f.readLine());
	    	int a = Integer.parseInt(st.nextToken());
	    	int b = Integer.parseInt(st.nextToken());
	    	int c = Integer.parseInt(st.nextToken());
	    	edges[a].add(new Edge(c, b));
	    	edges[b].add(new Edge(c, a));
	    }
	    PriorityQueue<State> pq = new PriorityQueue<State>();
	    dists[1] = 0;
	    pq.add(new State(1, 0));
	    while(!pq.isEmpty()) {
	    	State state = pq.poll();
	    	if(visited[state.id]) continue;
	    	visited[state.id] = true;
	    	for(Edge e: edges[state.id]) {
	    		int newdist = state.dist + e.dist;
	    		if(newdist < dists[e.dest] || (newdist == dists[e.dest] && state.id < prev[e.dest])) {
	    			dists[e.dest] = newdist;
	    			prev[e.dest] = state.id;
	    			pq.add(new State(e.dest, newdist));
	    		}
	    	}
	    }
//        System.out.println(Arrays.toString(dists));
	    LinkedList<Integer>[] newEdges = new LinkedList[n+1];

	    for(int i = 1; i <= n; i++) {
	    	newEdges[i] = new LinkedList();
	    }
	    
	    for(int i = 2; i <= n; i++) {
	    	newEdges[prev[i]].add(i);
	    }
	    subtreeCows = new int[n + 1];
        dfs(newEdges, dists, cows, t, 1);
//        System.out.println(Arrays.toString(subtreeCows));
	    long maxt = 0;
	    for(long l: timesaves) {
	    	if(l > maxt) maxt = l;
 	    }
	    out.println(maxt);
	    out.close();
	    
	}
	static int[] subtreeCows;
	public static int dfs(LinkedList<Integer>[] edges, int[] dists, int[] cows, int t, int i) {
		int currCows = cows[i];
		for(int e: edges[i]) {
			currCows += dfs(edges, dists, cows, t, e);
		}
		subtreeCows[i] = currCows;
		timesaves[i] = Math.max(((long)(dists[i] - t)*(long)currCows), 0L);
		return currCows;
	}

	static class State implements Comparable<State>{
		public int id;
		public int dist;

		public State(int i, int d) {
			id = i;
			dist = d;
		}

		public int compareTo(State s) {
			if(dist != s.dist) return dist-s.dist;
			return id-s.id;
		}

	}

	static class Edge{
		public int dist;
		public int dest;

		public Edge(int t, int d) {
			dist =  t;
			dest = d;
		}
	}
}