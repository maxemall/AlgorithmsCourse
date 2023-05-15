package org.algo.graphDemucron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KosarajuAlgorithm extends AdjacencyVector {

  public KosarajuAlgorithm(int[][] vector) {
    super(vector);
  }

  public KosarajuAlgorithm() {
    super();
  }

  public int[][] kosarajuAlgo() {
    int[][] transposedAdjacencyMatrix = transpose(adjacencyMatrix);
    int[] visited = new int[vector.length];
    List<Integer> order = new ArrayList<>();

    for (int i = 0; i < vector.length; i++) {
      if (visited[i] == 0) {
        dfsFirst(adjacencyMatrix, visited, i, order);
      }
    }

    Arrays.fill(visited, 0);
    List<List<Integer>> result = new ArrayList<>();

    for (int i = order.size() - 1; i >= 0; i--) {
      int v = order.get(i);
      if (visited[v] == 0) {
        List<Integer> subResult = new ArrayList<>();
        dfsSecond(transposedAdjacencyMatrix, visited, v, subResult);
        result.add(subResult);
      }
    }

    return result.stream()
        .map(sublist -> sublist.stream().mapToInt(Integer::intValue).toArray())
        .toArray(int[][]::new);
  }

  private int[][] transpose(int[][] matrix) {
    int[][] result = new int[matrix[0].length][matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        result[j][i] = matrix[i][j];
      }
    }
    return result;
  }

  private void dfsFirst(int[][] matrix, int[] visited, int v, List<Integer> order) {
    visited[v] = 1;
    for (int i = 0; i < matrix[v].length; i++) {
      if (matrix[v][i] == 1 && visited[i] == 0) {
        dfsFirst(matrix, visited, i, order);
      }
    }
    order.add(v);
  }

  private void dfsSecond(int[][] matrix, int[] visited, int v, List<Integer> subResult) {
    visited[v] = 1;
    subResult.add(v);
    for (int i = 0; i < matrix[v].length; i++) {
      if (matrix[v][i] == 1 && visited[i] == 0) {
        dfsSecond(matrix, visited, i, subResult);
      }
    }
  }
}
