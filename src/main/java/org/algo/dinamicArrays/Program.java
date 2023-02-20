package org.algo.dinamicArrays;

public class Program {

  public static void main(String[] args) {
    IArray<Integer> singleArray = new SingleArray<>();
    IArray<Integer> vectorArray = new VectorArray<>();
    IArray<Integer> factorArray = new FactorArray<>();
    IArray<Integer> matrixArray = new MatrixArray<>();
    IArray<Integer> arrayList = new ArrayListWrapper<>();
    testAddArray(singleArray, 100_000);
    testAddArray(vectorArray, 100_000);
    testAddArray(factorArray, 10_000_000);
    testAddArray(matrixArray, 1_000_000);
    testAddArray(arrayList, 10_000_000);

    testAddElement(singleArray, 100_000);
    testAddElement(vectorArray, 100_000);
    testAddElement(factorArray, 100_000);
    testAddElement(matrixArray, 100_000);
    testAddElement(arrayList, 100_000);

    testRemoveElement(singleArray, 100_000);
    testRemoveElement(vectorArray, 100_000);
    testRemoveElement(factorArray, 100_000);
    testRemoveElement(matrixArray, 100_000);
    testRemoveElement(arrayList, 100_000);
  }

  private static long testAddArray(IArray data, int total) {
    long start = System.currentTimeMillis();

    for (int j = 0; j < total; j++) data.add(j);
    long time = System.currentTimeMillis() - start;

    System.out.println(data + " testAddArray: " + (time));
    return time;
  }

  private static long testAddElement(IArray data, int total) {
    long start = System.currentTimeMillis();

    for (int j = 0; j < total; j = j + 10) data.add(j, j);
    long time = System.currentTimeMillis() - start;

    System.out.println(data.getClass() + " testAddArray: " + (time));
    return time;
  }

  private static long testRemoveElement(IArray data, int total) {
    long start = System.currentTimeMillis();

    for (int j = 0; j < total; j = j + 10) data.remove(j);
    long time = System.currentTimeMillis() - start;

    System.out.println(data.getClass() + " testAddArray: " + (time));
    return time;
  }
}
