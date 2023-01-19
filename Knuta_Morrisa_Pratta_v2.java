//Lipatov Aleksey, 2019a
//08.05.2018, Knuta-Morrisa-Pratta 2 (pat#str).
//30.04.2018

import java.io.*;
import java.util.*;

public class KMP2 {

    static int[] prefix;
    static int n;

    public static void prefix_function(String str) {
        for (int i = 1; i < str.length(); i++) {
            int k = prefix[i-1];
            while(k>0 && str.charAt(i) != str.charAt(k)) {
                k = prefix[k-1];
            }
            if (str.charAt(i) == str.charAt(k)) prefix[i] = ++k;
            if (prefix[i] == n) System.out.print(i - 2 * n + " ");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new FileReader("input.txt"));
        String str = sc.readLine();
        String pattern = sc.readLine();
        n = pattern.length();
        pattern += "#";
        pattern += str;
        prefix = new int[pattern.length()];
        prefix_function(pattern);
    }
}
