import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;
public class BeautyCopy {
    public static int mod = 1000000007;
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        // Scanner scan = new Scanner(System.in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(bf.readLine());
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int[] l = new int[n]; for(int i=0; i<n; i++) l[i] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(bf.readLine());
        int[] r = new int[n]; for(int i=0; i<n; i++) r[i] = Integer.parseInt(st.nextToken());
        long ans = 0;
        for(int i=1; i<n; i++) {
            ans += prob(l[i-1], r[i-1], l[i], r[i]);
            System.out.println(prob(l[i-1], r[i-1], l[i], r[i]));
            ans %= mod;
        }

        long toAdd = ans;
        System.out.println("Equal: " + ans);
        ans *= 3; // WHY IS THIS HERE!@1991J)!)))!)RMX @%@*%%*QRPAEJ{1209 N:PIMJ:O
        ans += 1;
        ans %= mod;
        long toAddSq = 0L + exp((int)toAdd, 2);
        for(int i=1; i<n; i++) {
            toAddSq += mod;
            toAddSq -= 1L*prob(l[i-1], r[i-1], l[i], r[i])*prob(l[i-1], r[i-1], l[i], r[i])%mod;
            toAddSq %= mod;
        }
        toAddSq %= mod;
        long dependentComponent = 0;
        System.out.println("P1^2 - P2: " + toAddSq);
        for(int i=2; i<n; i++) {
            toAddSq -= mult(prob(l[i-2], r[i-2], l[i-1], r[i-1]), prob(l[i-1], r[i-1], l[i], r[i]))*2 % mod;
//            toAddSq += prob3(l[i-2], r[i-2], l[i-1], r[i-1], l[i], r[i])*2;
            dependentComponent += prob3(l[i-2], r[i-2], l[i-1], r[i-1], l[i], r[i])*2;
            dependentComponent %= mod;
//            System.out.println(dependentComponent);
            toAddSq += mod;
            toAddSq %= mod;
            System.out.println("Indep: " + toAddSq);
        }
        System.out.println("Dep: " + dependentComponent);

        ans += toAddSq + dependentComponent;
        ans %= mod;
        ans += mod;
        ans %= mod;
        out.println(ans);

        // int n = Integer.parseInt(st.nextToken());
        // int n = scan.nextInt();

        out.close(); System.exit(0);
    }
    public static int prob3(int l1, int r1, int l2, int r2, int l3, int r3) {
        long ans = 0;
        ans += prob(l1, r1, l2, r2);
        ans += prob(l2, r2, l3, r3);
        int up = Math.min(Math.min(r1, r2), r3);
        int low = Math.max(Math.max(l1, l2), l3);
        ans -= (1 - (1L*Math.max(0, up-low+1)*inv(mult3(r1-l1+1, r2-l2+1, r3-l3+1))%mod));
        ans %= mod;
        ans += mod;
        ans %= mod;
        return (int)ans;
    }
    public static int prob(int l1, int r1, int l2, int r2) {
        // probability different
        int low = Math.max(l1, l2);
        int high = Math.min(r1, r2);
        return (1 - mult((Math.max(0, high-low+1)), inv(mult(r1-l1+1, r2-l2+1))) + mod) % mod;

    }
    // Exponentation
    public static int mult(int a, int b) {
        return (int)(1L*a*b % mod);
    }
    public static int mult3(int a, int b, int c ) {
        return mult(mult(a, b), c);
    }
    public static int exp(int base, int e) {
        if(e == 0) return 1;
        if(e == 1) return base;
        int val = exp(base, e/2);
        int ans = (int)(1L*val*val % mod);
        if(e % 2 == 1)
            ans = (int)(1L*ans*base % mod);
        return ans;
    }
    // Exponentation
    public static int inv(int base) {
        return exp(base, mod-2);
    }

}