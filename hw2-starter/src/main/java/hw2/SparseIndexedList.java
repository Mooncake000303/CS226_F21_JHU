package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An implementation of an IndexedList designed for cases where
 * only a few positions have distinct values from the initial value.
 *
 * @param <T> Element type.
 */
public class SparseIndexedList<T> implements IndexedList<T> {
  private Node<T> head;
  private T def;
  private int length;

  /**
   * Constructs a new SparseIndexedList of length size
   * with default value of defaultValue.
   *
   * @param size Length of list, expected: size > 0.
   * @param defaultValue Default value to store in each slot.
   * @throws LengthException if size <= 0.
   */
  public SparseIndexedList(int size, T defaultValue) throws LengthException {
    if (size <= 0) {
      throw new LengthException();
    }
    head = null; // a pointer
    def = defaultValue;
    length = size;
  }

  @Override
  public int length() {
    return length;
  }

  // Find the Node at a given index.
  // Returns the node if it exist. Otherwise return null.
  // Throws IndexException when index < 0 or index >= length.
  private Node<T> find(int index) throws IndexException {
    if (index < 0 || index >= length()) {
      throw new IndexException();
    }
    Node<T> node = head;
    while (node != null) {
      if (node.index == index) {
        return node;
      }
      node = node.next;
    }
    return null; // Empty list OR not found
  }

  // Add node based on ascending order of the index
  private void add(int index, T value) {
    Node<T> nodeAdd = new Node<T>(index, value);
    Node<T> nodePrev = head;
    if (nodePrev == null) { // when empty, add on head
      head = nodeAdd;
    } else { // have at least one node
      while (nodePrev.next != null && index > nodePrev.next.index) {
        nodePrev = nodePrev.next;
      }
      if (index < nodePrev.index) { // add before - between head and first node
        nodeAdd.next = head;
        head = nodeAdd;
      } else { // add after
        nodeAdd.next = nodePrev.next;
        nodePrev.next = nodeAdd;
      }
    }
  }

  // Remove node of given index
  // Pre-condition: There must be something that can be removed, ie. head can't be null.
  // If there's only one node, that one node is the only one to be removed.
  private void remove(int index) {
    Node<T> nodePrev = head;
    if (nodePrev.index == index) { // only one node
      head = head.next;
    } else {
      while (nodePrev.next.index != index) {
        nodePrev = nodePrev.next;
      }
      nodePrev.next = nodePrev.next.next;
    }
  }

  @Override
  public T get(int index) throws IndexException {
    Node<T> nodeResult = find(index);
    if (nodeResult == null) {
      return def;
    }
    return nodeResult.data;
  }

  @Override
  public void put(int index, T value) throws IndexException {
    // TODO
    Node<T> nodeResult = find(index);
    if (nodeResult == null) { // node does not exit
      if (value != def) {
        add(index, value);
      } // if value to be put in is default - do nothing
    } else { // node already exit
      if (value != def) {
        nodeResult.data = value;
      } else {
        remove(index);
      }
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }

  private static class Node<T> {
    T data;
    int index;
    Node<T> next;

    Node(int index, T data) {
      this.data = data;
      this.index = index;
      this.next = null;
    }
  }

  private class SparseIndexedListIterator implements Iterator<T> {
    private Node<T> nodeCur;
    private int indexCur;
    private int indexNext;

    SparseIndexedListIterator() { // O(1) ver
      nodeCur = head;
      indexCur = 0;
      indexNext = 0;
    }

    @Override
    public boolean hasNext() { // O(1) ver
      return indexCur < length();
    }

    @Override
    public T next() throws NoSuchElementException { // O(1) ver
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      if (nodeCur == null) { // head == null OR no element afterwards
        indexCur++;
        return def;
      } else { // at least one element afterwards
        indexNext = nodeCur.index;
        if (indexCur < indexNext) {
          indexCur++;
          return def;
        } else { // indexCur == indexNext, return data in Node
          T data = nodeCur.data;
          indexCur++;
          nodeCur = nodeCur.next;
          return data;
        }
      }
    }
  }
}