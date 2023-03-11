package org.algo.sorting;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SortTest {

    long[] testArray;

    {
        try {
            testArray = BinaryFileReader.read("src/test/resources/sorting-tests/numbers.bin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void bucketSort100() {
        Sort.bucketSort(testArray, 100);
    }

    @Test
    public void bucketSort10() {
        Sort.bucketSort(testArray, 10);
    }

    @Test
    public void countSort() {
        Sort.countSort(testArray);
    }

    @Test
    public void radixSort() {
        Sort.radixSort(testArray);
    }
}
