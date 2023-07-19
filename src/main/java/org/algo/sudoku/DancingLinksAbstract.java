package org.algo.sudoku;

import java.util.Stack;

public abstract class DancingLinksAbstract<T extends Node<T>> implements DancingLinksInterface<T> {
  protected static final String DELIMITER = ";";
  protected static final String DASH = "-";
  protected T rootNode;
  private final Stack<T> deletedItems = new Stack<>();

  protected DancingLinksAbstract() {}

  @Override
  public T removeElement(T element) {
    return deletedItems.push(deleteElement(element));
  }

  @Override
  public T deleteElement(T element) {
    deleteLeftAndRight(element);
    deleteUpAndDown(element);
    return element;
  }

  @Override
  public T addElement(T element) {
    element.getLeft().setRight(element);
    element.getRight().setLeft(element);
    element.getUp().setDown(element);
    element.getDown().setUp(element);
    return element;
  }

  @Override
  public T restoreElement() {
    T element = deletedItems.pop();
    element.getLeft().setRight(element);
    element.getRight().setLeft(element);
    element.getUp().setDown(element);
    element.getDown().setUp(element);
    return null;
  }

  public T getRootNode() {
    return rootNode;
  }

  protected void deleteLeftAndRight(T element) {
    element.getLeft().setRight(element.getRight());
    element.getRight().setLeft(element.getLeft());
  }

  protected void deleteUpAndDown(T element) {
    element.getUp().setDown(element.getDown());
    element.getDown().setUp(element.getUp());
  }
}
