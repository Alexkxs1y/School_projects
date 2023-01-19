//Lipatov Aleksey, 2019a
//08.05.2018, Knuta-Morrisa-Pratta 1 (honest).
//30.04.2018

import java.io.*;
import java.util.*;

public class Please {

    static int[] prefix;

    public static void prefix_function(String str) {
        for (int i = 1; i < str.length(); i++) {
            int k = prefix[i - 1];
            while (k > 0 && str.charAt(i) != str.charAt(k)) {
                k = prefix[k - 1];
            }
            if (str.charAt(i) == str.charAt(k)) prefix[i] = ++k;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
        String str = sc.readLine();
        String pattern = sc.readLine();
        sc.close();
        prefix = new int[pattern.length()];
        prefix_function(pattern);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == pattern.charAt(0)) {
                boolean flag = true;
                int tmp = i;
                int j = 0;
                for (j = 0; j < pattern.length(); j++) {
                    if (tmp < str.length() && str.charAt(tmp) == pattern.charAt(j)) {
                        tmp++;
                    } else {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    bw.write(i + " ");
                }
                if (j > 0) i = i + j - prefix[j - 1] - 1;
            }
        }
        bw.close();
    }
}
