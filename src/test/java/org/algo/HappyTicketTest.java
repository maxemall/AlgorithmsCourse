package org.algo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;


@RunWith(Parameterized.class)
public class HappyTicketTest {

    private final int maxSteps;
    private final long expectedResult;

    public HappyTicketTest(int maxSteps, long expectedResult) {
        this.maxSteps = maxSteps;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws AssertionError {
        Collection<Object[]> data = new ArrayList<>();
        ClassLoader classLoader = HappyTicketTest.class.getClassLoader();
        for (int i = 0; i <= 9; i++) {
            String inputFile = classLoader.getResource("happyTickets/test." + i + ".in").getFile();
            String outputFile = classLoader.getResource("happyTickets/test." + i + ".out").getFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                int maxSteps = Integer.parseInt(reader.readLine().trim());
                try (BufferedReader reader2 = new BufferedReader(new FileReader(outputFile))) {
                    long expectedResult = Long.parseLong(reader2.readLine().trim());
                    data.add(new Object[]{maxSteps, expectedResult});
                }
            } catch (Exception e) {
                System.out.println("Error while reading file: " + e.getMessage());
                throw new AssertionError(e);
            }
        }
        return data;
    }

    @Test
    public void testCountVariants() {
        long[] sumArray = new long[1];
        sumArray[0] = 1;
        long result = HappyTicket.countVariants(sumArray, 1, maxSteps);
        Assert.assertEquals(expectedResult, result);
    }
}
