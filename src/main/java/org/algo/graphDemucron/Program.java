package org.algo.graphDemucron;

import java.util.Arrays;

public class Program {
  public static void main(String[] args) {
    AdjacencyVector adjacencyVector = new AdjacencyVector();
    System.out.println(
        Arrays.toString(adjacencyVector.getInclinationDegree(adjacencyVector.adjacencyMatrix)));
    System.out.println(
            Arrays.deepToString(adjacencyVector.demukronAlgo()));
  }
}
