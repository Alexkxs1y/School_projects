//Lipatov Aleksey, 2019a
//12.03.2018, RSA3, find open key and privat key, encrypt and decrypt given number
//13.03.2018

import java.io.*;
import java.util.*;

public class RSA3 {

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
            tmp_r = r0 % r1;
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

    public static long pow(long a, long n, long m) {
        if (n==0) return 1;
        if (n%2 == 0) {
            long tmp = pow(a, n / 2, m) % m;
            return (tmp * tmp) % m;
        }
        return a*pow(a, n-1, m)%m;
    }

    public static long multiply(long a, long b, long m) {
        if (b==0) return 0;
        if (b%2 == 0) return multiply((2*a)%m, b/2, m);
        return a + multiply(a, b-1, m) % m;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Scanner fr = new Scanner(new BufferedReader(new FileReader("prime.txt")));
        int MAX_N = 50000000;
        long p = 0, q = 0, e = 0, n = 0 , phi = 0, d = -1;
        long[] prime = new long[MAX_N];
        for (int i = 0; i < MAX_N; i++) {
            prime[i] = fr.nextLong();
        }
        Random r = new Random();

        p = prime[r.nextInt(MAX_N)];
        q =  prime[r.nextInt(MAX_N)];
        n = p*q;
        phi = n + 1 - (p + q);

        e = phi;
        while (!(gcd(phi, e))) {
            e = prime[r.nextInt(MAX_N)];
        }

        d = find_d(e, phi);
        System.out.println(d);
        System.out.println((multiply(e,d, phi)));
        if (d<0) {
            d += phi * (int)Math.ceil(Math.abs(d) / phi + 1);
        }

        System.out.println(p + " " + q);
        System.out.println("Открытый ключ: (" + e + ", " + n + ")");
        System.out.println("Закрытый ключ: (" + d + ", " + n + ")");
        System.out.println((e*d)%(phi));

        int m = sc.nextInt();
        long ans = pow(m, e, n);
        System.out.println(ans);
        System.out.println(pow(ans, d, n));

    }
}
