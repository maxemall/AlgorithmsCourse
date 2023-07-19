package org.algo.sudoku;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SudokuNode implements Node<SudokuNode> {
  private SudokuNode left;
  private SudokuNode right;
  private SudokuNode down;
  private SudokuNode up;
  private boolean value = false;
  private boolean isHeader = false;
  private int rowIndex;
  private int columnIndex;
  private int decisionValue;
  private int size;
  private boolean isChecked;

  public SudokuNode(
      SudokuNode left,
      SudokuNode right,
      SudokuNode down,
      SudokuNode up,
      boolean value,
      int rowIndex,
      int columnIndex,
      int decisionValue) {
    this(left, right, down, up, value, false);
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.decisionValue = decisionValue;
  }

  public SudokuNode(boolean isHeader) {
    this.isHeader = isHeader;
  }

  public SudokuNode(
      SudokuNode left,
      SudokuNode right,
      SudokuNode down,
      SudokuNode up,
      boolean value,
      boolean isHeader) {
    this.left = left;
    this.right = right;
    this.down = down;
    this.up = up;
    this.isHeader = isHeader;
    this.value = value;
  }

  public int increaseSize() {
    return ++size;
  }

  public int decreaseSize() {
    return --size;
  }

  public void resetSize() {
    size = 0;
  }
}
