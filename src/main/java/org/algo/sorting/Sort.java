package org.algo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.random.RandomGenerator;

public class Sort {
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  public static void main(String[] args) throws NoSuchMethodException {
    List<TestResults> testResults = new ArrayList<>();
    long[] array = new long[] {3, 1, 2, 4, 7, 0, 14, 9, 11, -1};
    for (int n = 10; n <= 1_000_000; n *= 10) {

      long[] randomArray = generateRandomArray(n);
      //      long[] selectionSort = compute(randomArray, Sort::selectionSort, testResults,
      // "selectionSort");
      //      long[] insertionSort = compute(randomArray, Sort::insertionSort, testResults,
      // "insertionSort");
      //      long[] bubbleSort = compute(randomArray, Sort::bubbleSort, testResults, "bubbleSort");
      //      long[] bubbleSortOptimized = compute(randomArray, Sort::bubbleSortOptimized,
      // testResults, "bubbleSortOptimized");
      //      long[] insertionSortShift = compute(randomArray, Sort::insertionSortShift,
      // testResults, "insertionSortShift");
      //      long[] heapSort = compute(randomArray, Sort::heapSort, testResults, "heapSort");
      //      long[] shellSort2 = compute(randomArray, 2, Sort::shellSort, testResults,
      // "shellSort2");
      //      long[] shellSort25 = compute(randomArray, 25, Sort::shellSort, testResults,
      // "shellSort25");
      long[] quickSort = compute(randomArray, Sort::quickSort, testResults, "quickSort");
      long[] mergeSort = compute(randomArray, Sort::mergeSort, testResults, "mergeSort");
    }
    System.out.println(testResults);
  }

  public static long[] compute(
      long[] value,
      Function<long[], long[]> function,
      List<TestResults> testResults,
      String methodName) {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<long[]> future = executor.submit(() -> function.apply(value));
    long start = System.currentTimeMillis();
    long[] result = new long[0];
    try {
      result = future.get(2, TimeUnit.MINUTES); // wait for 2 minutes
      long time = System.currentTimeMillis() - start;
      testResults.add(new TestResults(methodName, value.length, time));
    } catch (TimeoutException e) {
      future.cancel(true); // cancel the future if it's still running
      testResults.add(new TestResults(methodName, value.length, 99999999L));
    } catch (Exception e) {
      testResults.add(new TestResults(methodName, value.length, 99999999L));
    } finally {
      executor.shutdownNow(); // shut down the executor
    }
    return result;
  }

  public static long[] compute(
      long[] value,
      int gap,
      BiFunction<long[], Integer, long[]> function,
      List<TestResults> testResults,
      String methodName) {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<long[]> future = executor.submit(() -> function.apply(value, gap));
    long start = System.currentTimeMillis();
    long[] result = new long[0];
    ;
    try {
      result = future.get(2, TimeUnit.MINUTES); // wait for 2 minutes
      long time = System.currentTimeMillis() - start;
      testResults.add(new TestResults(methodName, value.length, time));
    } catch (TimeoutException e) {
      future.cancel(true); // cancel the future if it's still running
      testResults.add(new TestResults(methodName, value.length, 99999999L));
    } catch (Exception e) {
      testResults.add(new TestResults(methodName, value.length, 99999999L));
    } finally {
      executor.shutdownNow(); // shut down the executor
    }
    return result;
  }

  public static long[] generateRandomArray(int size) {
    RandomGenerator randomGenerator = new Random();
    return randomGenerator.longs(size).toArray();
  }

  public static long[] bubbleSort(long[] array) {
    for (int j = array.length - 1; j > 0; j--) {
      for (int i = 0; i < j; i++) {
        if (array[i] > array[i + 1]) swap(array, i, i + 1);
      }
    }
    return array;
  }

  public static long[] bubbleSortOptimized(long[] array) {
    boolean swapped = true;
    int j = 0;
    while (swapped) {
      swapped = false;
      j++;
      for (int i = 0; i < array.length - j; i++) {
        if (array[i] > array[i + 1]) {
          swap(array, i, i + 1);
          swapped = true;
        }
      }
    }
    return array;
  }

  public static long[] selectionSort(long[] array) {
    for (int j = array.length - 1; j > 0; j--) {
      swap(array, findMax(array, j), j);
    }
    return array;
  }

  public static long[] insertionSort(long[] array) {
    for (int j = 0; j < array.length - 1; j++) {
      for (int i = j - 1; i > 0; i--) {
        if (array[i] < array[i - 1]) swap(array, i, i - 1);
      }
    }
    return array;
  }

  public static long[] insertionSortShift(long[] array) {
    int i;
    for (int j = 0; j < array.length - 1; j++) {
      long k = array[j];
      for (i = j - 1; i > 0; i--) {
        if (array[i] > array[i + 1]) array[i] = array[i + 1];
      }
      array[i + 1] = k;
    }
    return array;
  }

  public static long[] shellSort(long[] array, int gap) {
    for (int g = array.length / 2; g > 0; g /= 2) {
      for (int j = 0; j < array.length - 1; j++) {
        for (int i = j; i > g; i -= g) {
          if (array[i] < array[i - 1]) swap(array, i, i - 1);
        }
      }
    }
    return array;
  }

  public static long[] heapSort(long[] array) {
    for (int h = array.length / 2 - 1; h >= 0; h--) {
      heapify(array, h, array.length);
    }
    for (int j = array.length - 1; j > 0; j--) {
      swap(array, 0, j);
      heapify(array, 0, j);
    }
    return array;
  }

  public static long[] mergeSort(long[] array) {
    mergeSort(array, 0, array.length - 1);
    return array;
  }

  private static void mergeSort(long[] array, int left, int right) {
    if (left >= right) return;
    int middle = (left + right) / 2;
    mergeSort(array, left, middle);
    mergeSort(array, middle + 1, right);
    merge(array, left, middle, right);
  }

  private static void merge(long[] array, int left, int middle, int right) {
    int leftSize = middle - left + 1;
    int rightSize = right - middle;

    long[] tempArray = new long[right - left + 1];
    int[] rightTempArray = new int[rightSize];

    int leftCnt = left;
    int rightCnt = middle + 1;
    int index = 0;

    while (leftCnt <= middle && rightCnt <= right) {
      if (array[leftCnt] <= array[rightCnt]) tempArray[index++] = array[leftCnt++];
      else tempArray[index++] = array[rightCnt++];
    }

    while (leftCnt <= middle) tempArray[index++] = array[leftCnt++];
    while (rightCnt <= right) tempArray[index++] = array[rightCnt++];

    index = 0;
    for (int i = left; i <= right ; i++) {
      array[i] = tempArray[index++];
    }
  }

  public static long[] quickSort(long[] array) {
    quickSort(array, 0, array.length - 1);
    return array;
  }

  private static long[] quickSort(long[] array, int left, int right) {
    if (left >= right) return array;
    int middle = split(array, left, right);
    quickSort(array, left, middle - 1);
    quickSort(array, middle + 1, right);
    return array;
  }

  private static int split(long[] array, int left, int right) {
    long pivot = array[right];
    int i = left - 1;
    for (int j = left; j < right; j++) {
      if (array[j] < pivot) swap(array, ++i, j);
    }
    swap(array, i + 1, right);
    return i;
  }

  private static void heapify(long[] array, int rootIndex, int length) {
    int maxIndex = rootIndex;
    int leftBranchIndex = rootIndex * 2 + 1;
    int rightBranchIndex = rootIndex * 2 + 2;
    if (leftBranchIndex < length && array[leftBranchIndex] > array[rootIndex])
      maxIndex = leftBranchIndex;
    if (rightBranchIndex < length && array[rightBranchIndex] > array[rootIndex])
      maxIndex = rightBranchIndex;
    if (maxIndex == rootIndex) return;
    swap(array, rootIndex, maxIndex);
    heapify(array, maxIndex, length);
  }

  private static void swap(long[] array, int i, int j) {
    long buffer = array[j];
    array[j] = array[i];
    array[i] = buffer;
  }

  private static int findMax(long[] array, int j) {
    int maxIndex = 0;
    for (int i = 1; i < j; i++) {
      if (array[i] > array[maxIndex]) {
        maxIndex = i;
      }
    }
    return maxIndex;
  }

  public static class TestResults {
    public String algorithmName;
    public int cntElements;
    public long elapsedTime;

    public TestResults(String algorithmName, int cntElements, long elapsedTime) {
      this.algorithmName = algorithmName;
      this.cntElements = cntElements;
      this.elapsedTime = elapsedTime;
    }

    @Override
    public String toString() {
      return "algorithmName='"
          + algorithmName
          + '\''
          + ", cntElements="
          + cntElements
          + ", elapsedTime="
          + elapsedTime
          + '\n';
    }
  }
}
