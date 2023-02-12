package org.algo.algebraic;

import org.algo.HappyTicketTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class PrimeAlgorithmsTest {

    private final int maxSteps;
    private final int expectedResult;

    public PrimeAlgorithmsTest(int maxSteps, int expectedResult) {
        this.maxSteps = maxSteps;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws AssertionError {
        Collection<Object[]> data = new ArrayList<>();
        ClassLoader classLoader = HappyTicketTest.class.getClassLoader();
        for (int i = 0; i <= 14; i++) {
            String inputFile = classLoader.getResource("prime/test." + i + ".in").getFile();
            String outputFile = classLoader.getResource("prime/test." + i + ".out").getFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                int maxSteps = Integer.parseInt(reader.readLine().trim());
                try (BufferedReader reader2 = new BufferedReader(new FileReader(outputFile))) {
                    int expectedResult = Integer.parseInt(reader2.readLine().trim());
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
    public void primeNumbers() {
        int result = PrimeAlgorithms.primeNumbers(maxSteps);
        Assert.assertEquals(expectedResult, result);
    }
}
