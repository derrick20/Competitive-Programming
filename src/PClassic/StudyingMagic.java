import java.util.*;
import java.io.*;


public class StudyingMagic {

	static int findTriples (int[] A) {
	// TODO: Implement findTriples function
		Arrays.sort(A);
		HashSet<Integer> set = new HashSet<>();
		for (int val : A) {
			set.add(val);
		}
		int[] mapped = new int[set.size()];
		int ptr = 0;
		mapped[ptr]++;
		for (int i = 1; i < A.length; i++) {
			if (A[i] != A[i - 1]) {
				ptr++;
			}
			mapped[ptr]++;
		}
		int N = mapped.length;
		long[] below = new long[N];
		long[] above = new long[N];
		for (int i = 1; i < N; i++) {
			below[i] = below[i - 1] + mapped[i - 1];
		}
		for (int i = N - 2; i >= 0; i--) {
			above[i] = above[i + 1] + mapped[i + 1];
		}
		long mod = (long) 1e9 + 7;
		long ans = 0;
		for (int i = 1; i <= N - 2; i++) {
			ans += below[i] * above[i] * (mapped[i]);
			ans %= mod;
		}
		return (int) ans;
    }


	public static void main(String[] args) throws NumberFormatException, IOException{
		File filein = new File("StudyingMagicIN.txt");
		String st;
		BufferedReader br = new BufferedReader(new FileReader(filein));

		while ((st = br.readLine()) != null){
			String[] temp = st.split(" ");
			int[] A = new int[temp.length];
			int count = 0;
			for (String i: temp) {
				A[count] = Integer.parseInt(i);
				count++;
			}
			int ans = findTriples(A);
			System.out.println(ans);
		}
		br.close();
	}



}
