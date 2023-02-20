package org.algo.dinamicArrays;

public class MatrixArray<T> implements IArray<T> {

  private int size;
  private int vector;
  private IArray<IArray<T>> array;

  public MatrixArray(int vector) {
    this.vector = vector;
    array = new SingleArray<>();
    size = 0;
  }

  public MatrixArray() {
    this(10);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void add(T item) {
    if (size == array.size() * vector) array.add(new VectorArray<T>(vector));
    array.get(size / vector).add(item);
    size++;
  }

  @Override
  public void add(T item, int index) {
    // сдвигаем последний элемент
    add(get(size - 1));
    shiftRight(index, size - index);
    array.get(index / vector).replace(item, (index % vector));
  }

  @Override
  public T remove(int index) {
    T element = get(index);
    // вектор в который попадает первый элемент
    int targetVectorIdx = index / vector;
    // номер элемента внутри вектора
    int targetIdx = (index % vector);
    T removedItem = null;
    for (int vectorIdx = array.size() - 1; vectorIdx >= targetVectorIdx; vectorIdx--) {
      if (removedItem != null) array.get(vectorIdx).add(removedItem);
      try{

        removedItem = array.get(vectorIdx).remove(vectorIdx == targetVectorIdx ? targetIdx : 0);
        //удаляем пустой вектор
        if (array.get(vectorIdx).size() == 0) {
          array.remove(array.size() - 1);
        }
      } catch (Exception e) {
        System.out.println("Error " + vectorIdx);
      }
    }
    size--;
    return element;
  }

  @Override
  public void shiftRight(int srcPos, int lenght) {
    if ((srcPos + lenght) > size()) throw new UnsupportedOperationException();
    // вектор в который попадаете последний элемент
    int finalVector = (srcPos + lenght - 1) / vector;
    // вектор в который попадает первый элемент
    int targetVectorIdx = srcPos / vector;
    // номер элемента внутри вектора
    int targetIdx = (srcPos % vector);

    // сдвигаем все векторы после целевого
    for (int vectorIdx = finalVector; vectorIdx > targetVectorIdx; vectorIdx--) {
      IArray<T> iArray = array.get(vectorIdx);
      // сдвигаем вектор на 1 элемент вправо
      if (vectorIdx == finalVector) {
        int finalElementPos = ((srcPos + lenght - 1) % vector) - 1;
        // если последний элемент вектора
        if ((finalElementPos) == (vector - 1)) {
          // копируем последний элемент в следующий вектор
          iArray.replace(array.get(vectorIdx + 1).get(vector - 1), 0);
          finalElementPos++;
        }
        iArray.shiftRight(0, finalElementPos + 1);
      } else iArray.shiftRight(0, vector - 1);
      // копируем последний элемент из предыдущего вектора
      iArray.replace(array.get(vectorIdx - 1).get(vector - 1), 0);
    }
    // сдвигаем целевой вектор
    array
        .get(targetVectorIdx)
        .shiftRight(targetIdx, (vector - 1) == targetIdx ? 0 : vector - targetIdx - 1);
  }

  @Override
  public void replace(T item, int index) {
    // вектор в который попадает  элемент
    int targetVectorIdx = index / vector;
    array.get(targetVectorIdx).replace(item, index % vector);
  }

  @Override
  public T get(int index) {
    return array.get(index / vector).get(index % vector);
  }
}
