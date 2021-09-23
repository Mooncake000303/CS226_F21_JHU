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
    // TODO
    if (size <= 0) {
      throw new LengthException();
    }
    head = null; //?? TODO: type?
    def = defaultValue;
    length = size;
  }

  @Override
  public int length() {
    return length;
  }

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
    return null; // empty list and default value
  }

  // add node at the last position of linked list
  private void addLast(int index, T value) {
    Node<T> nodeAdd = new Node<T>(index, value);
    Node<T> nodeLast = head;
    if (nodeLast == null) {
      head = nodeAdd;
    } else {
      while (nodeLast.next != null) {
        nodeLast = nodeLast.next;
      }
      nodeLast.next = nodeAdd;
      nodeLast.next = nodeAdd;
    }
  }

  // remove node of given index
  private void remove(int index) {
    Node<T> nodePrev = head;
    if (nodePrev.index == index) {
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
    if (nodeResult == null) { // new node potentially needed
      if (value != def) { // if value to be put in is not default - add node
        addLast(index, value);
      } // if value to be put in is default - do nothing
    } else { // an node at index already exist
      if (value != def) { // if value to be put in is not default - update the data of that node
        nodeResult.data = value;
      } else { // if value to be put in is default - remove that node
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
    private T[] dataArr = (T[]) new Object[length()];
    private int arrIndex; // TODO: index

    SparseIndexedListIterator() {
      for (int i = 0; i < length; i++) {
        dataArr[i] = def;
      }
      Node<T> nodeChange = head;
      while (nodeChange != null) {
        dataArr[nodeChange.index] = nodeChange.data;
        nodeChange = nodeChange.next;
      }
      arrIndex = 0;
    }

    @Override
    public boolean hasNext() {
      return arrIndex < length();
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T t = dataArr[arrIndex++];
      return t;
    }
  }
}
