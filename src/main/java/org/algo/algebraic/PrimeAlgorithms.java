package org.algo.algebraic;

import java.util.ArrayList;
import java.util.List;

public class PrimeAlgorithms {
    /**
     * 03. +1 байт. Реализовать алгоритм поиска количества простых чисел через перебор делителей,
     * O(N^2).
     *
     * @param limit ограничение для поиска простых чисел
     * @return список простых чисел
     */
    public static int primeNumbers(int limit) {
      int result = 0;
      for (int i = 1; i <= limit; i++) {
        for (int i1 = 2; i1 <= i; i1++) {
          if ((i % i1 == 0) && (i1 < i)) break;
          if (i == i1) result++;
        }
      }
      return result;
    }
}
