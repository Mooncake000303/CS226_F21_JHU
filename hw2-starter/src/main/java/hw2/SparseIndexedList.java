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

  // add node according to ascending order of the index
  private void add(int index, T value) {
    Node<T> nodeAdd = new Node<T>(index, value);
    Node<T> nodePrev = head;
    if (nodePrev == null) { // when empty
      head = nodeAdd;
    } else {
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

  // remove node of given index
  // Pre-condition: There must be something that can be removed.
  // head can't be null.
  // If there's only one node, that one node is the only one to be removed.
  private void remove(int index) {
    Node<T> nodePrev = head;
    if (nodePrev.index == index) { // only one node, node.next is null
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
        add(index, value);
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
    //private Node<T> nodeHead = head; //?? make it outside and private
    private Node<T> nodeCur = head;

    private int indexCur;
    private int indexNext;

    SparseIndexedListIterator() {
    }

    @Override
    public boolean hasNext() {
      return indexCur < length(); // TODO
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      if (nodeCur == null) { // head == null
        indexCur++;
        return def;
      } else { // nodeCur != null, at least one element
        indexNext = nodeCur.index;
        if (indexCur < indexNext) {
          indexCur++;
          return def;
        } else { // indexCur == indexNext
          T data = nodeCur.data;
          indexCur++;
          nodeCur = nodeCur.next; // could be null - back to line 156 - seems to be handled
          return data;
        }
      }
    }
  }
}