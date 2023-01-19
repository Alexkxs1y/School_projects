//Lipatov Aleksey, 2019a
//10.04.2018, Karatsuba
//10.04.2018

import java.io.*;
import java.util.*;

public class Numbering {
    static long CONST = 1000 * 1000 * 1000;

    static class Number {

        ArrayList<Long> nums;

        Number(String s) {
            nums = new ArrayList<>();
            if (s.charAt(0) == '-') {
                for (int i = s.length(); i > 1; i -= 9) {
                    if (i <= 9) {
                        this.nums.add(-Long.parseLong(s.substring(1, i)));
                    } else {
                        String tmp = s.substring(i - 9, i);
                        this.nums.add(-Long.parseLong(s.substring(i - 9, i)));
                    }
                }
            } else {
                for (int i = s.length(); i > 0; i -= 9) {
                    if (i < 9) {
                        this.nums.add(Long.parseLong(s.substring(0, i)));
                    } else {
                        this.nums.add(Long.parseLong(s.substring(i - 9, i)));
                    }
                }
            }
        }

        void check() {
            for (int i = 0; i < nums.size(); i++) {
                if (Math.abs(nums.get(i)) >= CONST) {
                    normalise();
                }
            }
        }

        void normalise() {
            for (int i = 0; i < nums.size() - 1; i++) {
                if (nums.get(i) > CONST) {
                    nums.set(i + 1, nums.get(i + 1) + nums.get(i) / CONST);
                    nums.set(i, nums.get(i) % CONST);
                }
                if (nums.get(i) < 0) {
                    long tmp = (nums.get(i) + 1) / CONST - 1;
                    nums.set(i + 1, nums.get(i + 1) + tmp);
                    nums.set(i, nums.get(i) - tmp * CONST);
                }
            }

            if (nums.get(nums.size() - 1) >= CONST) {
                nums.add(nums.get(nums.size() - 1) / CONST);
                nums.set(nums.size() - 2, nums.get(nums.size() - 2) % CONST);
            }

            int i = nums.size() - 1;
            while (nums.get(i) == 0 && i > 0) {
                nums.remove(i);
                i--;
            }
        }

        public String toString() {
            this.normalise();
            StringBuilder sb = new StringBuilder("");
            if (nums.get(nums.size() - 1) < 0) {
                sb.append("-");
                if (nums.size() > 1) {
                    nums.set(0, nums.get(0) - CONST);
                    nums.set(nums.size() - 1, nums.get(nums.size() - 1) + 1);
                    for (int i = 1; i < nums.size() - 1; i++) {
                        nums.set(i, nums.get(i) - (CONST - 1));
                    }
                }
            }

            int i = nums.size() - 1;
            while (nums.get(i) == 0 && i > 0) {
                nums.remove(i);
                i--;
            }

            sb.append("" + Math.abs(nums.get(nums.size() - 1)));

            for (i = nums.size() - 2; i > -1; i--) {
                int l = ("" + Math.abs(nums.get(i))).length();
                for (int j = 0; j < 9 - l; j++) {
                    sb.append("0");
                }
                sb.append("" + Math.abs(nums.get(i)));
            }
            return sb.toString();

        }
    }

    static Number add(Number x, Number y) {
        x.check();
        y.check();
        if (x.nums.size() < y.nums.size()) {
            Number tmp = x;
            x = y;
            y = tmp;
        }
        Number res = new Number("0");
        res.nums = new ArrayList<>();

        for (int i = 0; i < x.nums.size(); i++) {
            if (i > y.nums.size() - 1) {
                res.nums.add(x.nums.get(i));
                continue;
            }
            res.nums.add(x.nums.get(i) + y.nums.get(i));
        }
        return res;
    }

    static Number sub(Number x, Number y) {
        x.check();
        y.check();
        Number res = new Number("0");
        res.nums = new ArrayList<>();

        for (int i = 0; i < Math.max(y.nums.size(), x.nums.size()); i++) {
            if (i > y.nums.size() - 1) {
                res.nums.add(x.nums.get(i));
                continue;
            }
            if (i > x.nums.size() - 1) {
                res.nums.add(-y.nums.get(i));
                continue;
            }
            res.nums.add(x.nums.get(i) - y.nums.get(i));
        }
        return res;
    }

    static Number naive_mul(Number x, Number y) {
        Number res = new Number("0");
        res.nums = new ArrayList<>();
        for (int i = 0; i < x.nums.size(); i++) {
            res.nums.add(x.nums.get(i) * y.nums.get(0));
        }
        return res;
    }

    static Number mul_ten(int k, Number o) {
        StringBuilder sb = new StringBuilder(o.toString());
        for (int i = 0; i < k * 9; i++) {
            sb.append("0");
        }
        return new Number(sb.toString());
    }

    static Number mul(Number x, Number y) {
        x.check();
        y.check();
        if (x.nums.size() == 1) {
            return naive_mul(y, x);
        }
        if (y.nums.size() == 1) {
            return naive_mul(x, y);
        }

        int k = Math.max(x.nums.size(), y.nums.size()) / 2;

        Number a = take_prefix(k, x);
        Number b = take_suffix(k, x);
        Number c = take_prefix(k, y);
        Number d = take_suffix(k, y);

        Number ac = mul(a, c);
        Number bd = mul(b, d);
        Number aplb = add(a, b);
        Number cpld = add(c, d);

        Number res = sub(mul(aplb, cpld), add(ac, bd));
        res = mul_ten(k, res);
        res = add(res, ac);
        bd = mul_ten(2 * k, bd);

        return add(res, bd);

    }

    static Number take_prefix(int k, Number o) {
        if (k > o.nums.size() - 1) return o;
        Number res = new Number("0");
        res.nums = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            res.nums.add(o.nums.get(i));
        }
        return res;
    }

    static Number take_suffix(int k, Number o) {
        Number res = new Number("0");
        if (k > o.nums.size() - 1) return res;
        res.nums = new ArrayList<>();
        for (int i = k; i < o.nums.size(); i++) {
            res.nums.add(o.nums.get(i));
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileReader("input.txt"));
        Number n = new Number(sc.nextLine());
        String todo = sc.nextLine();
        Number m = new Number(sc.nextLine());
        if (todo.equals("+")) {
            System.out.println(add(n, m));
        }
        if (todo.equals("-")) {
            System.out.println(sub(n, m));
        }
        if (todo.equals("*")) {
            System.out.println(mul(n, m));
        }
    }
}

