package hw7.hashing;

import hw7.Map;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Open Addressing Hashmap, double hashing to avoid primary & secondary clustering.
 *
 * @param <K> Key generic type
 * @param <V> Value Generic type
 */
public class OpenAddressingHashMap<K, V> implements Map<K, V> {
  private static class Entry<K, V> {
    K key;
    V value;
    boolean tombStone;

    private Entry(K k, V v) {
      this.key = k;
      this.value = v;
      this.tombStone = false; // default: not a tombStone
    }
  }

  private Entry<K, V>[] hashTable;
  private int capacity;
  private int numElements;
  private int filledCells;

  private final double loadFactor = 0.75;
  private final int[] primes = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437,
      102877, 205759, 411527, 823117, 1646237,3292489, 6584983, 13169977};
  private int primesIndex; // index in prime table initialized to 0
  private final int secondHashQ = 7; // Q for getSecondIndex

  /**
   * The default, initial constructor.
   * capacity is initialized 2 (primes[0])
   */
  public OpenAddressingHashMap() {
    this.capacity = 2;
    this.hashTable = (Entry<K, V>[]) Array.newInstance(Entry.class, capacity);
    this.numElements = 0;
    this.filledCells = 0;
  }

  /**
   * The constructor with capacity for rehashing.
   * @param capacity next prime in the primes[]
   */
  public OpenAddressingHashMap(int capacity) {
    this.capacity = capacity;
    this.hashTable = (Entry<K, V>[]) Array.newInstance(Entry.class, capacity);
    this.numElements = 0;
    this.filledCells = 0;
  }

  // first index hash function
  private int getIndex(K k) {
    return Math.floorMod(k.hashCode(), capacity); // not using % to avoid - hashCode!
  }

  // secondary index hash function
  private int getSecondIndex(K k) {
    return secondHashQ - Math.floorMod(k.hashCode(), secondHashQ);
  }

  // lecture-style double hashing
  private void doubleHashing(K k, V v) {
    int index1 = getIndex(k);
    int index2 = getSecondIndex(k);
    for (int i = 0; i < capacity; i++) {
      int index = (index1 + index2 * i) % capacity;
      if (hashTable[index] == null) { // do NOT write over tombStone, only insert in "real" available spaces
        insert(k, v, index);
        return;
      }
    }
  }

  // rehash with greater capacity, skip over tombStones
  private void rehash() {
    primesIndex++;
    int rehashedCapacity = primes[primesIndex]; // next capacity in the prime table
    OpenAddressingHashMap<K, V> rehashedMap = new OpenAddressingHashMap<>(rehashedCapacity);
    for (int i = 0; i < capacity; i++) {
      if (hashTable[i] != null) { // iterate through existing Entrys
        if (!hashTable[i].tombStone) { // not a tombStone
          K thisEntryKey = hashTable[i].key;
          V thisEntryValue = hashTable[i].value;
          rehashedMap.insert(thisEntryKey, thisEntryValue);
        }
        // if is a tombStone, skip over it
      }
    }
    // Update by reassignment
    this.hashTable = rehashedMap.hashTable;
    this.capacity = rehashedMap.capacity;
    this.numElements = rehashedMap.numElements;
  }

  // plain simple insert
  private void insert(K k, V v, int index) {
    hashTable[index] = new Entry<>(k, v);
    numElements++;
    filledCells++;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null || has(k)) {
      throw new IllegalArgumentException();
    }
    doubleHashing(k, v);
    if (filledCells >= loadFactor * capacity) { // rehash if a threshold of #filledCells is reached
      rehash();
    }
  }

  // returns the index of given k in the hashTable, otherwise -1 (k is null, not found, found but is tombStone)
  private int findIndex(K k) {
    if (k == null) { // k is null
      return -1;
    }

    int index1 = getIndex(k); // first index
    int index2 = getSecondIndex(k); // secondary index
    int index = index1; // index start with first index with no add-on of secondary index
    while (hashTable[index] != null) {
      if (k.equals(hashTable[index].key)) {
        if (!hashTable[index].tombStone) { // k matches & not a tombStone
          return index;
        }
      }
      index = (index + index2) % capacity; // move "one" index2 further
      if (index == index1) {
        break; // iterate through and again at index1, not found
      }
    }
    return -1;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null || !has(k)) { // If k is null or not "contained"
      throw new IllegalArgumentException();
    }
    int index = findIndex(k);
    hashTable[index].tombStone = true; // set to tombStone
    numElements--;
    return hashTable[index].value;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k == null || !has(k)) { // If k is null or not "contained"
      throw new IllegalArgumentException();
    }
    int index = findIndex(k);
    hashTable[index].value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null || !has(k)) { // If k is null or not "contained"
      throw new IllegalArgumentException();
    }
    int index = findIndex(k);
    return hashTable[index].value;

  }

  @Override
  public boolean has(K k) {
    int index = findIndex(k);
    return index >= 0; // false when index < 0
  }

  @Override
  public int size() {
    return numElements;
  }

  @Override
  public Iterator<K> iterator() {
    return new OpenAddressingIterator();
  }

  private class OpenAddressingIterator implements Iterator<K> {
    private int nextIndex;
    private int counter;

    OpenAddressingIterator() {
      nextIndex = 0;
      counter = 0; // counter of iterated elements
    }

    @Override
    public boolean hasNext() {
      return counter < numElements;
    }

    @Override
    public K next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      Entry<K, V> entry = hashTable[nextIndex];
      while (entry == null || entry.tombStone) { // if null or tombStone
        entry = hashTable[++nextIndex]; // skip to next entry
      }
      nextIndex++;
      counter++;
      return entry.key;
    }
  }
}