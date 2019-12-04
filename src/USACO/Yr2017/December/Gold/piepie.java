import java.io.*;
import java.util.*;
//0 bessie
//1 elsie
//2 place
//3 bessie (0) or elsie (1)
public class piepie {
    public static int[][] output;
    public static LinkedList<int[]> stack=new LinkedList<>();
    //first n=bessie
    //second n=elsie
    public static int[][][] pies;
    public static int numPies;
        public static void main(String[] args) throws IOException {
        //BufferedReader f = new BufferedReader(new FileReader("/Users/freddietang/IdeaProjects/haybales.txt"));
        BufferedReader f = new BufferedReader(new FileReader("piepie.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("piepie.out")));
        //PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/freddietang/IdeaProjects/haybalesout.txt")));
        StringTokenizer st = new StringTokenizer(f.readLine());
        numPies=Integer.parseInt(st.nextToken());
        int limit=Integer.parseInt(st.nextToken());
        pies=new int[2][numPies][4];
        for (int i=0;i<numPies;i++){
            st = new StringTokenizer(f.readLine());
            pies[0][i]=new int[]{Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    i, 0};
        }
        for (int i=0;i<numPies;i++){
            st = new StringTokenizer(f.readLine());
            pies[1][i]=new int[]{Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    i, 1};
        }
        Arrays.sort(pies[0],(int[] o1, int[] o2)->{
            if (o1[1] < o2[1]) return -1;
            if (o1[1] > o2[1]) return 1;
            return 0;
        });
        Arrays.sort(pies[1],(int[] o1, int[] o2)->{
            if (o1[0] < o2[0]) return -1;
            if (o1[0] > o2[0]) return 1;
            return 0;
        });
        output=new int[2][numPies];
        boolean[][] visited=new boolean[2][numPies];
        int c=0;
        while(c<numPies && pies[0][c][1]==0){
            stack.add(new int[]{pies[0][c][0],0,1});
            output[0][pies[0][c][2]]=1;
            visited[0][pies[0][c][2]]=true;
            c++;
        }
        c=0;
        while(c<numPies && pies[1][c][0]==0){
            stack.add(new int[]{pies[1][c][1],1,1});
            output[1][pies[1][c][2]]=1;
            visited[1][pies[1][c][2]]=true;
            c++;
        }
        while(stack.size()>0){
            int[] curr=stack.removeFirst();
//            System.out.println(Arrays.toString(curr));
            int v=bfs(curr[0],curr[1]);
            // Curr 0 and curr 1 represent the EVALUATION of the pie based on bessie and elsie

            while(v>=0 && curr[0]-limit<=pies[1-curr[1]][v][curr[1]]){
                if (visited[1-curr[1]][pies[1-curr[1]][v][2]]){
                    v--;
                    continue;
                }
                //System.out.println("\t"+v);
                visited[1-curr[1]][pies[1-curr[1]][v][2]]=true;
                output[1-curr[1]][pies[1-curr[1]][v][2]]=curr[2]+1;
                stack.add(new int[]{pies[1-curr[1]][v][1-curr[1]],1-curr[1],curr[2]+1});
                v--;
            }
        }

        for (int i=0;i<numPies;i++){
            if (!visited[0][i]){
                //System.out.println("-1");
                out.write("-1\n");
            }
            else {
                //System.out.println(output[0][i]);
                out.write(output[0][i] + "\n");
            }
        }
        out.close();
    }
    //to is 1=pies[a][b][3]
    public static int bfs(int val,int to){
        int low=0;
        int high= numPies-1;
        if (pies[1-to][numPies-1][to]<val){
            return numPies-1;
        }
        while (low<=high){
            int mid= (high+low)/2;
            if (pies[1-to][mid][to]>val){
                high=mid-1;
            }
            else if (pies[1-to][mid][to]<val){
                low=mid+1;
            }
            else{
                while(mid<numPies-1 && pies[1-to][mid+1][to]==val){
                    mid++;
                }
                return mid;
            }
        }
        return high;
    }
}