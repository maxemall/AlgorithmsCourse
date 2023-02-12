package org.algo.algebraic;

import org.algo.HappyTicketTest;
import org.algo.algebraic.FibonacciAlgorithms;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FibonacciAlgorithmsTest {

  private final int maxSteps;
  private final BigInteger expectedResult;

  public FibonacciAlgorithmsTest(int maxSteps, BigInteger expectedResult) {
    this.maxSteps = maxSteps;
    this.expectedResult = expectedResult;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() throws AssertionError {
    Collection<Object[]> data = new ArrayList<>();
    ClassLoader classLoader = HappyTicketTest.class.getClassLoader();
      for (int i = 0; i <= 12; i++) {
      String inputFile = classLoader.getResource("fibonacci/test." + i + ".in").getFile();
      String outputFile = classLoader.getResource("fibonacci/test." + i + ".out").getFile();

      try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        int maxSteps = Integer.parseInt(reader.readLine().trim());
        try (BufferedReader reader2 = new BufferedReader(new FileReader(outputFile))) {
          BigInteger expectedResult = new BigInteger(reader2.readLine().trim());
          data.add(new Object[] {maxSteps, expectedResult});
        }
      } catch (Exception e) {
        System.out.println("Error while reading file: " + e.getMessage());
        throw new AssertionError(e);
      }
    }
    return data;
  }

  @Test
  public void testFibonacciIterative() {
    BigInteger fibonacci =
        new FibonacciAlgorithms.FibonacciIterative().findFibonacciList(maxSteps);
    Assert.assertEquals(expectedResult, fibonacci);
  }

  @Test
  //начиная с 9 stack overflow error
  public void testFibonacciRecursive() {
    BigInteger fibonacci =
            new FibonacciAlgorithms.FibonacciRecursive().findFibonacciList(maxSteps);
    Assert.assertEquals(expectedResult, fibonacci);
  }

  @Test
  //начиная с 7 теста ошибка в расчетах из-за плавающей точки
  public void testFibonacciGoldenSection() {
    BigInteger fibonacci =
            new FibonacciAlgorithms.FibonacciGoldenSection().findFibonacciList(maxSteps);
    Assert.assertEquals(expectedResult, fibonacci);
  }

  @Test
  //начиная с 7 теста ошибка в расчетах из-за плавающей точки
  public void testFibonacciMatrix() {
    BigInteger fibonacci =
            new FibonacciAlgorithms.FibonacciMatrix().findFibonacciList(maxSteps);
    Assert.assertEquals(expectedResult, fibonacci);
  }
}
