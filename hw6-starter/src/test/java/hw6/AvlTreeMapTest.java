package hw6;

import hw6.bst.AvlTreeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new AvlTreeMap<>();
  }

  // insert
  @Test
  public void insertLeftRotation() {
    map.insert("1", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a
     */

    map.insert("2", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a,
        null 2:b
     */

    map.insert("3", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        2:b,
        1:a 3:c
     */

    String[] expected = new String[]{
        "2:b",
        "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  // TODO Add more tests
  @Test
  public void insertRightRotationBasic() {
    map.insert("7", "c");
    map.insert("5", "b");
    map.insert("3", "a");
    String[] expected = new String[]{
        "5:b",
        "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRightRotationBasic() {
    map.insert("7", "c");
    map.insert("3", "a");
    map.insert("5", "b");

    String[] expected = new String[]{
        "5:b",
        "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftRotationBasic() {
    map.insert("3", "a");
    map.insert("7", "c");
    map.insert("5", "b");

    String[] expected = new String[]{
        "5:b",
        "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRotationComplex() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("5", "e");
    map.insert("4", "d");
    map.insert("6", "f");

    map.insert("7", "g");
    String[] expected = new String[]{
        "5:e",
        "3:c 6:f",
        "2:b 4:d null 7:g"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotationComplex() {
    map.insert("5", "e");
    map.insert("3", "c");
    map.insert("6", "f");
    map.insert("2", "b");
    map.insert("4", "d");

    map.insert("1", "a");
    String[] expected = new String[]{
        "3:c",
        "2:b 5:e",
        "1:a null 4:d 6:f"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRightRotationComplex() {
    map.insert("5", "e");
    map.insert("2", "b");
    map.insert("6", "f");
    map.insert("1", "a");
    map.insert("4", "d");

    map.insert("3", "c");
    String[] expected = new String[]{
        "4:d",
        "2:b 5:e",
        "1:a 3:c null 6:f"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightLeftRotationComplex() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("6", "f");
    map.insert("5", "e");
    map.insert("7", "g");

    map.insert("4", "d");
    String[] expected = new String[]{
        "5:e",
        "3:c 6:f",
        "2:b 4:d null 7:g"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  // remove
  @Test
  public void removeLeftRotation() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("5", "e");
    map.insert("1", "a");
    map.insert("4", "d");
    map.insert("6", "f");
    map.insert("7", "g");

    String s = map.remove("1");
    assertEquals(s, "a");
    String[] expected = new String[]{
        "5:e",
        "3:c 6:f",
        "2:b 4:d null 7:g"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeRightRotation() {
    map.insert("5", "e");
    map.insert("3", "c");
    map.insert("6", "f");
    map.insert("2", "b");
    map.insert("4", "d");
    map.insert("7", "g");
    map.insert("1", "a");

    String s = map.remove("7");
    assertEquals(s, "g");

    String[] expected = new String[]{
        "3:c",
        "2:b 5:e",
        "1:a null 4:d 6:f"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeLeftRightRotation() {
    map.insert("5", "e");
    map.insert("2", "b");
    map.insert("6", "f");
    map.insert("1", "a");
    map.insert("4", "d");
    map.insert("7", "g");
    map.insert("3", "c");

    String s = map.remove("7");
    assertEquals(s, "g");

    String[] expected = new String[]{
        "4:d",
        "2:b 5:e",
        "1:a 3:c null 6:f"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeRightLeftRotation() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("6", "f");
    map.insert("1", "a");
    map.insert("5", "e");
    map.insert("7", "g");
    map.insert("4", "d");

    String s = map.remove("1");
    assertEquals(s, "a");
    String[] expected = new String[]{
        "5:e",
        "3:c 6:f",
        "2:b 4:d null 7:g"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  // insert, no structural change
  @Test
  public void insertNoRotationBasic() {
    map.insert("5", "b");
    map.insert("7", "c");

    map.insert("3", "a");
    String[] expected = new String[]{
        "5:b",
        "3:a 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertNoRotationComplex() {
    map.insert("4", "d");
    map.insert("2", "b");
    map.insert("7", "g");
    map.insert("1", "a");
    map.insert("3", "c");
    map.insert("6", "f");
    map.insert("8", "h");

    map.insert("5", "e");
    String[] expected = new String[]{
        "4:d",
        "2:b 7:g",
        "1:a 3:c 6:f 8:h",
        "null null null null 5:e null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  // remove, no structural change
  @Test
  public void removeNoRotationBasic() {
    map.insert("5", "b");
    map.insert("7", "c");
    map.insert("3", "a");

    String s = map.remove("3");
    assertEquals(s, "a");
    String[] expected = new String[]{
        "5:b",
        "null 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeNoRotationComplex() {
    map.insert("4", "d");
    map.insert("2", "b");
    map.insert("7", "g");
    map.insert("1", "a");
    map.insert("3", "c");
    map.insert("6", "f");
    map.insert("8", "h");
    map.insert("5", "e");

    String s = map.remove("7");
    assertEquals(s, "g");
    String[] expected = new String[]{
        "4:d",
        "2:b 6:f",
        "1:a 3:c 5:e 8:h"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  // remove, leaf
  @Test
  public void removeLeaf() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");
    map.insert("4", "d");
    map.insert("5", "e");
    map.insert("6", "f");
    map.insert("7", "g");

    String s = map.remove("7");
    assertEquals(s, "g");
    String[] expected = new String[]{
        "1:a",
        "2:b 3:c",
        "4:d 5:e 6:f null"
    };
  }

  // remove, one child
  @Test
  public void removeOneChild() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");
    map.insert("4", "d");
    map.insert("5", "e");
    map.insert("6", "f");

    String s = map.remove("3");
    assertEquals(s, "c");
    String[] expected = new String[]{
        "1:a",
        "2:b 6:f",
        "4:d 5:e null null"
    };
  }

  // remove, two children
  @Test
  public void removeTwoChildren() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");
    map.insert("4", "d");
    map.insert("5", "e");
    map.insert("6", "f");
    map.insert("7", "g");

    String s = map.remove("3");
    assertEquals(s, "c");
    String[] expected = new String[]{
        "1:a",
        "2:b 6:f",
        "4:d 5:e null null"
    };
  }

  /* Complex cases is starting here */
  // multiple insertion
  @Test
  public void insertMultiple() {
    map.insert("4", "d");
    map.insert("1", "a");
    map.insert("6", "f");
    map.insert("8", "h");
    map.insert("7", "g");
    map.insert("5", "e");


    String[] expected = new String[]{
        "6:f",
        "4:d 7:g",
        "1:a 5:e null 8:h"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  // multiple removal
  @Test
  public void removeMultiple() {
    map.insert("4", "d");
    map.insert("1", "a");
    map.insert("6", "f");
    map.insert("8", "h");
    map.insert("7", "g");
    map.insert("5", "e");

    String s = map.remove("6");
    assertEquals(s, "f");
    String[] expected = new String[]{
        "5:e",
        "4:d 7:g",
        "1:a null null 8:h"
    };

    s = map.remove("5");
    assertEquals(s, "e");
    expected = new String[]{
        "4:d",
        "1:a 7:g",
        "null null null 8:h"
    };

    s = map.remove("4");
    assertEquals(s, "d");
    expected = new String[]{
        "1:a",
        "7:g 8:h"
    };
  }

  // Test all: complex case: multiple insertion and removal
  @Test
  public void ComplexTestAll() {
    // two inserts
    map.insert("5", "e");
    map.insert("3", "c");
    map.insert("7", "g");
    map.insert("2", "b");
    map.insert("1", "a");
    String[] expected = new String[]{
        "5:e",
        "2:b 7:g",
        "1:a 3:c null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.insert("8", "h");
    map.insert("9", "i");
    expected = new String[]{
        "5:e",
        "2:b 8:h",
        "1:a 3:c 7:g 9:i"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    // three removes
    String s = map.remove("2");
    assertEquals(s, "b");
    String[] afterRemove = new String[]{
        "5:e",
        "1:a 8:h",
        "null 3:c 7:g 9:i"
    };
    assertEquals((String.join("\n", afterRemove) + "\n"), map.toString());

    s = map.remove("3");
    assertEquals(s, "c");
    afterRemove = new String[]{
        "5:e",
        "1:a 8:h",
        "null null 7:g 9:i"
    };
    assertEquals((String.join("\n", afterRemove) + "\n"), map.toString());

    s = map.remove("5");
    assertEquals(s, "e");
    afterRemove = new String[]{
        "8:h",
        "1:a 9:i",
        "null 7:g null null"
    };
    assertEquals((String.join("\n", afterRemove) + "\n"), map.toString());
  }

}
