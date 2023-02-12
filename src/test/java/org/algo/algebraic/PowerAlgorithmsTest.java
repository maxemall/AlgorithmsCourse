package org.algo.algebraic;

import org.algo.HappyTicketTest;
import org.algo.algebraic.PowerAlgorithms;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PowerAlgorithmsTest {

  private final double number;
  private final long degree;
  private final double expectedResult;

  public PowerAlgorithmsTest(double number, double expectedResult, long degree) {
    this.number = number;
    this.expectedResult = expectedResult;
    this.degree = degree;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() throws AssertionError {
    Collection<Object[]> data = new ArrayList<>();
    ClassLoader classLoader = HappyTicketTest.class.getClassLoader();
    // начиная с 11 java.lang.OutOfMemoryError: Java heap space: failed reallocation of scalar
    // replaced objects
    for (int i = 0; i <= 9; i++) {
      String inputFile = classLoader.getResource("power/test." + i + ".in").getFile();
      String outputFile = classLoader.getResource("power/test." + i + ".out").getFile();

      try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        double number = Double.parseDouble(reader.readLine().trim());
        long degree = Long.parseLong(reader.readLine().trim());
        try (BufferedReader reader2 = new BufferedReader(new FileReader(outputFile))) {
          double expectedResult = Double.parseDouble(reader2.readLine().trim());
          data.add(new Object[] {number, expectedResult, degree});
        }
      } catch (Exception e) {
        System.out.println("Error while reading file: " + e.getMessage());
        throw new AssertionError(e);
      }
    }
    return data;
  }

  /** 9 тест зависает*/
  @Test
  public void exponentiateIterative() {
    double result = PowerAlgorithms.powerIterative(number, degree);
    Assert.assertEquals(expectedResult, result, 0.00000000001);
  }

  /** меньше точность на double результатах, выше скорость выполняется 9 тест*/
  @Test
  public void exponentiateByMultiplying() {
    double result = PowerAlgorithms.powerByHalfMultiplying(number, degree);
    Assert.assertEquals(expectedResult, result, 0.0000001);
  }

  /** меньше точность на double результатах, выше скорость выполняется 9 тест*/
  @Test
  public void exponentiateByDegreesOfTwo() {
    double result = PowerAlgorithms.powerByDegreesOfTwo(number, degree);
    Assert.assertEquals(expectedResult, result, 0.0000001);
  }
}
