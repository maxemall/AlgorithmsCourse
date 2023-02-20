package org.algo.algebraic;

import java.util.ArrayList;

public class AlgebraicAlgorithms {

  public static void main(String[] args) {
    long m = System.currentTimeMillis();
    System.out.println(PowerAlgorithms.powerIterative(3, 11));
    System.out.println((double) (System.currentTimeMillis() - m));

    m = System.currentTimeMillis();
    System.out.println(PowerAlgorithms.powerByHalfMultiplying(3, 11));
    System.out.println((double) (System.currentTimeMillis() - m));

    m = System.currentTimeMillis();
    System.out.println(PowerAlgorithms.powerByDegreesOfTwo(3, 11));
    System.out.println((double) (System.currentTimeMillis() - m));

    m = System.currentTimeMillis();
    System.out.println(PrimeAlgorithms.primeNumbers(100));
    System.out.println((double) (System.currentTimeMillis() - m));

    m = System.currentTimeMillis();
    System.out.println(new FibonacciAlgorithms.FibonacciRecursive().findFibonacciList(100));
    System.out.println((double) (System.currentTimeMillis() - m));

    m = System.currentTimeMillis();
    System.out.println(new FibonacciAlgorithms.FibonacciIterative().findFibonacciList(100));
    System.out.println((double) (System.currentTimeMillis() - m));

    m = System.currentTimeMillis();
    System.out.println(new FibonacciAlgorithms.FibonacciMatrix().findFibonacciList(100));
    System.out.println((double) (System.currentTimeMillis() - m));
  }

}
