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
    Node node = head;
    int balanceFactor = getBalanceFactor(node);
    if (balanceFactor > 1 && key < node.leftNode.key) return rightRotation(node);

    if (balanceFactor < -1 && key > node.rightNode.key) return leftRotation(node);

    if (balanceFactor > 1 && key > node.leftNode.key) {
      node.leftNode = leftRotation(node.leftNode);
      return rightRotation(node);
    }

    if (balanceFactor < -1 && key < node.rightNode.key) {
      node.rightNode = rightRotation(node.rightNode);
      return leftRotation(node);
    }

    return result;
  }

  public int getBalanceFactor(Node node) {
    return null != node
        ? (null != node.leftNode ? node.leftNode.height : 0)
            - (null != node.rightNode ? node.rightNode.height : 0)
        : 0;
  }
}
