import java.util.*;
import java.io.*;
public class teamwork
{
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("teamwork.in")); //new InputStreamReader(System.in));//
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("teamwork.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        ArrayList<Integer> cows = new ArrayList<Integer>();
        cows.add(0);
        for (int i = 1; i < N + 1; i++)
            cows.add(Integer.parseInt(br.readLine()));

        ArrayList<Integer> memo = new ArrayList<Integer>(); // 1-index for ease
        for (int i = 0; i < N + 1; i++) // fill some extra 0's, so dp doesn't mess up
            memo.add(0);

        System.out.println(cows.subList(N - K + 1, N + 1).get(K - 1));
        int endMax = 0; // the last K cows (0-K, so that explains the +1)
        for (int i = 1; i <= K; i++) // first K base cases filled out to avoid annoying edge cases in dp formula
        {
            endMax = Math.max(endMax, cows.get(N-i+1)); // the endMax KEEPS GETTING BIGGER-ish!!!!
            memo.set(N-i+1, i * endMax);
        }
        System.out.println(memo.subList(N-K+1, N+1));

        for (int i = N-K; i >= 1; i--) // memo(i) gives the best value of a team starting from i to the end
        {
            dp(i, K, memo, cows);
        }
        pw.println(memo.get(1));
        pw.close();
        br.close();
    }

    public static void dp(int n, int K, ArrayList<Integer> memo, ArrayList<Integer> cows)
    {
        int N = cows.size() - 1;
        int max = 0;
        /*for (int i = 1; i <= K; i++) // we can have teams of size 1 through K, i-1 represents # of extra players in addition to first one
        {
            int maxMember = 0;
            if (n + i <= N+1)
                maxMember = Math.max(maxMember, Collections.max(cows.subList(n, n + i))); //cows.get(n+i-1));//
            int value = 0;
            if (n + i <= N)
            {
                value = memo.get(n + i);
            }
            max = Math.max(max, i * maxMember + value);
        }
        memo.set(n, max);
        //*/
        int maxMember = cows.get(n); // current max
        for (int i = 1; i <= K; i++) // try all sizes
        {
            // essentially, we form a team from i people, then use the info about what the best team for the rest would be, and that team begins i spots after n
            max = Math.max(max, i * maxMember + memo.get(n+i)); // works out, since initially we ask memo[N-K+1], which is the earliest thing we have an answer stored for
            maxMember = Math.max(maxMember, cows.get(n+i)); // after this test, we steal a player from the memo's team to add to our team, and see the score again
        }
        memo.set(n, max); // */
       // memo.set(n, max); // we have determined from bottom up what the best value from that n to the end is
    }
}

/*
 int maxMember = 0;
            if (n + i <= N+1)
                maxMember = Collections.max(cows.subList(n, n + i));
            int value = 0;
            if (n + i <= N)
            {
                value = memo.get(n + i);
            }
            max = Math.max(max, i * maxMember + value);
 */