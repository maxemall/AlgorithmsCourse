package org.algo.searchTree;

public class AVLTree extends SearchTree {
  public Node rightRotation(Node node) {
    Node left = node.leftNode;
    Node central = left.rightNode;
    left.rightNode = node;
    node.leftNode = central;
    return updateParents(node, left, central);
  }

  private Node updateParents(Node node, Node child, Node central) {
    if (central != null) central.parentNode = node;
    child.parentNode = node.parentNode;
    node.parentNode = child;

    if (child.parentNode != null) {
      if (child.parentNode.rightNode == node) child.parentNode.rightNode = child;
      else child.parentNode.leftNode = child;
    }

    if (head == node) head = child;

    recalculateHeight(node);
    return child;
  }

  public Node leftRotation(Node node) {
    Node right = node.rightNode;
    Node central = right.leftNode;
    right.leftNode = node;
    node.rightNode = central;

    return updateParents(node, right, central);
  }

  @Override
  public Node insert(int key, Object value) {
    Node result = super.insert(key, value);
    recalculateHeight(result);
    rebalance(key, head);

    return result;
  }

  private void rebalance(int key, Node node) {
    int balanceFactor = getBalanceFactor(node);
    if (balanceFactor > 1 && key < (node.leftNode != null ? node.leftNode.key : 0)) rightRotation(node);

    if (balanceFactor < -1 && key > (node.rightNode != null ? node.rightNode.key : 0)) leftRotation(node);

    if (balanceFactor > 1 && key > (node.leftNode != null ? node.leftNode.key : 0)) {
      node.leftNode = leftRotation(node.leftNode);
      rightRotation(node);
    }

    if (balanceFactor < -1 && key < (node.rightNode != null ? node.rightNode.key : 0)) {
      node.rightNode = rightRotation(node.rightNode);
      leftRotation(node);
    }
  }

  @Override
  public Node remove(int key) {
    Node result = super.remove(key);
    if (result == null) return null;
    recalculateHeight(result.parentNode);
    rebalance(key, head);
    return result;
  }

  public int getBalanceFactor(Node node) {
    return null != node
        ? (null != node.leftNode ? node.leftNode.height : 0)
            - (null != node.rightNode ? node.rightNode.height : 0)
        : 0;
  }
}
