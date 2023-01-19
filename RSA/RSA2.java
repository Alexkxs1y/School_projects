//Lipatov Aleksey, 2019a
//12.03.2018, RSA2 p, q are given, find open key and privat key
//12.03.2018


import java.io.*;
import java.util.*;

public class RSA2 {

    public static long find_d(long A, long B) {
        long a1, b1, r1, a0, b0, r0, tmp_r, tmp_a, tmp_b;

        a1 = 0;
        b1 = 1;
        r1 = B;
        a0 = 1;
        b0 = 0;
        r0 = A;

        while (r1 != 0) {
            long quotient =r0 / r1;
            tmp_r = r0 - quotient * r1;
            tmp_a = a0 - quotient * a1;
            tmp_b = b0 - quotient * b1;

            r0 = r1;
            a0 = a1;
            b0 = b1;

            r1 = tmp_r;
            a1 = tmp_a;
            b1 = tmp_b;
        }
        return a0;
    }

    public static boolean gcd(long a, long b) {
        while (b !=0) {
            long tmp = a%b;
            a = b;
            b = tmp;
        }
        return (a == 1);
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Scanner fr = new Scanner(new FileReader("prime.txt"));
        int MAX_N = 50847534;
        long p = 0, q = 0, e = 0, n = 0 , phi = 0, d = -1;

        p = sc.nextLong();
        q =  sc.nextLong();
        n = p*q;
        phi = n + 1 - (p + q);
        e = phi;

        Random r = new Random();
        long[] prime = new long[MAX_N];
        for (int i = 0; i < MAX_N; i++) {
            prime[i] = fr.nextLong();
        }

        while(d<0) {
            e = phi;
            while (!(gcd(phi, e))) {
                e = prime[r.nextInt(MAX_N)];
            }
            d = find_d(e, phi);
        }

        System.out.println("Открытый ключ: (" + e + ", " + n + ")");
        System.out.println("Закрытый ключ: (" + d + ", " + n + ")");
        System.out.println((e*d)%(phi));
    }
}
