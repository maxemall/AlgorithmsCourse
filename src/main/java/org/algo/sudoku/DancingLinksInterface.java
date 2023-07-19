package org.algo.sudoku;


public interface DancingLinksInterface<T extends Node<T>> {
    public T removeElement(T element);

    T deleteElement(T element);

    public T addElement(T element);
    public T restoreElement();

}
