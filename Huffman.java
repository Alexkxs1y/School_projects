//Liaptov Aleksey, 2019a
//20.02.2018, Huffman
//20.02.2018

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Ar {

    static int[] counts = new int[(Byte.MAX_VALUE + 1) * 2];
    static int n = 0;
    static Node[] tree;

    static class Node implements Comparable<Node> {
        int weight, left, right, num, c;
        String code;

        public Node(int weight, int i, int num) {
            this.weight = weight;
            this.c = i;
            this.left = -1;
            this.right = -1;
            this.code = "";
            this.num = num;
        }

        public int compareTo(Node o) {
            return Integer.compare(weight, o.weight);
        }
    }

    public static void setCodes(Node root, String prefix) {
        root.code = prefix;
        if (root.left != -1) {
            setCodes(tree[root.left], prefix + "0");
            setCodes(tree[root.right], prefix + "1");
        }
    }


    public static void main(String[] args) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("input.txt"));


        for (int i = 0; i < bytes.length; i++) {
            counts[bytes[i]]++;
            if (counts[bytes[i]] == 1) {
                n++;
            }
        }
        System.out.println(n);

        tree = new Node[2 * n - 1];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int cur = 0;
        for (int i = 0; i < (Byte.MAX_VALUE + 1) * 2; i++) {
            if (counts[i] > 0) {
                tree[cur] = new Node(counts[i], i, cur);
                pq.add(tree[cur]);
                cur++;
            }
        }

        while (pq.size() > 1) {
            Node a = pq.poll();
            Node b = pq.poll();
            tree[cur] = new Node(a.weight + b.weight, 0, cur);
            tree[cur].left = a.num;
            tree[cur].right = b.num;
            pq.add(tree[cur]);
            cur++;
        }

        setCodes(tree[2 * n - 2], "");

        String[] codes = new String[(Byte.MAX_VALUE + 1) * 2];

        for (int i = 0; i < n; i++) {
            codes[tree[i].c] = tree[i].code;
        }

        StringBuilder ans = new StringBuilder("");
        for (int i = 0; i < bytes.length; i++) {
            ans.append(codes[bytes[i]]);
        }

        BitSet bs = new BitSet();
        for (int i = 0; i < 32; i++) {
            bs.set(i, ((n >> i) & 1) == 1);
        }

        int count = 0;
        for (int i = 0; i < (Byte.MAX_VALUE + 1) * 2; i++) {
            if (counts[i] > 0) {
                System.out.println(i);
                for (int j = 0; j < 8; j++) {
                    bs.set(32 + count * 40 + j, ((i >> j) & 1) == 1);
                }
                for (int j = 0; j < 32; j++) {
                    bs.set(32 + count * 40 + 8 + j, ((counts[i] >> j) & 1) == 1);
                }
                count++;
            }
        }

        for (int i = 0; i < ans.length(); i++) {
            bs.set(i + 32 + count * 40, ans.charAt(i) == '1');
        }
        Files.write(Paths.get("output.txt"), Arrays.copyOf(bs.toByteArray(), 4 + 10*count + ans.length() / 8));

    }
}
