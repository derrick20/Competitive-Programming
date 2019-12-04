import java.util.*;
import java.io.*;
/*
For MP3 This is straight up a bogus solution:
6 1
1 1 2 2 3 3

Num: [0, 1, 3, 5, 0, 0, 0]
6 3 0
6 5 1
2

Look at that first output number it straight up would work WTFFFF
 */
public class c1 {
    public static void main(String[] args) throws Exception{
        FastScanner sc = new FastScanner(System.in);
        int n = sc.nextInt();
        int I = sc.nextInt();
        int k=8*I/n;
        long K;
        int a[]=new int[n+1];
        int num[]=new int[n+1];
        int cnt=0;
        for(int i=1;i<=n;i++){
            a[n+1-i]=sc.nextInt();
        }
        Arrays.sort(a,1,n+1);
        a[0]=-1;
        for(int i=1;i<=n;i++){
            if(a[i]!=a[i-1])
                num[++cnt]=i;
        }
        K=(int)(Math.log((double)(a[n]-a[1]))/Math.log(2.0));
        if(K<k){
            System.out.println(0);
        }else{
            System.out.println(Arrays.toString(num));
            int ans=n;
            for(int i=1;i<cnt;i++){
                int c=i+(1<<k)-1;
                if(c>cnt)
                    break;
                System.out.println(n + " " + num[c] + " " +num[i-1]);
                ans=Math.min(ans, n-num[c]+num[i-1]);
            }
            System.out.println(ans);
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}