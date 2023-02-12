package org.algo.algebraic;

import java.math.BigInteger;

/**
 * 14. +1 байт. Написать класс умножения матриц, реализовать алгоритм возведения матрицы в степень
 * через двоичное разложение показателя степени, реализовать алгоритм поиска чисел Фибоначчи O(LogN)
 * через умножение матриц, используя созданный класс.
 */
public class MatrixAlgorithms {
  public static BigInteger[][] multiplyMatrix(BigInteger[][] matrix, BigInteger[][] matrix2) {
    if (matrix.length > 2 || matrix[0].length > 2 || matrix2.length > 2 || matrix2[0].length > 2)
      throw new UnsupportedOperationException("Размер марицы больше " + "чем 2х2");
    BigInteger[][] result = new BigInteger[2][2];
    result[0][0] = matrix[0][0].multiply(matrix2[0][0]).add(matrix[0][1].multiply(matrix2[1][0]));
    result[0][1] = matrix[0][0].multiply(matrix2[0][1]).add(matrix[0][1].multiply(matrix2[1][1]));
    result[1][0] = matrix[1][0].multiply(matrix2[0][0]).add(matrix[1][1].multiply(matrix2[1][0]));
    result[1][1] = matrix[1][0].multiply(matrix2[0][1]).add(matrix[1][1].multiply(matrix2[1][1]));
    return result;
  }

  /**
   * реализовать алгоритм возведения матрицы в степень * через двоичное разложение показателя
   * степени
   *
   * @param matrix число
   * @param degree степень
   * @return
   */
  public static BigInteger[][] powerMatrixByDegreesOfTwo(BigInteger[][] matrix, long degree) {
    BigInteger[][] result = {
      {BigInteger.valueOf(1), BigInteger.valueOf(0)}, {BigInteger.valueOf(0), BigInteger.valueOf(1)}
    };
    if (degree == 0) {
      return result;
    }

    if (degree < 0) {
      throw new UnsupportedOperationException(
          "Возведение в отрицательную степень не поддерживается");
    }

    if (degree % 2 == 1) {
      result = matrix;
    }

    while (degree >= 1) {
      degree /= 2;
      matrix = multiplyMatrix(matrix, matrix);
      if (degree % 2 == 1) result = multiplyMatrix(result, matrix);
    }

    return result;
  }
}
