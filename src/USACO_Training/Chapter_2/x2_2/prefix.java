/*
ID: d3rrickl
LANG: JAVA
PROG: prefix
 */
/**
 * Actually, i'm not entirely sure why a trie would be useful here??
 * Maybe it would make sense to do hashing lookups, but i don't feel like
 * it...
 */

import java.io.*;
import java.util.*;

public class prefix {
    static FastScanner sc;
    static PrintWriter out;

    static void setupIO(String problem_name, boolean isTesting) throws Exception {
        String prefix = isTesting ? "/Users/derrick/IntelliJProjects/src/USACO_Training/Chapter_2/x2_2/" : "";
        sc = new FastScanner(prefix + problem_name + ".in");
        out = new PrintWriter(new FileWriter(prefix + problem_name + ".out"));
    }

    static void setupIO() throws Exception {
        sc = new FastScanner();
        out = new PrintWriter(System.out);
    }

    public static void main(String args[]) throws Exception {
        setupIO("prefix", false);

        String buffer = sc.next();

        ArrayList<String> primitives = new ArrayList<>();
        while (buffer.compareTo(".") != 0) {
            primitives.add(buffer);
            buffer = sc.next();
        }
        StringBuilder seq = new StringBuilder();
        while (sc.hasNext()) {
            seq.append(sc.next());
        }

        boolean[] dp = new boolean[seq.length() + 1];
        // dp[i] = true if it is possible to reach this prefix of first i
        // characters.
        dp[0] = true;
        // O(K * L * N)
        // 200 primitives, max len 10, for 200,000, which is 4*10^8. That's barely fine here
        int highest = 0;
        for (int len = 1; len <= seq.length(); len++) {
            for (int i = 0; i < primitives.size(); i++) {
                String added = primitives.get(i);
                int addedLen = added.length();
                if (addedLen <= len && dp[len - addedLen]) {
                    // Going up to, but excluding, len, and starting
                    // from the the position which excludes addedLen chars,
                    // is where it starts. (len - 1 is the actual position
                    // within the real string, but subtracting the -1's cancels
                    // Find ABAB, and at position 3
                    // Testing to see if B can be appended to ABA
                    // At len 4 - len 1, we are at pos 3, and check if it matches
                    // from [3, 4) with B, which it does
                    if (seq.substring(len - addedLen, len).compareTo(added) == 0) {
                        dp[len] = true; // We reached it now
                        highest = Math.max(highest, len);
                    }
                }
            }
        }
        out.println(highest);
        out.close();
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
                curr = curr.children[c]; // Move down into that vertex
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