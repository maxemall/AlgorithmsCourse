package org.algo.graphDemucron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class AdjacencyVector {
  int[][] vector;
  int[][] adjacencyMatrix;

  public AdjacencyVector(int[][] vector) {
    this.vector = vector;
    adjacencyMatrix = getAdjacencyMatrix();
  }

  public AdjacencyVector() {
    this(
        new int[][] {
          {1, -1, -1, -1},
          {4, -1, -1, -1},
          {3, -1, -1, -1},
          {0, 1, 4, 5},
          {6, -1, -1, -1},
          {7, -1, -1, -1},
          {7, -1, -1, -1},
          {-1, -1, -1, -1}
        });
  }

  private int[][] getAdjacencyMatrix() {
    int[][] result = new int[vector.length][vector.length];
    for (int i = vector.length - 1; i >= 0; i--) {
      for (int j = 0; j < vector[i].length; j++) {
        if (vector[i][j] < 0) break;
        result[i][vector[i][j]] = 1;
      }
    }
    return result;
  }

  public int[] getInclinationDegree(int[][] matrix) {
    return IntStream.range(0, matrix[0].length)
        .map(colIndex -> Arrays.stream(matrix).mapToInt(row -> row[colIndex]).sum())
        .toArray();
  }

  public int[][] demukronAlgo() {
    int[] inclinationDegree = getInclinationDegree(adjacencyMatrix);
    List<List<Integer>> result = new ArrayList<>();

    while (true) {
      List<Integer> subResult = new ArrayList<>();
      boolean isCircle = true;
      boolean allCounted = Arrays.stream(inclinationDegree).noneMatch(value -> value >= 0);
      if (allCounted) break;
      for (int i = 0; i < inclinationDegree.length; i++) {
        if (inclinationDegree[i] == 0) {
          subResult.add(i);
          inclinationDegree[i]--;
          isCircle = false;
        }
      }
      subResult.forEach(
          integer ->
          {for (int i = 0; i < adjacencyMatrix[integer].length; i++) {
            inclinationDegree[i] = inclinationDegree[i] - adjacencyMatrix[integer][i];
          }});
      if (isCircle) throw new RuntimeException("Is circle");
      result.add(subResult);
    }

    return result.stream()
        .map(sublist -> sublist.stream().mapToInt(Integer::intValue).toArray())
        .toArray(int[][]::new);
  }
}
