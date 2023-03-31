package org.algo.leetcode;

public class Pow50 {
    public static double myPow(double x, long n) {
        double result = 1d;
        if (n == 0) {
            return result;
        }
        boolean isNegative = false;

        if (n < 0) {
            isNegative = true;
            n = n * -1;
        }

        if (n % 2 == 1) {
            result = x;
        }

        while (n >= 1) {
            n /= 2;
            x = x * x;
            if (Double.isInfinite(x) || Double.isInfinite(result)) return 0d;
            if (n % 2 == 1) result = result * x;
        }

        if (isNegative) result = 1d / result;

        return result;
    }

    public static void main(String[] args){
//    System.out.println(myPow(2.10000, 3));
//    System.out.println(myPow(2.00000, -2));
    System.out.println(myPow(2.00000, -2147483648));
    }
}
