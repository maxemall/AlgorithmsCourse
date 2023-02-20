package org.algo.dinamicArrays;

public class SingleArray<T> implements IArray<T> {

  private Object[] array;

  public SingleArray() {
    array = new Object[0];
  }

  @Override
  public int size() {
    return array.length;
  }

  @Override
  public void add(T item) {
    resize();
    array[size() - 1] = item;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T get(int index) {
    return (T) array[index];
  }

  @Override
  public void add(T item, int index) {
    Object[] newArray = new Object[size() + 1];
    System.arraycopy(array, 0, newArray, 0, index);
    newArray[index] = item;
    System.arraycopy(array, index, newArray, index + 1, size() - index);
    array = newArray;
  }

  @Override
  public T remove(int index) {
    T element = get(index);
    Object[] newArray = new Object[size() - 1];
    if ((size() - 1) == index)
      System.arraycopy(array, 0, newArray, 0, index);
      else
    System.arraycopy(array, index + 1, newArray, index, size() - index - 1);
    array = newArray;
    return element;
  }

  @Override
  public void shiftRight(int srcPos, int lenght) {
    if ((srcPos + lenght - 1) > size()) resize();
    System.arraycopy(array, srcPos, array, srcPos + 1, lenght);
  }

  @Override
  public void replace(T item, int index) {
    array[index] = item;
  }

  private void resize() {
    Object[] newArray = new Object[size() + 1];
    System.arraycopy(array, 0, newArray, 0, size());
    array = newArray;
  }
}
