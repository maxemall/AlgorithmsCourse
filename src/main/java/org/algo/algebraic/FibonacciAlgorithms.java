package org.algo.algebraic;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 02. +1 байт. Реализовать рекурсивный O(2^N) и итеративный O(N) алгоритмы поиска чисел Фибоначчи.
 *
 * @return массив чисел Фибоначчи
 */
public class FibonacciAlgorithms {

  interface Fibonacci {

    public BigInteger findFibonacciList(int limit);
  }

  protected abstract static class FibonacciAbstract implements Fibonacci {
    protected BigInteger a = BigInteger.valueOf(0);
    protected BigInteger b = BigInteger.valueOf(1);
    protected BigInteger newValue = BigInteger.valueOf(0);
  }

  /**
   * 02. +1 байт. Реализовать рекурсивный O(2^N) и итеративный O(N) алгоритмы поиска чисел
   * Фибоначчи.
   */
  public static class FibonacciRecursive extends FibonacciAlgorithms.FibonacciAbstract {
    private BigInteger findFibonacciList(int limit, int step, BigInteger a, BigInteger b) {
      newValue = a.add(b);
      if (step >= limit) return this.newValue;
      return findFibonacciList(limit, ++step, b, newValue);
    }

    @Override
    public BigInteger findFibonacciList(int limit) {
      if (limit <= 1) return BigInteger.valueOf(limit);
      return findFibonacciList(limit, 2, this.a, this.b);
    }
  }

  /**
   * 02. +1 байт. Реализовать рекурсивный O(2^N) и итеративный O(N) алгоритмы поиска чисел
   * Фибоначчи.
   */
  public static class FibonacciIterative extends FibonacciAlgorithms.FibonacciAbstract {
    @Override
    public BigInteger findFibonacciList(int limit) {
      if (limit <= 1) return BigInteger.valueOf(limit);
      for (int i = 2; i <= limit; i++) {
        newValue = a.add(b);
        a = b;
        b = newValue;
      }
      return newValue;
    }
  }

  /** 13. +1 байт. Реализовать алгоритм поиска чисел Фибоначчи по формуле золотого сечения. */
  public static class FibonacciGoldenSection extends FibonacciAlgorithms.FibonacciAbstract {
    private static final BigDecimal BD2 = BigDecimal.valueOf(2);
    private static final BigDecimal BD1 = BigDecimal.valueOf(1);

    @Override
    public BigInteger findFibonacciList(int limit) {
      if (limit <= 1) return BigInteger.valueOf(limit);
      BigDecimal phi = (BD1.add(BigDecimal.valueOf(Math.sqrt(5)))).divide(BD2);
      BigDecimal psi = (BD1.subtract(BigDecimal.valueOf(Math.sqrt(5)))).divide(BD2);

      return phi.pow(limit)
          .subtract(psi.pow(limit))
          .divide(BigDecimal.valueOf(Math.sqrt(5)))
          .toBigInteger();
    }
  }

  /**
   * 14. +1 байт. Написать класс умножения матриц, реализовать алгоритм возведения матрицы в степень
   * через двоичное разложение показателя степени, реализовать алгоритм поиска чисел Фибоначчи
   * O(LogN) через умножение матриц, используя созданный класс.
   */
  public static class FibonacciMatrix extends FibonacciAlgorithms.FibonacciAbstract {

    @Override
    public BigInteger findFibonacciList(int limit) {
      BigInteger[][] matrix = {{b, b}, {b, a}};
      if (limit <= 1) return BigInteger.valueOf(limit);
      if (limit == 2) return BigInteger.valueOf(1);;
      return MatrixAlgorithms.powerMatrixByDegreesOfTwo(matrix, limit-1)[0][0];
    }
  }
}
