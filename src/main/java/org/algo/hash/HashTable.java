package org.algo.hash;

public class HashTable<K, V> {
  private Entry<K, V>[] table;

  private int count;

  private int threshold;
  private int capacity = 11;

  public HashTable(int capacity) {
    this.capacity = capacity;
    threshold = (3 * capacity) / 4;
    count = 0;
    table = new Entry[capacity];
  }

  public V put(K key, V value) {
    int hashCode = key.hashCode();
    int index = hashCode % capacity;
    if (table[index] != null) {
      Entry<K, V>.Node<K, V> node = table[index].head;
      while (node != null) {
        if (node.key == key) {
          V buf = node.value;
          node.value = value;
          return buf;
        }
        node = node.next;
      }
    }
    addEntry(hashCode, key, value, index);
    count++;
    return value;
  }

  public V get(K key) {
    int hashCode = key.hashCode();
    int index = hashCode % capacity;
    if (table[index] == null) return null;
    Entry<K, V>.Node<K, V> node = table[index].head;
    while (node != null) {
      if (node.key == key) {
        return node.value;
      }
      node = node.next;
    }
    return null;
  }

  public V delete(K key) {
    int hashCode = key.hashCode();
    int index = hashCode % capacity;
    if (table[index] == null) return null;
    Entry<K, V> entry = table[index];
    Entry<K, V>.Node<K, V> node = null;
    if (entry != null) {
      node = entry.delete(key);
      if (entry.head == null) table[index] = null;
      count--;
    }
    return node != null ? node.value : null;
  }

  private void addEntry(int hash, K key, V value, int index) {
    if (count >= threshold) {
      // Rehash the table if the threshold is exceeded
      rehash();

      hash = key.hashCode();
      index = hash % table.length;
    }

    Entry<K, V> e = table[index];
    if (e == null) e = new Entry<>();
    e.add(key, value, hash);
    table[index] = e;
  }

  private void rehash() {
    int newCapacity = capacity * 2;
    this.threshold = (3 * newCapacity) / 4;
    Entry<K, V>[] newTable = new Entry[newCapacity];
    for (int idx = table.length - 1; idx >= 0; idx--) {
      Entry<K, V>.Node<K, V> node;
      while (table[idx] != null && (node = table[idx].pull()) != null) {
        Entry<K, V> e = new Entry<>();
        e.add(node.key, node.value, node.hash);
        int newIndex = node.hash % newCapacity;
        newTable[newIndex] = e;
      }
    }
    this.table = newTable;
    this.capacity = newCapacity;
  }

  public static class Entry<K, V> {
    Node<K, V> head;

    public Entry() {
      head = null;
    }

    public void add(K key, V value, int hash) {
      // добавляем в начало
      head = new Node<>(key, value, hash, head);
    }

    public Node<K, V> pull() {
      if (head == null) return null;
      Node<K, V> node = head;
      this.head = head.next;
      return node;
    }

    public Node<K, V> delete(K key) {
      Node<K, V> currentNode = head;
      Node<K, V> previousNode = null;
      while (currentNode != null) {
        if (key == currentNode.key) {
          if (previousNode != null) previousNode.next = currentNode.next;
          else head = currentNode.next;
          return currentNode;
        }
        previousNode = currentNode;
        currentNode = currentNode.next;
      }
      return null;
    }

    public class Node<K1, V1> {
      public K1 key;
      public V1 value;
      public int hash;
      public Node<K1, V1> next;

      public Node(K1 key, V1 value, int hash, Node<K1, V1> nextValue) {
        this(key, value, hash);
        this.next = nextValue;
      }

      public Node(K1 key, V1 value, int hash) {
        this.key = key;
        this.value = value;
        this.hash = hash;
      }
    }
  }
}
