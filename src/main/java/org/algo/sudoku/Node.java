package org.algo.sudoku;

public interface Node<T extends Node> {

  T getLeft();

  void setLeft(T left);

  T getRight();

  void setRight(T right);

  T getDown();

  void setDown(T down);

  T getUp();

  void setUp(T up);

  void setHeader(boolean header);

  boolean isHeader();
}
