//Lipato Aleksey, 2019a
//12.03.2018, RSA1, p, q, e are given, find d
//13.03.2018

import java.util.*;

public class RSA1 {

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


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long p, q, e, n , phi, d;
        p = sc.nextLong();
        q =  sc.nextLong();
        e =  sc.nextLong();
        n = p*q;
        phi = n + 1 - (p + q) ;
        d = find_d(e, phi);
        System.out.println(d);
    }
}
