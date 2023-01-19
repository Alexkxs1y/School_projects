//Lipatov Aleksey, 2019a
//24.04.2018, Hash table
//24.04.2018

import java.io.*;
import java.util.*;

public class Hash {

    static ArrayList[] table;
    static long PRIME = 239;
    static long MOD;

    public static long find_hash(String s) {
        long curHash = 0;
        for (int i = 0; i < s.length(); i++) {
            curHash = (curHash * PRIME + s.charAt(i)) % MOD;
        }
        return curHash;
    }

    public static void add(String s) {
        int ind = (int)find_hash(s);
        table[ind].add(s);
    }

    public static boolean find(String s) {
        int ind = (int)find_hash(s);
        for (Object i : table[ind]) {
            if (s.equals(i)) return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileReader("input.txt"));
        int n = Integer.parseInt(sc.nextLine());

        MOD = 100*n;
        if (MOD%PRIME == 0) MOD+=1;

        table = new ArrayList[(int)MOD];

        for (int i = 0; i < MOD; i++) {
            table[i] = new ArrayList();
        }

        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            add(s);
        }

        int k = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < k; i++) {
            String s = sc.nextLine();
            System.out.println(find(s));
        }
    }
}
