package org.algo.sudoku;

public class Main {
  public static void main(String[] args) {
//    DancingLinksSudokuImpl dancingLinks = new DancingLinksSudokuImpl(10);
//
//    System.out.println(dancingLinks.toString());
//
//    dancingLinks.fillFull(5);
//    System.out.println(dancingLinks.toString());

    int[][] matrix2 = new int[][] {{2, 0, 0, 0}, {1, 0, 2, 0}, {0, 0, 3, 0}, {0, 0, 0, 4}};
    int[][] matrix3 = new int[][]{
            {0, 5, 0, 0, 1, 0, 0, 8, 0},
            {6, 0, 0, 0, 0, 0, 0, 0, 2},
            {0, 0, 0, 5, 0, 3, 0, 0, 0},
            {3, 0, 1, 0, 0, 0, 2, 0, 9},
            {0, 0, 0, 0, 7, 0, 0, 0, 0},
            {9, 0, 0, 3, 0, 1, 0, 0, 7},
            {0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 4, 8, 0, 0, 0, 5, 9, 0},
            {0, 0, 0, 2, 0, 4, 0, 0, 0}};

    int[][] matrix4 = new int[][]{
            {1, 0, 0, 5, 0, 0, 0, 0, 4},
            {0, 4, 0, 2, 9, 3, 0, 6, 0},
            {0, 0, 9, 0, 0, 4, 2, 0, 0},
            {0, 8, 5, 0, 4, 0, 0, 1, 3},
            {0, 6, 0, 3, 0, 2, 0, 4, 0},
            {4, 9, 0, 0, 6, 0, 7, 5, 0},
            {0, 0, 6, 4, 0, 0, 5, 0, 0},
            {0, 5, 0, 6, 2, 1, 0, 8, 0},
            {3, 0, 0, 0, 0, 5, 0, 0, 6}};
    SudokuSolver sudokuSolver = new SudokuSolver();
    sudokuSolver.solve(matrix3);

  }
}
