package org.algo.searchTree;
public class Node {
  int key;
  Object value;
  Node leftNode;
  Node rightNode;
  int height;
  Node parentNode;

  public Node(int key, Object value, Node leftNode, Node rightNode, int height) {
    this.key = key;
    this.value = value;
    this.leftNode = leftNode;
    this.rightNode = rightNode;
    this.height = height;
  }

  public Node(int key, Object value, Node parentNode) {
    this.key = key;
    this.value = value;
    this.parentNode = parentNode;
    this.leftNode = null;
    this.rightNode = null;
  }
}
