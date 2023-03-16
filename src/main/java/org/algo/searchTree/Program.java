package org.algo.searchTree;

import java.util.Arrays;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Program {

  //тестирование dsds
  public static void main(String[] args) {
    test();

    int[] rnds = generateRandomArray(100_000);

    System.out.println("Дерево поиска");
    test2(new SearchTree(), rnds, "случайном");

    Arrays.sort(rnds);
    test2(new SearchTree(), rnds, "возрастающем");

    System.out.println("Дерево AVL");
    test2(new AVLTree(), rnds, "случайном");

    Arrays.sort(rnds);
    test2(new AVLTree(), rnds, "возрастающем");
  }

  private static void test2(SearchTree bst, int[] rnds, String order) {
    long start = System.currentTimeMillis();
    for (int rnd : rnds) {
      bst.insert(rnd, rnd);
    }
    long time = System.currentTimeMillis() - start;
    System.out.println("Заполнение массива в " + order + " порядке мс " + (time));

    start = System.currentTimeMillis();
    for (int i = 0; i < rnds.length; i += 10) {
      bst.search(rnds[i]);
    }
    time = System.currentTimeMillis() - start;
    System.out.println("Поиск в массиве в " + order + " порядке мс " + (time));

    start = System.currentTimeMillis();
    for (int i = 0; i < rnds.length; i += 10) {
      bst.search(rnds[i]);
    }
    time = System.currentTimeMillis() - start;
    System.out.println("Удаление в массиве в " + order + " порядке мс " + (time));
  }

  private static void test() {
    SearchTree bst = new AVLTree();
    int[] rnds = generateRandomArray(20);
    for (int rnd : rnds) {
      bst.insert(rnd, rnd);
    }
    bst.print();
//
//    System.out.println(Arrays.toString(bst.getSorted()));
//
//    System.out.println(bst.search(rnds[2]).key);
//    System.out.println(bst.search(rnds[5]).key);
//    System.out.println(bst.search(rnds[9]).key);
//
//    bst.remove(rnds[0]);
//    bst.remove(rnds[5]);
//    bst.remove(rnds[9]);
//
//    System.out.println(bst.search(rnds[0]));
//    System.out.println(bst.search(rnds[5]));
//    System.out.println(bst.search(rnds[9]));
//    System.out.println(Arrays.toString(bst.getSorted()));
  }

  public static int[] generateRandomArray(int size) {
    RandomGenerator randomGenerator = new Random(size);
    int[] array = new int[size];
    for (int i = 0; i < size; i++) {
      array[i] = Math.abs(randomGenerator.nextInt(500));
    }
    return array;
  }
}
