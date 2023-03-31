package org.algo.algebraic;

public class PowerAlgorithms {
  /**
   * 01. +1 байт. Реализовать итеративный O(N) алгоритм возведения числа в степень
   *
   * @param number число
   * @param degree степень
   * @return
   */
  public static double powerIterative(double number, long degree) {
    double result = 1;
    if (degree == 0) {
      return result;
    }

    if (degree < 0) {
      throw new UnsupportedOperationException(
          "Возведение в отрицательную степень не поддерживается");
    }

    for (int i = 1; i <= degree; i++) {
      result = result * number;
    }
    return result;
  }

  /**
   * 11. +1 байт. Реализовать алгоритм возведения в степень через домножение O(N/2+LogN) = O(N).
   *
   * @param number число
   * @param degree степень
   * @return
   */
  public static double powerByHalfMultiplying(double number, long degree) {
    double result = 1;
    if (degree == 0) {
      return result;
    }

    if (degree < 0) {
      throw new UnsupportedOperationException(
          "Возведение в отрицательную степень не поддерживается");
    }

    result = number;
    long twoDegree = 2;
    while (degree >= twoDegree) {
      result = result * result;
      twoDegree = twoDegree * 2;
    }

    twoDegree = 1 + twoDegree / 2;

    for (long i = twoDegree; i <= degree; i++) {
      result = result * number;
    }
    return result;
  }

  /**
   * 12. +1 байт. Реализовать алгоритм возведения в степень через двоичное разложение показателя
   * степени O(2LogN) = O(LogN)
   *
   * @param number число
   * @param degree степень
   * @return
   */
  public static double powerByDegreesOfTwo(double number, long degree) {
    double result = 1;
    if (degree == 0) {
      return result;
    }
    boolean isNegative = false;

    if (degree < 0) {
      isNegative = true;
      degree = degree * -1;
    }

    if (degree % 2 == 1) {
      result = number;
    }

    while (degree >= 1) {
      degree /= 2;
      number = number * number;
      if (degree % 2 == 1) result = result * number;
    }

    if (isNegative) result = 1d / result;

    return result;
  }
}
