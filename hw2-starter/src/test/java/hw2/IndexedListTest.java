package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit Tests for any class implementing the IndexedList interface.
 */
public abstract class IndexedListTest {
  protected static final int LENGTH = 10;
  protected static final int INITIAL = 7;
  private IndexedList<Integer> indexedList;

  public abstract IndexedList<Integer> createArray();

  @BeforeEach
  public void setup() {
    indexedList = createArray();
  }

  /* Note that [length does not change] tests are implemented throughout all tests below,
   * and are not grouped into a separate category.
   * To find these tests, see tests whose names end with CheckLength.
   */

  // test constructor
  @Test
  @DisplayName("The constructor SparseIndexedList() throws LengthException if index is below valid range (<= 0).")
  void testConstructorWithIndexBelowRangeThrowsException() {
    try {
      IndexedList<Integer> emptyList = new SparseIndexedList<>(0, INITIAL);
      fail("LengthException was not thrown for index = 0");
    } catch (LengthException e) {
      return;
    }
  }

  @Test
  @DisplayName("The constructor SparseIndexedList() assigns the correct length to the SparseIndexList.")
  void testConstructorCheckLength() {
    assertEquals(LENGTH, indexedList.length());
  }

  // test get - Exception
  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexBelowRangeThrowsException() {
    try {
      indexedList.get(-1);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("get() throws exception if index is above the valid range.")
  void testGetWithIndexAboveRangeThrowsException() {
    try {
      indexedList.get(10);
      fail("IndexException was not thrown for index > length");
    } catch (IndexException ex) {
      return;
    }
  }

  // test get - Normal
  @Test
  @DisplayName("get() returns the default value after IndexedList is instantiated.")
  void testGetAfterConstruction() {
    for (int index = 0; index < indexedList.length(); index++) {
      assertEquals(INITIAL, indexedList.get(index));
    }
  }

  // test put - Exception
  @Test
  @DisplayName("put() throws exception if index is below the valid range.")
  void testPutWithIndexBelowRangeThrowsException() {
    try {
      indexedList.put(-1, 5);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is above the valid range.")
  void testPutWithIndexAboveRangeThrowsException() {
    try {
      indexedList.put(10, 5);
      fail("IndexException was not thrown for index > length");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() should correctly put value at starting and ending edges. ")
  void testPutUsingGetEdgeCases() { // Note: NOT A UNIT TEST!
    indexedList.put(0, 42); // edge - start
    assertEquals(42, indexedList.get(0));
    indexedList.put(9, 43); // edge - end
    assertEquals(43, indexedList.get(9));
  }

  // test put - Add Node: To-be-put value is non-default and Node at index did NOT exist before.
  @Test
  @DisplayName("put() adds a new node when to-be-put value is non-default and Node at index did not exist before. " +
          "Value at put-index should be the new (different from) default value now.")
  void testPutAddNodeChangeAtIndex() {
    indexedList.put(3, 24);
    assertEquals(24, indexedList.get(3));
  }

  @Test
  @DisplayName("put() adds a new node when to-be-put value is non-default and Node at index did not exist before. " +
          "Value at other positions should be left untouched (still the default value) at other positions.")
  void testPutAddNodeUnchangedAtOtherPosition() {
    indexedList.put(3, 24);
    for (int i = 0; i < 3; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
    for (int i = 4; i < LENGTH; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
  }

  @Test
  @DisplayName("put() adds a new node when to-be-put value is non-default and Node at index did not exist before. " +
          "Length should not change.")
  void testPutAddNodeCheckLength() {
    indexedList.put(3, 24);
    assertEquals(LENGTH, indexedList.length());

  }

  // test put - Do Nothing: To-be-put value is default and Node at index did NOT exist before.
  @Test
  @DisplayName("put() do nothing when to-be-put value is default and Node at index did not exist before. " +
          "Value at every position should be the default value.")
  void testPutDoNothingAllValueDefault() {
    indexedList.put(3, INITIAL);
    for (int i = 0; i < LENGTH; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
  }

  @Test
  @DisplayName("put() do nothing when to-be-put value is default and Node at index did not exist before. " +
          "Length should not change.")
  void testPutDoNothingCheckLength() {
    indexedList.put(3, INITIAL);
    assertEquals(LENGTH, indexedList.length());
  }

  // test put - Update Node: To-be-put value is non-default and Node at index DID exist before.
  @Test
  @DisplayName("put() update data in Node when to-be-put value is non-default and Node at index DID exist before. " +
          "Value at put-index should be the updated (different from the origin value) now.")
  void testPutUpdateNodeChangeAtIndex() {
    indexedList.put(3, 24);
    assertEquals(24, indexedList.get(3));
    indexedList.put(3, 6);
    assertEquals(6, indexedList.get(3));
  }

  @Test
  @DisplayName("put() update data in Node when to-be-put value is non-default and Node at index DID exist before. " +
          "Value at other positions should be left untouched (still the default value) at other positions.")
  void testPutUpdateNodeUnchangedAtOtherPosition() {
    indexedList.put(3, 24);
    indexedList.put(3, 6);
    for (int i = 0; i < 3; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
    for (int i = 4; i < LENGTH; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
  }

  @Test
  @DisplayName("put() update data in Node when to-be-put value is non-default and Node at index DID exist before. " +
          "Length should not change.")
  void testPutUpdateNodeCheckLength() {
    indexedList.put(3, 24);
    assertEquals(LENGTH, indexedList.length());
    indexedList.put(3, 6);
    assertEquals(LENGTH, indexedList.length());
  }

  // test put - Remove Node: To-be-put value is default and Node at index DID exist before.
  @Test
  @DisplayName("put() remove the node when To-be-put value is default and Node at index DID exist before. " +
          "Value at put-index should be changed to default value now.")
  void testPutRemoveNodeChangeBackAtIndex() {
    indexedList.put(3, 24);
    indexedList.put(3, INITIAL);
    assertEquals(INITIAL, indexedList.get(3));
  }

  @Test
  @DisplayName("put() remove the node when To-be-put value is default and Node at index DID exist before. " +
          "Value at other positions should be left untouched (still the default value) at other positions.")
  void testPutRemoveNodeUnchangedAtOtherPosition() {
    indexedList.put(3, 24);
    indexedList.put(3, INITIAL);
    for (int i = 0; i < 3; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
    for (int i = 4; i < LENGTH; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
  }

  @Test
  @DisplayName("put() remove the node when To-be-put value is default and Node at index DID exist before. " +
          "Length should not change.")
  void testPutRemoveNodeCheckLength() {
    indexedList.put(3, 24);
    indexedList.put(3, INITIAL);
    assertEquals(LENGTH, indexedList.length());
  }

  // test hasNext
  @Test
  @DisplayName("hasNext() should return true just after construction. ")
  void testHasNextAfterConstruction() { // A not-very-meaningful hasNext() test, but the only possible unit test
    Iterator<Integer> it = indexedList.iterator();
    assertEquals(true, it.hasNext());
  }

  /* Note: in two tests below, unit tests for hasNext() are not possible
   * because we have to call next() to move iterator to next iterator.
   */
  @Test
  @DisplayName("hasNext() should return false when trying to do hasNext at the end of the list. ")
  void testHasNextAboveLength() {
    Iterator<Integer> it = indexedList.iterator();
    for (int i = 0; i < LENGTH; i++) {
      it.next();
    }
    assertEquals(false, it.hasNext());
  }

  @Test
  @DisplayName("hasNext() should operate the same way after all 4 kinds of operations by put.")
  void testHasNextAfterPut() {
    Iterator<Integer> it = indexedList.iterator();
    indexedList.put(3, 24);
    indexedList.put(5, 22);
    indexedList.put(3, 20);
    indexedList.put(5, INITIAL);
    int counter = 0;
    while (it.hasNext()) {
      it.next();
      counter++;
    }
    assertEquals(LENGTH, counter);
  }

  // test Next
  @Test
  @DisplayName("next() should throw NoSuchElementException when hasNext() is false. ")
  void testNextThrowsException() {
    Iterator<Integer> it = indexedList.iterator();
    for (int i = 0; i < LENGTH; i++) {
      it.next();
    }
    try {
      it.next();
      fail("NoSuchElementException was not thrown for cases when hasNext() is false");
    } catch (NoSuchElementException e) {
      return;
    }
  }

  @Test
  @DisplayName("next() should match the values at every index just after construction (default value). ")
  void testNextAfterConstruction() {
    Iterator<Integer> it = indexedList.iterator();
    for(int i = 1; i < LENGTH; i++) {
      assertEquals(indexedList.get(i), it.next());
    }
  }

  @Test
  @DisplayName("next() should operate the same way after all 4 kinds of operations by put. ")
  void testNextAfterPut() {
    indexedList.put(3, 24);
    indexedList.put(5, 22);
    indexedList.put(3, 20);
    indexedList.put(5, INITIAL);
    Iterator<Integer> it = indexedList.iterator();
    for (int i = 0; i < LENGTH; i++) {
      assertEquals(indexedList.get(i), it.next());
    }
  }

  // test iterator - enhanced for loops
  @Test
  @DisplayName("testIterator using enhanced for loop just after construction.")
  void testIteratorEnhancedForAfterConstruction() {
    for(int value: indexedList) {
      assertEquals(INITIAL, value);
    }
  }

  @Test
  @DisplayName("testIterator using enhanced for loop after all 4 kinds of operations by put.")
  void testIteratorEnhancedForAfterPut() {
    indexedList.put(3, 24);
    indexedList.put(5, 22);
    indexedList.put(3, 20);
    indexedList.put(5, INITIAL);
    int i = 0;
    for(int value: indexedList) {
      assertEquals(indexedList.get(i++), value);
    }
  }

  /* Below is a "Complex" but not necessarily comprehensive test.
  * It integrates put, get and iterator in this test and it's purpose is NOT a unit test. */
  @Test
  @DisplayName("Complex test that involves put, get, and iterator.")
  void testAllComplex() {
    for (int i = 0; i < LENGTH; i += 2) { // put i*i at index i when i is even
      indexedList.put(i, i * i);
    }
    for(int i = 0; i < LENGTH; i++) { // test get
      if (i % 2 == 0) {
        assertEquals(i * i, indexedList.get(i));
      } else {
        assertEquals(INITIAL, indexedList.get(i));
      }
    }

    for(int i = 0; i < LENGTH; i++) { // should remove all nodes
      indexedList.put(i, INITIAL);
    }
    indexedList.put(0, 24);
    indexedList.put(9, 42); // add 2 nodes

    for(int i = 1; i < LENGTH - 1; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
    assertEquals(24, indexedList.get(0));
    assertEquals(42, indexedList.get(9));

    int index = 0;
    for(int elements: indexedList) { // iterator
      assertEquals(indexedList.get(index), elements);
      index++;
    }
  }
}