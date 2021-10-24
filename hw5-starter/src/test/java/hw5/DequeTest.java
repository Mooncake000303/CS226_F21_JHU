package hw5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Add two import statements
import static org.junit.jupiter.api.Assertions.fail;
import exceptions.EmptyException;

/**
 * Tests for Deque and FlawedDeque.
 */
public abstract class DequeTest {

  private Deque<String> deque;

  @BeforeEach
  public void setUp() {
    this.deque = createDeque();
  }

  protected abstract Deque<String> createDeque();

  @Test
  @DisplayName("Deque is empty after construction.")
  public void testConstructor() {
    assertTrue(deque.empty());
    assertEquals(0, deque.length());
  }

  // TODO Add more tests
  /* Note: tests of functions on NonEmpty Deque not 100% *unit* testing since
   * calls to front&back or to insertFront/Back & removeFront/Back are inevitable
   * Therefore, they are not in a particular order.
   * Comments are just for section outlining. */
  @Test
  @DisplayName("empty returns true when the deque is empty")
  public void testEmptyOnEmpty() {
    assertTrue(deque.empty());
  }

  @Test
  @DisplayName("empty returns false when the deque is non-empty")
  public void testEmptyOnNonEmpty() {
    deque.insertFront("1");
    assertTrue(!deque.empty());
    deque.insertFront("2");
    assertTrue(!deque.empty());
    deque.removeFront();
    assertTrue(!deque.empty());
    deque.removeFront();
    assertTrue(deque.empty());
  }

  @Test
  @DisplayName("length is 0 when the deque is empty")
  public void testLengthOnEmpty() {
    assertEquals(0, deque.length());
  }

  // This is a simple trial
  // Comprehensive length test is later! (though it's not targeted at length())
  @Test
  @DisplayName("length is 1 when one is insertedFront (simple trial, no comprehensiveness meant!)")
  public void testLengthOnNonEmptySimple() {
    deque.insertFront("1");
    assertEquals(1, deque.length());
  }

  // Empty exceptions
  @Test
  @DisplayName("front throws EmptyException when Deque is empty")
  public void frontThrowsEmptyException() {
    try {
      deque.front();
      fail("front FAILs to throws EmptyException when Deque is empty.");
    } catch (EmptyException ex) {
      return;
    }
  }

  @Test
  @DisplayName("back throws EmptyException when Deque is empty")
  public void backThrowsEmptyException() {
    try {
      deque.back();
      fail("back FAILs to throws EmptyException when Deque is empty.");
    } catch (EmptyException ex) {
      return;
    }
  }

  @Test
  @DisplayName("removeFront throws EmptyException when Deque is empty")
  public void removeFrontThrowsEmptyException() {
    try {
      deque.removeFront();
      fail("removeFront FAILs to throws EmptyException when Deque is empty.");
    } catch (EmptyException ex) {
      return;
    }
  }

  @Test
  @DisplayName("removeBack throws EmptyException when Deque is empty")
  public void removeBackThrowsEmptyException() {
    try {
      deque.removeBack();
      fail("removeBack FAILs to throws EmptyException when Deque is empty.");
    } catch (EmptyException ex) {
      return;
    }
  }

  // front & back on NonEmpty
  @Test
  @DisplayName("front returns the correct front value of non-empty deque")
  public void frontNonEmpty() {
    deque.insertFront("7");
    deque.insertFront("6");
    // deque.insertBack("5");

    deque.insertFront("4");
    assertEquals("4", deque.front());
    deque.insertFront("3");
    assertEquals("3", deque.front());
    deque.insertFront("2");
    assertEquals("2", deque.front());
    deque.insertFront("1");
    assertEquals("1", deque.front());
  }

  @Test
  @DisplayName("back returns the correct back value of non-empty deque")
  public void backNonEmpty() {
    deque.insertFront("9");
    // deque.insertBack("8");
    deque.insertFront("7");

//    deque.insertBack("1");
//    assertEquals("1", deque.back());
    deque.insertFront("1");
    assertEquals("9", deque.back());
    deque.insertBack("3");
    assertEquals("3", deque.back());
  }

  // insertFront & insertBack
  @Test
  @DisplayName("after insertFront to Empty Deque, have correct front and back")
  public void insertFrontEmpty() {
    deque.insertFront("1");
    assertEquals("1", deque.front());
    assertEquals("1", deque.back());
  }

  @Test
  @DisplayName("after insertFront to NonEmpty Deque, have correct front and back")
  public void insertFrontNonEmpty() {
    deque.insertFront("1");
    deque.insertFront("2");
    assertEquals("2", deque.front());
    assertEquals("1", deque.back());
    deque.insertFront("3");
    assertEquals("3", deque.front());
    assertEquals("1", deque.back());
    deque.insertFront("4");
    assertEquals("4", deque.front());
    assertEquals("1", deque.back());
  }

  @Test
  @DisplayName("after insertBack to Empty Deque, have correct front and back")
  public void insertBackEmpty() {
    deque.insertBack("6");
    assertEquals("6", deque.front());
    assertEquals("6", deque.back());
  }

  @Test
  @DisplayName("after insertBack to NonEmpty Deque, have correct front and back")
  public void insertBackNonEmpty() {
    deque.insertBack("3");
    deque.insertBack("2");
    assertEquals("2", deque.back());
    assertEquals("3", deque.front());
    deque.insertBack("1");
    assertEquals("1", deque.back());
    assertEquals("3", deque.front());
    deque.insertBack("0");
    assertEquals("0", deque.back());
    assertEquals("3", deque.front());
  }

  // removeFront & removeBack
  @Test
  @DisplayName("after removeFront to NonEmpty Deque, have correct front and back")
  public void removeFrontNonEmpty() {
    deque.insertFront("1");
    deque.insertFront("2");
    deque.insertFront("3");
    deque.insertFront("4");

    assertEquals("4", deque.front());
    assertEquals("1", deque.back());
    deque.removeFront();
    assertEquals("3", deque.front());
    assertEquals("1", deque.back());
    deque.removeFront();
    assertEquals("2", deque.front());
    assertEquals("1", deque.back());
    deque.removeFront();
    assertEquals("1", deque.front());
    assertEquals("1", deque.back());
    deque.removeFront();
    assertTrue(deque.empty());

  }

  @Test
  @DisplayName("after removeBack to NonEmpty Deque, have correct front and back")
  public void removeBackNonEmpty() {
//    deque.insertBack("6");
//    deque.insertBack("7");
//    deque.insertBack("8");
//    deque.insertBack("9");
    deque.insertFront("9");
    deque.insertFront("8");
    deque.insertFront("7");
    deque.insertFront("6");

    assertEquals("9", deque.back());
    assertEquals("6", deque.front());
    deque.removeBack();
    assertEquals("8", deque.back());
    assertEquals("6", deque.front());
    deque.removeBack();
    assertEquals("7", deque.back());
    assertEquals("6", deque.front());
    deque.removeBack();
    assertEquals("6", deque.back());
    assertEquals("6", deque.front());
    deque.removeBack();
    assertTrue(deque.empty());
  }

  /* length tests for front, back, insertFront, insertBack, removeFront, removeBack */
  // front & back should not change length
  @Test
  @DisplayName("front should not change length")
  public void lengthNotChangedByFront() {
    deque.insertFront("1");
    deque.insertFront("2");
    deque.insertFront("3");
    deque.insertFront("4");
    assertEquals(4, deque.length());
    deque.front();
    assertEquals(4, deque.length());
  }

  @Test
  @DisplayName("back should not change length")
  public void lengthNotChangedByBack() {
//    deque.insertBack("6");
//    deque.insertBack("7");
//    deque.insertBack("8");
//    deque.insertBack("9");
    deque.insertFront("9");
    deque.insertFront("8");
    deque.insertFront("7");
    deque.insertFront("6");
    assertEquals(4, deque.length());
    deque.back();
    assertEquals(4, deque.length());
  }

  // insertFront & insertBack should have length + 1
  @Test
  @DisplayName("insertFront should increase length by 1 each time")
  public void lengthIncAfterInsertFront() {
    assertEquals(0, deque.length());
    deque.insertFront("1");
    assertEquals(1, deque.length());
    deque.insertFront("2");
    assertEquals(2, deque.length());
    deque.insertFront("3");
    assertEquals(3, deque.length());
    deque.insertFront("4");
    assertEquals(4, deque.length());
  }

  @Test
  @DisplayName("insertBack should increase length by 1 each time")
  public void lengthIncAfterInsertBack() {
    assertEquals(0, deque.length());
    deque.insertBack("9");
    assertEquals(1, deque.length());
    deque.insertBack("8");
    assertEquals(2, deque.length());
//    assertEquals(1, deque.length());
    deque.insertBack("7");
    assertEquals(3, deque.length());
//    assertEquals(2, deque.length());
    deque.insertBack("6");
    assertEquals(4, deque.length());
//    assertEquals(2, deque.length());
    deque.insertBack("5");
    assertEquals(5, deque.length());
//    assertEquals(3, deque.length());
    deque.insertBack("6");
    assertEquals(6, deque.length());
//    assertEquals(4, deque.length());
    deque.insertBack("7");
    assertEquals(7, deque.length());
//    assertEquals(4, deque.length());
    deque.insertBack("8");
    assertEquals(8, deque.length());
//    assertEquals(5, deque.length());
    deque.insertBack("9");
    assertEquals(9, deque.length());
//    assertEquals(6, deque.length());
    deque.insertBack("10");
    assertEquals(10, deque.length());
//    assertEquals(7, deque.length());
  }


  // removeFront & removeBack should have length - 1
  @Test
  @DisplayName("removeFront should decrease length by 1 each time")
  public void lengthDecAfterRemoveFront() {
    deque.insertFront("1");
    deque.insertFront("2");
    deque.insertFront("3");
    deque.insertFront("4");
    assertEquals(4, deque.length());
    deque.removeFront();
    assertEquals(3, deque.length());
    deque.removeFront();
    assertEquals(2, deque.length());
    deque.removeFront();
    assertEquals(1, deque.length());
    deque.removeFront();
    assertEquals(0, deque.length());
  }

  @Test
  @DisplayName("removeBack should decrease length by 1 each time")
  public void lengthDecAfterRemoveBack() {
    deque.insertFront("9");
    deque.insertFront("8");
    deque.insertFront("7");
    deque.insertFront("6");
    assertEquals(4, deque.length());
    deque.removeBack();
    assertEquals(3, deque.length());
    deque.removeBack();
    assertEquals(2, deque.length());
    deque.removeBack();
    assertEquals(1, deque.length());
    deque.removeBack();
    assertEquals(0, deque.length());
  }

  // Complex cases
  @Test
  @DisplayName("Test all functions in a Complex case: without InsertBack()")
  public void testComplexWithoutInsertBack() {
    assertEquals(0, deque.length());

    deque.insertFront("9");
    assertEquals("9", deque.front());
    assertEquals("9", deque.back());
    assertEquals(1, deque.length());

    deque.insertFront("8");
    assertEquals("8", deque.front());
    assertEquals("9", deque.back());
    assertEquals(2, deque.length());

    deque.insertFront("7");
    deque.removeFront();
    assertEquals("8", deque.front());
    assertEquals("9", deque.back());
    assertEquals(2, deque.length());

    deque.insertFront("6");
    deque.removeBack();
    assertEquals("6", deque.front());
    assertEquals("8", deque.back());
    assertEquals(2, deque.length());

    deque.insertFront("5");
    deque.insertFront("4");
    deque.insertFront("3");
    deque.insertFront("2");
    deque.insertFront("1");
    assertEquals("1", deque.front());
    assertEquals("8", deque.back());
    assertEquals(7, deque.length());

    deque.removeFront();
    deque.removeBack();
    assertEquals("2", deque.front());
    assertEquals("6", deque.back());
    assertEquals(5, deque.length());

    deque.removeFront();
    deque.removeFront();
    assertEquals("4", deque.front());
    assertEquals("6", deque.back());
    assertEquals(3, deque.length());

    deque.removeBack();
    deque.removeBack();
    assertEquals("4", deque.front());
    assertEquals("4", deque.back());
    assertEquals(1, deque.length());

    deque.removeFront();
    assertEquals(0, deque.length());
    assertTrue(deque.empty());
  }

  // Observe the behavior of InsertBack
  @Test
  @DisplayName("Experiments on Problematic InsertBack to find problems")
  public void ExperimentOnInsertBack() {
    deque.insertBack("1");
    assertEquals("1", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("2");
    // assertEquals("2", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("3");
    assertEquals("3", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("4");
    // assertEquals("4", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("5");
    assertEquals("5", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("6");
    assertEquals("6", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("7");
    // assertEquals("7", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("8");
    assertEquals("8", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("9");
    assertEquals("9", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("10");
    assertEquals("10", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("11");
    assertEquals("11", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("12");
    //assertEquals("12", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("13");
    assertEquals("13", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("14");
    assertEquals("14", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("15");
    assertEquals("15", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("16");
    assertEquals("16", deque.back());
    assertEquals("1", deque.front());
    deque.insertBack("17");
    assertEquals("17", deque.back());
    assertEquals("1", deque.front());


  }



// // other interesting & edges cases: perhaps withInsertBack (?)
//  @Test
//  @DisplayName("")
//  public void () {
//
//  }

}
