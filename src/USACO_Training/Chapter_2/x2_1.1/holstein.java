/*
ID: d3rrick1
LANG: JAVA
PROB: holstein
*/
import java.io.*;
import java.util.*;
class holstein {
	public static int[][] feeds; // the feeds and how many vitamins each contains
	public static boolean[] visited;
	public static int unused;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));// new FileReader("holstein.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out)); //new FileWriter("holstein.out")));

		int N = Integer.parseInt(br.readLine());
		int[] needed = new int[N]; // the needed number of each vitamin type

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++)
			needed[i] = Integer.parseInt(st.nextToken());

		int F = Integer.parseInt(br.readLine());
		feeds = new int[F][N];
		visited = new boolean[F];
		for (int i = 0; i < F; i++) {
			feeds[i] = new int[N];
			visited[i] = false;
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				feeds[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		unused = 0;

	}

	public static boolean finished(int[] current_needs) {
		for (int x : current_needs) {
			if (x > 0) {
				return false;
			}
		}
		return true;
	}

	/*public static int[] bfs(int[] current_needs, int[] taken) {

		if (finished(current_needs))
			return taken;
		else {
			for (int i = unused; i < feeds.length; i++) {
				int[] copy = Arrays.copyOf(current_needs, current_needs.length);

			}
		}
	}*/
}