//Lipatov Aleksey, 2019a
//24.04.2018, find pattern in a given string
//24.04.2018

import java.io.*;
import java.util.*;

public class Patterns {

    static long PRIME = 239;
    static long q;
    static long MOD = (long)10e9 + 7;

    public static long find_hash(String s) {
        long curHash = 0;
        for (int i = 0; i < s.length(); i++) {
            curHash = (curHash * PRIME + s.charAt(i)) % MOD;
        }
        return curHash;
    }

    public static long pow(long a, long n, long m) {
        if (n==0) return 1;
        if (n%2 == 0) {
            long tmp = pow(a, n / 2, m) % m;
            return (tmp * tmp) % m;
        }
        return a*pow(a, n-1, m)%m;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileReader("input.txt"));

        String str = sc.nextLine();
        String pat = sc.nextLine();

        int n = str.length();
        int k = pat.length();

        q = pow(PRIME, k-1, MOD);

        long curHash = 0;

        for (int i = 0; i < k; i++) {
            curHash = (curHash * PRIME + str.charAt(i)) % MOD;
        }

        long patHash = find_hash(pat);
        if (patHash == curHash && pat.equals(str.substring(0, k))) System.out.println(0);;

        for (int i = 1; i + k - 1 < n; i++) {
            curHash = ((((curHash - (str.charAt(i - 1) * q)) % MOD) + MOD) % MOD);
            curHash = (curHash * PRIME + str.charAt(i + k - 1)) % MOD;
            if (patHash == curHash) {
                boolean flag = true;
                for (int j = 0; j < k; j++) {
                    if (pat.charAt(j) != str.charAt(j+i)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) System.out.println(i);
            }
        }
    }
}
