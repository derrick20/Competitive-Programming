import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

public class Race {
    
	public int getRaceLength(int k, int[][] arr) {
		//TODO
		int s1 = 0;
		ArrayDeque<Pair> q = new ArrayDeque<>();
		boolean[] visited = new boolean[k];
		q.add(new Pair(s1, 0));
		visited[s1] = true;
		Pair farthest = new Pair(s1, 0);
		while (q.size() > 0) {
			Pair top = q.pollFirst();
			if (top.second > farthest.second) {
				farthest = top;
			}
			for (int adj : arr[top.first]) {
				if (!visited[adj]) {
					visited[adj] = true;
					q.addLast(new Pair(adj, top.second + 1));
				}
			}
		}
		// found the farthest, now do bfs again

		q = new ArrayDeque<>();
		visited = new boolean[k];
		int s2 = farthest.first;
		Pair opposite = new Pair(farthest.first, 0);
		q.add(opposite);
		visited[s2] = true;
		while (q.size() > 0) {
			Pair top = q.pollFirst();
			if (top.second > opposite.second) {
				opposite = top;
			}
			for (int adj : arr[top.first]) {
				if (!visited[adj]) {
					visited[adj] = true;
					q.addLast(new Pair(adj, top.second + 1));
				}
			}
		}
		return opposite.second;
	}
	
	class Pair {
		int first;
		int second;
		
		public Pair(int x, int y) {
			first = x;
			second = y;
		}
	}
	
	//Do not modify below this line
	
    public void run() throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader("RaceIN.txt"));
        String line;
        while ((line = br.readLine()) != null) {
        	int statueCount = Integer.parseInt(line);
        	int[][] arr = new int[statueCount][];
        	for (int j = 0; j < statueCount; j++) {
        		line = br.readLine();
        		String[] numbers = line.split(" ");
        		int[] adjStatues = new int[numbers.length];
        		for (int i = 0; i < numbers.length; i++) {
        			int statue = Integer.parseInt(numbers[i]);
		            adjStatues[i] = statue;
        		}
        		arr[j] = adjStatues;
        	}
    		int num = getRaceLength(statueCount, arr);
            System.out.println(num);
        	
        }
        br.close();
    }
    
    public static void main (String[] args) throws Exception {
        new Race().run();
    }
    //Do not modify above this line
}