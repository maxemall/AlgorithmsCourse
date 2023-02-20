package org.algo.dinamicArrays;

public interface IArray<T> {
    int size();
    void add(T item);
    T get(int index);

    void add(T item, int index); // with shift to tail

    void shiftRight(int srcPos, int lenght);

    void replace(T item, int index);

    T remove(int index); // return deleted element
}
