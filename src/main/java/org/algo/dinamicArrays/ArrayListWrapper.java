package org.algo.dinamicArrays;

import java.util.ArrayList;

public class ArrayListWrapper<T> implements IArray<T> {

  private ArrayList<T> arrayList;

  public ArrayListWrapper(int initialCapacity) {
    arrayList = new ArrayList<>(initialCapacity);
  }

  public ArrayListWrapper() {
    this.arrayList = new ArrayList<>();
  }

  @Override
  public int size() {
    return arrayList.size();
  }

  @Override
  public void add(T item) {
    arrayList.add(item);
  }

  @Override
  public T get(int index) {
    return arrayList.get(index);
  }

  @Override
  public void add(T item, int index) {
    arrayList.add(index, item);
  }

  @Override
  public void shiftRight(int srcPos, int lenght) {}

  @Override
  public void replace(Object item, int index) {}

  @Override
  public T remove(int index) {
    return arrayList.remove(index);
  }
}
