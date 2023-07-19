package org.algo.sudoku;

import java.util.Arrays;
import java.util.Stack;

public class SudokuSolver {
  public int[][] solve(int[][] matrix) {
    DancingLinksSudokuImpl dancingLinks = prepareDancingLinks(matrix);
    printMatrix(matrix);
    SudokuNode headerNode;
    SudokuNode currentNode;
    while (!(headerNode = dancingLinks.searchNextHeader()).equals(dancingLinks.getRootNode())) {
      while ((currentNode = dancingLinks.searchNextNode(headerNode)) != null) {
        dancingLinks.removeIntersectionOfElement(currentNode);
        Stack<Stack<SudokuNode>> deletedLists = dancingLinks.getDeletedLists();
        while (!deletedLists.isEmpty()) {
          SudokuNode resultElement = deletedLists.pop().pop();
          matrix[resultElement.getRowIndex()][resultElement.getColumnIndex()] =
              resultElement.getDecisionValue();
        }

        printMatrix(matrix);
      }
    }

    printMatrix(matrix);
    return matrix;
  }

  private void printMatrix(int[][] matrix) {
    Arrays.stream(matrix)
        .forEach(
            ints -> {
              Arrays.stream(ints).forEach(value -> System.out.print(value + " "));
              System.out.println();
            });
    System.out.println();
  }

  private DancingLinksSudokuImpl prepareDancingLinks(int[][] matrix) {
    int size = checkMatrix(matrix);
    DancingLinksSudokuImpl dancingLinks = new DancingLinksSudokuImpl(size * size * 4);
    dancingLinks.fillFullWithEmpty(size);
    fillCellsCoverage(dancingLinks, size);
    fillRowsCoverage(dancingLinks, size);
    fillColsCoverage(dancingLinks, size);
    fillSquareCoverage(dancingLinks, size);
    dancingLinks.clearEmpty();
    dancingLinks.calculateSize();
    printMatrix(matrix);
    clearKnownValues(dancingLinks, matrix);
    return dancingLinks;
  }

  private void clearKnownValues(DancingLinksSudokuImpl dancingLinks, int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int i1 = 0; i1 < matrix[i].length; i1++) {
        if (matrix[i][i1] > 0) {
          dancingLinks.removeIntersectionOfElement(i, i1, matrix[i][i1]);
        }
      }
    }
  }

  private void fillCellsCoverage(DancingLinksSudokuImpl dancingLinks, int size) {
    SudokuNode currentNode = dancingLinks.getRootNode();
    int width = size * size;

    for (int h = 0; h < width; h++) {
      currentNode = currentNode.getRight();
      for (int i = 0; i < size; i++) {
        currentNode = currentNode.getDown();
        currentNode.setValue(true);
      }
    }
  }

  private void fillRowsCoverage(DancingLinksSudokuImpl dancingLinks, int size) {
    SudokuNode currentNode = dancingLinks.getRootNode();
    for (int i = 0; i < size * size; i++) {
      currentNode = currentNode.getRight();
    }

    for (int i = 0; i < size; i++) {
      for (int c = 0; c < size; c++) {
        for (int i1 = 0; i1 < size; i1++) {
          currentNode = currentNode.getRight().getDown();
          currentNode.setValue(true);
        }
        for (int i1 = 0; i1 < size && c < size - 1; i1++) {
          currentNode = currentNode.getLeft();
        }
      }
    }
  }

  private void fillSquareCoverage(DancingLinksSudokuImpl dancingLinks, int size) {
    SudokuNode currentNode = dancingLinks.getRootNode();
    for (int i = 0; i < size * size * 3; i++) {
      currentNode = currentNode.getRight();
    }
    int sqrtSize = (int) Math.sqrt(size);

    for (int i = 0; i < size; i++) {
      for (int c = 0; c < size; c++) {
        for (int i1 = 0; i1 < size; i1++) {
          currentNode = currentNode.getRight().getDown();
          currentNode.setValue(true);
        }
        for (int i1 = 0; i1 < size && (c + 1) % sqrtSize != 0; i1++) {
          currentNode = currentNode.getLeft();
        }
      }
      for (int i1 = 0; i1 < size * sqrtSize && (i + 1) % sqrtSize != 0; i1++) {
        currentNode = currentNode.getLeft();
      }
    }
  }

  private void fillColsCoverage(DancingLinksSudokuImpl dancingLinks, int size) {
    SudokuNode currentNode = dancingLinks.getRootNode();
    int width = size * size;
    for (int i = 0; i < size * size * 2; i++) {
      currentNode = currentNode.getRight();
    }
    for (int i = 0; i < size; i++) {
      for (int h = 0; h < width; h++) {
        currentNode = currentNode.getRight();
        currentNode = currentNode.getDown();
        currentNode.setValue(true);
      }
      for (int i1 = 0; i1 < size * size; i1++) {
        currentNode = currentNode.getLeft();
      }
    }
  }

  private int checkMatrix(int[][] matrix) {
    if (matrix.length != matrix[0].length)
      throw new RuntimeException(
          "Матрица не имеет квадратную форму, ее размер - "
              + matrix.length
              + "X"
              + matrix[0].length);
    if ((Math.sqrt(matrix.length) % 1) != 0)
      throw new RuntimeException(
          "Матрица имеет сторону " + matrix.length + "не имеющую целочисленного корня");
    return matrix.length;
  }
}
