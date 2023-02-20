package org.algo.dinamicArrays;

public class FactorArray<T> implements IArray<T> {

  private Object[] array;
  private int factor;
  private int size;

  public FactorArray(int factor, int initLength) {
    this.factor = factor;
    array = new Object[initLength];
    size = 0;
  }

  public FactorArray() {
    this(50, 10);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void add(T item) {
    if (size() == array.length) resize();
    array[size] = item;
    size++;
  }

  @Override
  public void add(T item, int index) {
    if (size() == array.length) resize();
    System.arraycopy(array, index, array, index + 1, size() - index);
    array[index] = item;
    size++;
  }

  @Override
  public void shiftRight(int srcPos, int lenght) {
    if ((srcPos + lenght - 1) > size()) resize();
    System.arraycopy(array, srcPos, array, srcPos + 1, lenght);
    size++;
  }

  @Override
  public void replace(T item, int index) {
    array[index] = item;
  }

  @Override
  public T remove(int index) {
    T element = get(index);
    if ((size() - 1) == index)
      System.arraycopy(array, 0, array, 0, index);
    else
      System.arraycopy(array, index + 1, array, index, size() - index - 1);
    size--;
    return element;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T get(int index) {
    return (T) array[index];
  }

  private void resize() {
    Object[] newArray = new Object[array.length + array.length * factor / 100];
    System.arraycopy(array, 0, newArray, 0, array.length);
    array = newArray;
  }
}
