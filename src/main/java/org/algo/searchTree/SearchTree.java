package org.algo.searchTree;

public class SearchTree {
  Node head;
  int size;

  public int[] getSorted() {
    int[] array = new int[this.size];
    int[] counter = new int[] {0};
    return getSorted(head, array, counter);
  }

  protected void recalculateHeight(Node node) {
    if (node == null) return;
    node.height =
        Math.max(
                null != node.leftNode ? node.leftNode.height : 0,
                null != node.rightNode ? node.rightNode.height : 0)
            + 1;
    recalculateHeight(node.parentNode);
  }

  private int[] getSorted(Node node, int[] array, int[] pointer) {
    if (node == null) return array;
    getSorted(node.leftNode, array, pointer);
    array[pointer[0]++] = node.key;
    getSorted(node.rightNode, array, pointer);
    return array;
  }


  public Node insert(int key, Object value) {
    Node currentNode = head;
    Node parentNode = null;
    boolean rightNode = false;
    this.size++;

    if (currentNode == null) {
      head = new Node(key, value, null);
      return head;
    }

    while (currentNode != null) {
      parentNode = currentNode;
      if (currentNode.key > key) {
        currentNode = currentNode.leftNode;
        rightNode = false;
      } else {
        currentNode = currentNode.rightNode;
        rightNode = true;
      }
    }

    currentNode = new Node(key, value, parentNode);
    if (rightNode) parentNode.rightNode = currentNode;
    else parentNode.leftNode = currentNode;
    recalculateHeight(currentNode);
    return currentNode;
  }

  public Node search(int key) {
    Node currentNode = head;

    while (currentNode != null && currentNode.key != key) {
      if (currentNode.key > key) currentNode = currentNode.leftNode;
      else currentNode = currentNode.rightNode;
    }

    return currentNode;
  }

  public Node remove(int key) {
    Node deleteNode = search(key);

    if (deleteNode == null) {
      return null;
    }

    boolean isRoot = deleteNode.parentNode == null;

    this.size--;

    // два дочерних
    if (deleteNode.leftNode != null && deleteNode.rightNode != null) {
      // поиск максимума в левой ветви
      Node currentNode = deleteNode.leftNode;
      while (currentNode.rightNode != null) {
        currentNode = currentNode.rightNode;
      }
      int keyDelete = deleteNode.key;
      Object valueDelete = deleteNode.value;
      deleteNode.key = currentNode.key;
      deleteNode.value = currentNode.value;
      currentNode.value = valueDelete;
      currentNode.key = keyDelete;
      deleteNode = currentNode;
    }

    boolean isParentsRight = (isRoot || deleteNode.parentNode.rightNode == deleteNode);

    // лист - нет дочерних
    if (deleteNode.leftNode == null && deleteNode.rightNode == null) {
      if (isRoot) {
        head = null;
        return deleteNode;
      }
      if (isParentsRight) deleteNode.parentNode.rightNode = null;
      else deleteNode.parentNode.leftNode = null;
      return deleteNode;
    }

    // один дочерний слева
    if (deleteNode.leftNode != null) {
      if (isRoot) {
        head = deleteNode.leftNode;
        head.parentNode = null;
        return deleteNode;
      }
      if (isParentsRight) {
        deleteNode.parentNode.rightNode = deleteNode.leftNode;
      } else deleteNode.parentNode.leftNode = deleteNode.leftNode;

      deleteNode.leftNode.parentNode = deleteNode.parentNode;
      return deleteNode;
    }

    // один дочерний справа
    if (deleteNode.rightNode != null) {
      if (isRoot) {
        head = deleteNode.rightNode;
        head.parentNode = null;
        return head;
      }
      if (isParentsRight) {
        deleteNode.parentNode.rightNode = deleteNode.rightNode;
      } else deleteNode.parentNode.leftNode = deleteNode.rightNode;

      deleteNode.rightNode.parentNode = deleteNode.parentNode;
    }
    return deleteNode;
  }

  public void print() {
    print(this.head, "", true);
  }

  private void print(Node node, String prefix, boolean isLeft) {
    if (node != null) {
      System.out.print(prefix);
      System.out.print(isLeft ? "├──L " : "└──R ");
      System.out.println(node.key);

      print(node.leftNode, prefix + (isLeft ? "│   " : "    "), true);
      print(node.rightNode, prefix + (isLeft ? "│   " : "    "), false);
    }
  }
}
