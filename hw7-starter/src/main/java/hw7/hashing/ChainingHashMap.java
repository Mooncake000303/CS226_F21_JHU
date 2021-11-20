package hw7.hashing;

import hw7.Map;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Chaining Hashmap, with linked-list buckets.
 * Note: To be consistent with rubric, no tombStone is used. Capacity is doubled + 1 instead of prime table lookups.
 * @param <K> Key generic type
 * @param <V> Value Generic type
 */
public class ChainingHashMap<K, V> implements Map<K, V> {
  private static class Entry<K, V> {
    private K key;
    private V value;

    private Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private LinkedList<Entry<K, V>>[] hashTable; // An array of linked lists
  private int capacity; // initialized to 2
  private int numElements;
  private final double loadFactor = 0.75; // Load factor will be default to 0.75

  /**
   * The default, initial constructor.
   * capacity is initialized to 2
   */
  public ChainingHashMap() {
    this.capacity = 2;
    this.hashTable = (LinkedList<Entry<K,V>>[]) Array.newInstance(LinkedList.class, capacity);
    this.numElements = 0;
    for (int i = 0; i < capacity; i++) { // iterate through the table and initialize every bucket
      hashTable[i] = new LinkedList<>();
    }
  }

  /**
   * The constructor with capacity for rehashing.
   * @param capacity rehashed new capacity.
   */
  public ChainingHashMap(int capacity) {
    this.capacity = capacity;
    this.hashTable = (LinkedList<Entry<K,V>>[]) Array.newInstance(LinkedList.class, capacity);
    this.numElements = 0;
    for (int i = 0; i < capacity; i++) {
      hashTable[i] = new LinkedList<>();
    }
  }

  // hash function
  private int getIndex(K k) {
    return Math.floorMod(k.hashCode(), capacity);
  }

  // rehash with double +1 capacity (as suggested by campuswire post)
  private void rehash() {
    ChainingHashMap<K, V> rehashedMap = new ChainingHashMap<>(capacity * 2 + 1);
    for (K key: this) { // iterate through to reinsert
      rehashedMap.insert(key, this.get(key));
    }
    this.hashTable = rehashedMap.hashTable;
    this.capacity = rehashedMap.capacity;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null || has(k)) {
      throw new IllegalArgumentException();
    }
    hashTable[getIndex(k)].add(new Entry<>(k, v));
    numElements++;
    if (numElements >= loadFactor * capacity) { // rehash if a threshold of #filledCells is reached
      rehash();
    }
  }

  // Returns the entry if k is mapped, otherwise null (k is null or not mapped)
  private Entry<K, V> find(K k) {
    if (k == null) {
      return null;
    }
    for (Entry<K, V> entry: hashTable[getIndex(k)]) { // iterate through linked-list (within a bucket)
      if (k.equals(entry.key)) {
        return entry;
      }
    }
    return null;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException();
    }
    Entry<K, V> removedEntry = find(k);
    boolean entryIsRemoved = hashTable[getIndex(k)].remove(removedEntry);
    if (!entryIsRemoved) { // if fail to remove(not exist, etc), throw exception
      throw new IllegalArgumentException();
    }
    numElements--;
    return removedEntry.value;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Entry<K, V> entry = find(k);
    if (entry == null) {
      throw new IllegalArgumentException();
    }
    entry.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Entry<K, V> entry = find(k);
    if (entry == null) {
      throw new IllegalArgumentException();
    }
    return entry.value;
  }

  @Override
  public boolean has(K k) {
    return find(k) != null;
  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Iterator<K> iterator() {
    return new ChainingIterator();
  }

  private class ChainingIterator implements Iterator {
    private LinkedList<Entry<K, V>> curr; // current bucket
    private int counter; // counter of iterated elements
    private int outerIndex;
    private int innerIndex;

    private ChainingIterator() {
      counter = 0;
      outerIndex = 0;
      innerIndex = 0;
      curr = hashTable[outerIndex];
    }

    // move to present outIndex and innerIndex
    private void findLocation() {
      while (innerIndex >= curr.size()) { // move to next bucket if iteration in this bucket is done
        outerIndex++;
        curr = hashTable[outerIndex];
        innerIndex = 0;
      }
    }

    @Override
    public boolean hasNext() {
      return counter < numElements;
    }

    @Override
    public Object next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      findLocation();
      // move curr to the end, so that next will be the first in LL
      Entry<K, V> entry = curr.removeFirst();
      curr.addLast(entry);
      innerIndex++;
      counter++;
      return entry.key;
    }
  }
}
