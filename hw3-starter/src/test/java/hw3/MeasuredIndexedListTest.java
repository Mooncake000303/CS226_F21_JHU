package hw3;

import exceptions.IndexException;
import exceptions.LengthException;
import hw3.list.MeasuredIndexedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MeasuredIndexedListTest {

  private static final int LENGTH = 15;
  private static final int DEFAULT_VALUE = 3;

  private MeasuredIndexedList<Integer> measuredIndexedList;

  @BeforeEach
  void setup() {
    measuredIndexedList = new MeasuredIndexedList<>(LENGTH, DEFAULT_VALUE);
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero reads")
  void zeroReadsStart() {
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero writes")
  void zeroWritesStart() {
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Access with get() invalid index does not change access count")
  void testAccessGetOnlyInvalidIndex(){
    assertEquals(0, measuredIndexedList.accesses());
    measuredIndexedList.get(0);
    assertEquals(1, measuredIndexedList.accesses());
    try {
      measuredIndexedList.get(-1);
    } catch (IndexException ex){
      assertEquals(1, measuredIndexedList.accesses());
    }
  }

  @Test
  @DisplayName("Access with get() called returns the number of get()")
  void testAccessGetOnly(){
    assertEquals(0, measuredIndexedList.accesses());
    measuredIndexedList.get(0);
    assertEquals(1, measuredIndexedList.accesses());
    measuredIndexedList.get(1);
    assertEquals(2, measuredIndexedList.accesses());
    measuredIndexedList.get(2);
    assertEquals(3, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("Access with put() only should return 0 because put() should not increment access count")
  void testAccessPutOnly(){
    measuredIndexedList.put(13, 5);
    assertEquals(0, measuredIndexedList.accesses());
    measuredIndexedList.put(14, 7);
    assertEquals(0, measuredIndexedList.accesses());
    measuredIndexedList.put(10, 6);
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("Access with length() only should return 0 because length() should not increment access count")
  void testAccessLengthOnly(){
    measuredIndexedList.length();
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("Mutation with Access with get() invalid index does not change mutation count")
  void testMutateOnlyInvalidIndex(){
    assertEquals(0, measuredIndexedList.mutations());
    measuredIndexedList.put(2, 24);
    assertEquals(1, measuredIndexedList.mutations());
    try {
      measuredIndexedList.put(-1, 42);
    } catch (IndexException ex) {
      assertEquals(1, measuredIndexedList.mutations());
    }
  }

  @Test
  @DisplayName("Mutation with put() called returns the number of put()")
  void testMutatePutOnly(){
    assertEquals(0, measuredIndexedList.mutations());
    measuredIndexedList.put(2, 24);
    assertEquals(1, measuredIndexedList.mutations());
    measuredIndexedList.put(3, 42);
    assertEquals(2, measuredIndexedList.mutations());
    measuredIndexedList.put(9, 43);
    assertEquals(3, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Mutate with get() only should return 0 because get() should not increment Mutation count")
  void testMutateGetOnly(){
    measuredIndexedList.get(5);
    assertEquals(0, measuredIndexedList.mutations());
    measuredIndexedList.get(6);
    assertEquals(0, measuredIndexedList.mutations());
    measuredIndexedList.get(7);
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Mutate with length() only should return 0 because length() should not increment Mutation count")
  void testMutateLengthOnly(){
    measuredIndexedList.length();
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("count() should return Length # of default value and 0 of other values in default case.")
  void testCountDefault(){
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(0, measuredIndexedList.count(DEFAULT_VALUE - 1));
  }

  @Test
  @DisplayName("count() should return Length - #puts of default values")
  void testCountAfterPut(){
    measuredIndexedList.put(3, 24);
    assertEquals(LENGTH - 1, measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(1, measuredIndexedList.count(24));
    measuredIndexedList.put(6, 42);
    assertEquals(LENGTH - 2, measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(1, measuredIndexedList.count(24));
    assertEquals(1, measuredIndexedList.count(42));
    measuredIndexedList.put(7, 24);
    assertEquals(LENGTH - 3, measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(1, measuredIndexedList.count(42));
    assertEquals(2, measuredIndexedList.count(24));
  }

  @Test
  @DisplayName("count() should return Length because get() does not change values in the list")
  void testCountAfterGet(){
    measuredIndexedList.get(3);
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));
    measuredIndexedList.get(4);
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));
    measuredIndexedList.get(5);
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));

  }

  @Test
  @DisplayName("count() should return Length because length() does not change values in the list")
  void testCountAfterLength(){
    measuredIndexedList.length();
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));
  }

  /*A note: this might not seems like unit testing, but here it's unit testing on reset
  * instead access or get.
  * Also, note all access and mutation counter of reset are tested together instead of separately
  * because it only makes sense that reset is correct only when both of these counter are correct.*/
  @Test
  @DisplayName("Get 3 times and reset should reset access and mutation both to 0")
  void testResetGetOnly(){
    measuredIndexedList.get(0);
    measuredIndexedList.get(1);
    measuredIndexedList.get(2);
    assertEquals(3 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
    measuredIndexedList.reset();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Put 2 times and reset should reset access and mutation both to 0")
  void testResetPutOnly(){
    measuredIndexedList.put(13, 5);
    measuredIndexedList.put(14, 7);
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(2 , measuredIndexedList.mutations());
    measuredIndexedList.reset();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("In default case, length() should not affect reset of access and mutations to 0")
  void testResetLengthOnly(){
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
    measuredIndexedList.reset();
    measuredIndexedList.length();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("In case with get and put, length() should not affect reset of access and mutations to 0")
  void testResetLengthWithGetPut(){
    measuredIndexedList.get(1);
    measuredIndexedList.put(13, 5);
    measuredIndexedList.put(14, 7);
    assertEquals(1 , measuredIndexedList.accesses());
    assertEquals(2 , measuredIndexedList.mutations());
    measuredIndexedList.reset();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
    measuredIndexedList.length();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
  }

  /* complex cases starting here */
  @Test
  @DisplayName("test Access only, with put, get, and length")
  void testAccessAll(){
    measuredIndexedList.get(0);
    assertEquals(1 , measuredIndexedList.accesses());
    measuredIndexedList.put(4, 42);
    assertEquals(1 , measuredIndexedList.accesses());
    measuredIndexedList.get(5);
    assertEquals(2 , measuredIndexedList.accesses());
    measuredIndexedList.length();
    assertEquals(2 , measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("test Mutate only, with put, get, and length")
  void testMutateAll(){
    measuredIndexedList.put(0, 24);
    assertEquals(1 , measuredIndexedList.mutations());
    measuredIndexedList.get(4);
    assertEquals(1 , measuredIndexedList.mutations());
    measuredIndexedList.put(9, 56);
    assertEquals(2 , measuredIndexedList.mutations());
    measuredIndexedList.length();
    assertEquals(2 , measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("test Count only, with put, get, and length")
  void testCountAll(){
    assertEquals(LENGTH , measuredIndexedList.count(DEFAULT_VALUE));
    measuredIndexedList.put(3, 24);
    assertEquals(LENGTH - 1 , measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(1 , measuredIndexedList.count(24));
    measuredIndexedList.get(4);
    assertEquals(LENGTH - 1 , measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(1 , measuredIndexedList.count(24));
    measuredIndexedList.put(0, 42);
    assertEquals(LENGTH - 2 , measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(1 , measuredIndexedList.count(24));
    assertEquals(1 , measuredIndexedList.count(42));
    measuredIndexedList.put(3, 42);
    assertEquals(LENGTH - 2 , measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(0 , measuredIndexedList.count(24));
    assertEquals(2 , measuredIndexedList.count(42));
    measuredIndexedList.length();
    assertEquals(LENGTH - 2 , measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(0 , measuredIndexedList.count(24));
    assertEquals(2 , measuredIndexedList.count(42));
  }

  @Test
  @DisplayName("test Reset only, with put, get, and length")
  void testResetAll() {
    measuredIndexedList.put(3, 24);
    measuredIndexedList.get(2);
    measuredIndexedList.put(7, 42);
    measuredIndexedList.length();
    measuredIndexedList.reset();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
    measuredIndexedList.length();
    measuredIndexedList.get(4);
    measuredIndexedList.put(9, 56);
    measuredIndexedList.get(5);
    measuredIndexedList.reset();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Integrated test of access, mutate, count, reset, with put, get, and length")
  void testAllComplexNormal() {
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(LENGTH , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());

    measuredIndexedList.reset();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());

    measuredIndexedList.get(4);
    measuredIndexedList.get(6);
    measuredIndexedList.get(9);
    measuredIndexedList.put(5, 24);
    measuredIndexedList.put (10, 24);
    assertEquals(3 , measuredIndexedList.accesses());
    assertEquals(2 , measuredIndexedList.mutations());
    assertEquals(2, measuredIndexedList.count(24));
    measuredIndexedList.reset();
    assertEquals(0 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());

    measuredIndexedList.length();
    measuredIndexedList.get(3);
    measuredIndexedList.get(0);
    measuredIndexedList.get(13);
    assertEquals(3 , measuredIndexedList.accesses());
    assertEquals(0 , measuredIndexedList.mutations());

    measuredIndexedList.put(5, 24);
    measuredIndexedList.put(10, 42);
    assertEquals(3 , measuredIndexedList.accesses());
    assertEquals(2 , measuredIndexedList.mutations());

    measuredIndexedList.length();
    assertEquals(LENGTH - 2, measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(3 + LENGTH , measuredIndexedList.accesses());
    assertEquals(2 , measuredIndexedList.mutations());

    measuredIndexedList.put(10, 24);
    assertEquals(3 + LENGTH , measuredIndexedList.accesses());
    assertEquals(3 , measuredIndexedList.mutations());

    assertEquals(LENGTH - 2, measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(3 + LENGTH + LENGTH, measuredIndexedList.accesses());
    assertEquals(3 , measuredIndexedList.mutations());

    assertEquals(2, measuredIndexedList.count(24));
    assertEquals(3 + LENGTH + LENGTH + LENGTH, measuredIndexedList.accesses());
    assertEquals(3 , measuredIndexedList.mutations());
  }

  /* P.S. Some additional tests for iterator. Read on ONLY if you are interested...*/
  @Test
  @DisplayName("Iterator returns correct value in default case.")
  void testIteratorDefaultValue() {
    int i = 0;
    for (int e: measuredIndexedList) {
      assertEquals(DEFAULT_VALUE, e);
    }
  }

  @Test
  @DisplayName("Iterator does not change access counter when iterating through in default case.")
  void testIteratorDefaultAccessCounter() {
    for (int e: measuredIndexedList) {
      assertEquals(0, measuredIndexedList.accesses());
    }
  }

  @Test
  @DisplayName("Iterator does not change mutation counter when iterating through in default case..")
  void testIteratorDefaultMutateCounter() {
    for (int e: measuredIndexedList) {
      assertEquals(0, measuredIndexedList.mutations());
    }
  }

  @Test
  @DisplayName("Iterator returns changed value after put.")
  void testIteratorNonDefaultValue() {
    int i = 0;
    for (int e: measuredIndexedList) {
      measuredIndexedList.put(i++, 24);
    }
    for (int e: measuredIndexedList) {
      assertEquals(24, e);
    }
  }

  @Test
  @DisplayName("Iterator does not affect access counter non-default value cases.")
  void testIteratorNonDefaultAccessCounter() {
    int i = 0;
    for (int e: measuredIndexedList) {
      measuredIndexedList.put(i++, 24);
    }
    assertEquals(0, measuredIndexedList.accesses());
    measuredIndexedList.reset();
    for (int e: measuredIndexedList) {
      assertEquals(0, measuredIndexedList.accesses());
    }
  }
  @Test
  @DisplayName("Iterator does not affect mutate counter non-default value cases.")
  void testIteratorNonDefaultMutationCounter() {
    int i = 0;
    for (int e: measuredIndexedList) {
      measuredIndexedList.put(i++, 24);
    }
    assertEquals(LENGTH, measuredIndexedList.mutations());

    measuredIndexedList.reset();
    for (int e: measuredIndexedList) {
      assertEquals(0, measuredIndexedList.mutations());
    }
  }

}
