//Lipatov Aleksey, 2019a
//08.05.2018, prefix function
//30.04.2018

import java.io.*;
import java.util.*;

public class Help {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileReader("input.txt"));
        String str = sc.nextLine();
        int n = str.length();
        int[] prefix = new int[n];
        for (int i = 1; i < n; i++) {
            int k = prefix[i-1];
            while(k>0 && str.charAt(i) != str.charAt(k)) {
                k = prefix[k-1];
            }
            if (str.charAt(i) == str.charAt(k)) prefix[i] = ++k;
        }

        for (int o: prefix) {
            System.out.println(o);
        }
    }
}
