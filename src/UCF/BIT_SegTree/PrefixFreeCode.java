/**
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class PrefixFreeCode {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        N = sc.nextInt();
        int K = sc.nextInt();
        TreeSet<String> arr = new TreeSet<>();
        for (int i = 0; i < N; i++) {
            arr.add(sc.next());
        }
        Trie trie = new Trie();
        TreeMap<String, Integer> map = new TreeMap<>();
        // Use the map to convert the strings into their rank/ids
        for (String s : arr) {
            trie.insert(s);
            map.put(s, map.size() + 1);
        }
        String s = sc.next();
        StringBuilder currStr = new StringBuilder();
        TrieNode curr = trie.root;
        int[] pattern = new int[K];
        int pos = 0;
        for (char c : s.toCharArray()) {
            int index = c - 'a';
            curr = curr.children[index];
            currStr.append(c);
            // See if it matches anything, and if so, recreate it
//            if (map.containsKey(curr.toString())) {
//                pattern[pos++] = map.get(curr.toString());
//                curr = new StringBuilder();
//            }
            // Use the trie to speed up the searching of a specific string
            if (curr.isLeaf) {
                curr = trie.root; // go back to the top
                pattern[pos++] = map.get(currStr.toString());
                // add this converted value to the pattern
                currStr = new StringBuilder(); // reset running string
            }
        }

        BIT = new int[N + 1];
        // The BIT will store 1's for numbers already used, which allows us to do the digit dp
        // while accounting for using numbers (we subtract out anything used - they are not an option)
        long mod = (long) 1e9 + 7;
        // dp at i stores the number of ways to make a sequence less than the pattern seequence
        long currDP = pattern[0] - 1; // 1 less than the number of options available for first 1
        update(pattern[0], 1); // That number is not usable now
        for (int i = 1; i < K; i++) {
            long nextDP = 0;
            // Two transition cases:
            // We can be unmatched already, so we can use any available number
            nextDP += (currDP * (N - i)) % mod; // i represents how many values used, so N - i is number of choices
            nextDP %= mod;
            // We can be matched, but then this position is the first unmatch,
            // so we can achieve this by using any number available less than the pattern's value here
            int lessThan = (pattern[i] - 1 - prefixSum(pattern[i] - 1));
            nextDP += lessThan;
            nextDP %= mod;
            update(pattern[i], 1); // That number is not usable now
            // We could have matched all the way here, then use a less than to bring us to an
            // unmatching state. (Implicitly there is another dimension of, matched or unmatched,
             // but matched will always equal 1, since only 1 way to do that)

            currDP = nextDP;
        }
        // It will be the position right AFTER the number of values coming before it!
        currDP++;
        currDP %= mod;
        out.println(currDP);
        out.close();
    }

    static int[] BIT;
    static int N;

    static int prefixSum(int i) {
        int ans = 0;
        while (i > 0) {
            ans += BIT[i];
            i -= i & -i;
            // Remove the least significant bit, so that we move to the next
            // right turn that contains a subtree
        }
        return ans;
    }

    static void update(int i, int val) {
        while (i <= N) {
            BIT[i] += val;
            i += i & -i;
        }
    }

    static class Trie {
        TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                int index = c - 'a';
                if (curr.children[index] == null) {
                    curr.children[index] = new TrieNode(c);
                }
                curr = curr.children[index]; // Move down into that vertex
                if (i == word.length() - 1) {
                    curr.words++;
                    curr.isLeaf = true;
                }
                else {
                    curr.prefixes++;
                }
            }
        }

        public boolean contains(String word) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                int index = c - 'a';
                if (curr.children[index] != null) {
                    curr = curr.children[index];
                }
                else {
                    break;
                }
                if (i == word.length() - 1 && curr.isLeaf) {
                    return true;
                }
            }
            return false;
        }
    }

    static class TrieNode {
        int words, prefixes;
        boolean isLeaf;
        TrieNode[] children;
        char c;

        public TrieNode() {
            children = new TrieNode[26];
        }

        public TrieNode(char myChar) {
            c = myChar;
            children = new TrieNode[26];
        }

        public String toString() {
            StringBuilder edges = new StringBuilder();
            for (TrieNode child : children) {
                if (child != null) {
                    edges.append(child.c + ", ");
                }
            }
            return c + ": " + edges.substring(0, edges.length() - 2);
        }
    }

    static class FastScanner {
        public int BS = 1<<16;
        public char NC = (char)0;
        byte[] buf = new byte[BS];
        int bId = 0, size = 0;
        char c = NC;
        double cnt = 1;
        BufferedInputStream in;

        public FastScanner() {
            in = new BufferedInputStream(System.in, BS);
        }

        public FastScanner(String s) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
            }
            catch (Exception e) {
                in = new BufferedInputStream(System.in, BS);
            }
        }

        private char getChar(){
            while(bId==size) {
                try {
                    size = in.read(buf);
                }catch(Exception e) {
                    return NC;
                }
                if(size==-1)return NC;
                bId=0;
            }
            return (char)buf[bId++];
        }

        public int nextInt() {
            return (int)nextLong();
        }

        public long nextLong() {
            cnt=1;
            boolean neg = false;
            if(c==NC)c=getChar();
            for(;(c<'0' || c>'9'); c = getChar()) {
                if(c=='-')neg=true;
            }
            long res = 0;
            for(; c>='0' && c <='9'; c=getChar()) {
                res = (res<<3)+(res<<1)+c-'0';
                cnt*=10;
            }
            return neg?-res:res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c!='.' ? cur:cur+nextLong()/cnt;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c>32) {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while(c<=32)c=getChar();
            while(c!='\n') {
                res.append(c);
                c=getChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if(c>32)return true;
            while(true) {
                c=getChar();
                if(c==NC)return false;
                else if(c>32)return true;
            }
        }
    }
}