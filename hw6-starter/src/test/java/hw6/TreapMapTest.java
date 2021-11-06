package hw6;

import hw6.bst.TreapMap;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
@SuppressWarnings("All")
public class TreapMapTest extends BinarySearchTreeMapTest {


  @Override
  protected Map<String, String> createMap() {
    return new TreapMap<>(50);
  }

  // TODO Add tests
  //  (think about how you might write tests while randomness is involved in TreapMap implementation!)

  /* PLEASE README FIRST!!!:
  YOU need to follow the instruction in line 89 in TreapMap.java
  in order for this test to run!
  */

  // A sequence of randomly generated number between number 1 - 24 with seed = 50 is the following
  // 14 13 18 17 8 2 5 19 13 17 9 9 13 12 21 1 9 12 24 5 12 4 8 14 ...

  // insert
  @Test
  public void insertLeftRotation() {
    map.insert("c", "c");
    map.insert("d", "d");

    String[] expected = new String[]{
        "d:d:13",
        "c:c:14 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotation() {
    map.insert("b", "b");
    map.insert("a", "a");

    String[] expected = new String[]{
        "a:a:13",
        "null b:b:14"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertLeftRotationComplex() {
    map.insert("c", "c");
    map.insert("d", "d");
    map.insert("e", "e");
    map.insert("f", "f");
    String[] expected = new String[]{
        "d:d:13",
        "c:c:14 f:f:17",
        "null null e:e:18 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void insertRightRotationComplex() {
    map.insert("d", "d");
    map.insert("c", "c");
    map.insert("b", "b");
    map.insert("a", "a");

    String[] expected = new String[]{
        "c:c:13",
        "a:a:17 d:d:14",
        "null b:b:18 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

  @Test
  public void removeLeftRotation() {
    map.insert("a", "a");
    map.insert("c", "c");
    map.insert("d", "d");
    map.insert("b", "b");

    String s = map.remove("a");
    assertEquals(s, "a");
    String[] expected = new String[]{
        "c:c:13",
        "b:b:17 d:d:18"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeRightRotation() {
    map.insert("b", "b");
    map.insert("c", "c");
    map.insert("d", "d");
    map.insert("a", "a");

    String s = map.remove("b");
    assertEquals(s, "b");
    String[] expected = new String[]{
        "c:c:13",
        "a:a:17 d:d:18"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  // no structural change
  @Test
  public void insertNoRotation() {
    map.insert("c", "c");
    map.insert("d", "d");

    map.insert("e", "e");
    String[] expected = new String[]{
        "d:d:13",
        "c:c:14 e:e:18"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

  }

  @Test
  public void removeNoRotation() {
    map.insert("b", "b");
    map.insert("d", "d");
    map.insert("e", "e");
    map.insert("c", "c");

    String s = map.remove("c");
    assertEquals(s, "c");
    String[] expected = new String[]{
        "d:d:13",
        "b:b:14 e:e:18"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeLeaf() {
    map.insert("a", "a");
    map.insert("b", "b");
    map.insert("c", "c");
    map.insert("d", "d");
    map.insert("e", "e");

    String s = map.remove("c");
    assertEquals(s, "c");
    String[] expected = new String[]{
        "e:e:8",
        "b:b:13 null",
        "a:a:14 d:d:17 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeOneChild() {
    map.insert("a", "a");
    map.insert("b", "b");
    map.insert("c", "c");
    map.insert("d", "d");
    map.insert("e", "e");

    String s = map.remove("d");
    assertEquals(s, "d");
    String[] expected = new String[]{
        "e:e:8",
        "b:b:13 null",
        "a:a:14 c:c:18 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  public void removeTwoChildren() {
    map.insert("a", "a");
    map.insert("b", "b");
    map.insert("c", "c");
    map.insert("d", "d");
    map.insert("e", "e");

    String s = map.remove("b");
    assertEquals(s, "b");
    String[] expected = new String[]{
        "e:e:8",
        "a:a:14 null",
        "null d:d:17 null null",
        "null null c:c:18 null null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }


  // Test all: insert and remove under complex case
  @Test
  public void ComplexTestAll() {
    map.insert("a", "a");
    map.insert("b", "b");
    map.insert("c", "c");
    map.insert("d", "d");
    map.insert("e", "e");
    map.insert("f", "f");
    map.insert("g", "g");
    map.insert("h", "h");


    // 2 inserts
    map.insert("i", "i");
    String[] expected = new String[]{
        "f:f:2",
        "e:e:8 g:g:5",
        "b:b:13 null null i:i:13",
        "a:a:14 d:d:17 null null null null h:h:19 null",
        "null null c:c:18 null null null null null null null null null null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    map.insert("j", "j");
    expected = new String[]{
        "f:f:2",
        "e:e:8 g:g:5",
        "b:b:13 null null i:i:13",
        "a:a:14 d:d:17 null null null null h:h:19 j:j:17",
        "null null c:c:18 null null null null null null null null null null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

    String s = map.remove("f");
    assertEquals(s, "f");
    String[] afterRemove = new String[]{
        "g:g:5",
        "e:e:8 i:i:13",
        "b:b:13 null h:h:19 j:j:17",
        "a:a:14 d:d:17 null null null null null null",
        "null null c:c:18 null null null null null null null null null null null null null"
    };
    assertEquals((String.join("\n", afterRemove) + "\n"), map.toString());

    s = map.remove("g");
    assertEquals(s, "g");
    afterRemove = new String[]{
        "e:e:8",
        "b:b:13 i:i:13",
        "a:a:14 d:d:17 h:h:19 j:j:17",
        "null null c:c:18 null null null null null"
    };
    assertEquals((String.join("\n", afterRemove) + "\n"), map.toString());

    s = map.remove("e");
    assertEquals(s, "e");
    afterRemove = new String[]{
        "i:i:13",
        "b:b:13 j:j:17",
        "a:a:14 h:h:19 null null",
        "null null d:d:17 null null null null null",
        "null null null null c:c:18 null null null null null null null null null null null"
    };
    assertEquals((String.join("\n", afterRemove) + "\n"), map.toString());
  }

}