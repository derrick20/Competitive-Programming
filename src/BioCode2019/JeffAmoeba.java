import java.io.*;
import java.util.*;
public class JeffAmoeba {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        int n = sc.nextInt();
        int[] to = new int[n];
        int[] parent = new int[n];
        for(int i = 0; i < n; i++) {
            to[i] = sc.nextInt()-1;
            parent[to[i]]++;
        }
        int ret = n;
        LinkedList<Integer> q = new LinkedList<Integer>();
        for(int i = 0; i < n; i++) {
            if(parent[i] == 0) {
                q.add(i);
                ret--;
            }
        }
        while(!q.isEmpty()) {
            int curr = q.removeFirst();
            if(--parent[to[curr]] == 0) {
                q.add(to[curr]);
                ret--;
            }
        }
        pw.println(ret);
        pw.close();
    }
}