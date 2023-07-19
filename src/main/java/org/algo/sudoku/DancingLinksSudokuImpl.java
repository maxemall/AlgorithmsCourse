package org.algo.sudoku;

import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

@Getter
public class DancingLinksSudokuImpl extends DancingLinksAbstract<SudokuNode> {
  private int width;

  private final Stack<Stack<SudokuNode>> deletedLists = new Stack<>();

  /**
   * Create headers row for DLX
   *
   * @param width quantity of columns exclude root
   */
  public DancingLinksSudokuImpl(int width) {
    rootNode = new SudokuNode(true);
    rootNode.setLeft(rootNode);
    rootNode.setRight(rootNode);
    SudokuNode currentNode = rootNode;
    for (int i = 0; i < width; i++) {
      SudokuNode newNode = new SudokuNode(currentNode, rootNode, null, null, false, true);
      currentNode.setRight(newNode);
      rootNode.setLeft(newNode);
      newNode.setDown(newNode);
      newNode.setUp(newNode);
      currentNode = newNode;
    }
    this.width = width;
  }

  public SudokuNode searchNextHeader() {
    SudokuNode current = rootNode;
    SudokuNode resultNode = current.getRight();
    while (!current.getRight().equals(rootNode)) {
      current = current.getRight();
      if (current.getSize() == 0) throw new RuntimeException("Нет решений");
      if (current.getSize() == 1) return current;
      if (resultNode.getSize() > current.getSize()) resultNode = current;
    }
    return resultNode;
  }

  public SudokuNode searchNextNode(SudokuNode headerNode) {
    SudokuNode currentNode = headerNode;
    while (!currentNode.getDown().equals(headerNode)) {
      currentNode = currentNode.getDown();
      if (!currentNode.isChecked()) {
        currentNode.setChecked(true);
        return currentNode;
      }
    }
    return null;
  }

  public SudokuNode removeIntersectionOfElement(int rowIndex, int columnIndex, int value) {
    SudokuNode headerNode = rootNode;
    SudokuNode currentNode = headerNode;
    SudokuNode resultNode = null;
    while (!headerNode.getRight().equals(rootNode)) {
      headerNode = headerNode.getRight();
      currentNode = headerNode;
      while (!currentNode.getDown().equals(headerNode)) {
        currentNode = currentNode.getDown();
        if (currentNode.getDecisionValue() == value
            && currentNode.getRowIndex() == rowIndex
            && currentNode.getColumnIndex() == columnIndex) {
          resultNode = removeIntersectionOfElement(currentNode);
          // очищаем стек, так как метод используется для поиска известных значений
          //          deletedLists.clear();
          return resultNode;
        }
      }
    }
    // очищаем стек, так как метод используется для поиска известных значений
    //    deletedLists.clear();
    return resultNode;
  }

  public SudokuNode removeIntersectionOfElement(SudokuNode element) {
    Queue<SudokuNode> localQueue = new LinkedList<>() {};
    SudokuNode firstRowElement = element;
    //    debug();

    while (true) {
      SudokuNode currentColumnElement = firstRowElement = firstRowElement.getRight();
      SudokuNode header = null;
      while (true) {
        SudokuNode finalRowElement = currentColumnElement = currentColumnElement.getDown();
        if (currentColumnElement.isHeader()) {
          // хедер удалим в конце итерации
          header = currentColumnElement;
          continue;
        }
        while (true) {
          // один столбец
          finalRowElement = finalRowElement.getRight();
          localQueue.add(finalRowElement);
          if (finalRowElement.equals(currentColumnElement)
              || finalRowElement.getRight().equals(finalRowElement)) break;
        }
        if (currentColumnElement.equals(firstRowElement)) break;
      }
      if (header != null) localQueue.add(header);
      if (firstRowElement.equals(element)) break;
    }

    localQueue.add(element);

    Stack<SudokuNode> localStack = new Stack<>();
    while (!localQueue.isEmpty()) {
      localStack.push(removeElement(localQueue.poll()));
    }

    deletedLists.push(localStack);
    this.calculateSize();
    return element;
  }

  public void calculateSize() {
    SudokuNode currentNode = rootNode;
    SudokuNode headerNode = currentNode;

    while (!headerNode.getRight().equals(rootNode)) {
      currentNode = currentNode.getRight();
      headerNode = headerNode.getRight();
      headerNode.resetSize();
      //      // фикс проблемы с удалением хедеров, надо найти.
      //      if (headerNode.getDown().equals(headerNode)) {
      //        removeElement(headerNode);
      //        return;
      //      }
      while (!currentNode.getDown().equals(headerNode)) {
        currentNode = currentNode.getDown();
        headerNode.increaseSize();
      }
      currentNode = currentNode.getDown();
    }
  }

  public void fillFullWithEmpty(int size) {
    int deep = size * size * size;
    for (int i = 0; i < deep; i++) {
      SudokuNode headerNode = rootNode;
      SudokuNode previousLeft = null;
      int rowIndex = i / (size * size);
      int decisionValue = 1 + i % size;
      int columnIndex = (i - ((size * size) * (i / (size * size)))) / size;
      while (!headerNode.getRight().equals(rootNode)) {
        headerNode = headerNode.getRight();
        SudokuNode newNode =
            new SudokuNode(
                null,
                null,
                headerNode,
                headerNode.getUp(),
                false,
                rowIndex,
                columnIndex,
                decisionValue);
        newNode.setLeft(previousLeft == null ? newNode : previousLeft);
        newNode.setRight(previousLeft == null ? newNode : previousLeft.getRight());
        newNode = addElement(newNode);
        previousLeft = newNode;
      }
    }
  }

  public void clearEmpty() {
    SudokuNode currentNode = rootNode;
    while (!currentNode.getRight().equals(rootNode)) {
      currentNode = currentNode.getRight();
      while (!currentNode.getDown().isHeader()) {
        currentNode = currentNode.getDown();
        if (!(currentNode.isHeader() || currentNode.isValue())) {
          this.removeElement(currentNode);
        }
      }
      currentNode = currentNode.getDown();
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    SudokuNode headerNode = rootNode;
    int columnNum = 1;
    while (!headerNode.getRight().equals(rootNode)) {
      sb.append("H").append(columnNum++).append(DELIMITER);
      headerNode = headerNode.getRight();
      SudokuNode currentNode = headerNode;
      while (!currentNode.getDown().equals(headerNode)) {
        currentNode = currentNode.getDown();
        sb.append(currentNode.getRowIndex())
            .append(DASH)
            .append(currentNode.getColumnIndex())
            .append(DASH)
            .append(currentNode.getDecisionValue())
            .append(DELIMITER);
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  public void debug() {
    StringBuilder sb = new StringBuilder();
    SudokuNode headerNode = rootNode;
    int columnNum = 1;
    while (!headerNode.getRight().equals(rootNode)) {
      sb.append("H").append(columnNum++).append(DELIMITER);
      headerNode = headerNode.getRight();
      SudokuNode currentNode = headerNode;
      while (!currentNode.getDown().equals(headerNode)) {
        currentNode = currentNode.getDown();
        sb.append(System.identityHashCode(currentNode)).append(DELIMITER);
      }
      sb.append("\n");
    }

    System.out.println(sb);
  }

  public int getWidth() {
    return width;
  }
}
