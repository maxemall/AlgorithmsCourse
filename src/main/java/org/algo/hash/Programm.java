package org.algo.hash;

import org.algo.searchTree.SearchTree;
import static org.algo.searchTree.Program.generateRandomArray;

public class Programm {
  public static void main(String[] args) {
    HashTable<Integer, String> hashTable = new HashTable<>(11);

    for (int i = 0; i < 20; i++) {
      hashTable.put(i, Integer.toString(i));
      hashTable.put(i + 11, Integer.toString(i));
    }

    System.out.println(hashTable.get(11));
    System.out.println(hashTable.get(99));
    hashTable.delete(11);
    System.out.println(hashTable.get(11));

    int[] rnds = generateRandomArray(1000_000);
    test2(new HashTable(11), rnds, "Произвольный");
  }

  private static void test2(HashTable hashTable, int[] rnds, String order) {
    long start = System.currentTimeMillis();
    for (int rnd : rnds) {
      hashTable.put(rnd, rnd);
    }
    long time = System.currentTimeMillis() - start;
    System.out.println("Заполнение массива в " + order + " порядке мс " + (time));

    start = System.currentTimeMillis();
    for (int i = 0; i < rnds.length; i += 10) {
      hashTable.get(rnds[i]);
    }
    time = System.currentTimeMillis() - start;
    System.out.println("Поиск в массиве в " + order + " порядке мс " + (time));

    start = System.currentTimeMillis();
    for (int i = 0; i < rnds.length; i += 10) {
      hashTable.delete(rnds[i]);
    }
    time = System.currentTimeMillis() - start;
    System.out.println("Удаление в массиве в " + order + " порядке мс " + (time));
  }
}
