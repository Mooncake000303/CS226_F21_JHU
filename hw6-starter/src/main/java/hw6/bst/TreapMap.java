package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Map implemented as a Treap.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class TreapMap<K extends Comparable<K>, V>
    implements OrderedMap<K, V> {

  /*** Do not change variable name of 'rand'. ***/
  private static Random rand;
  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;
  private int maxPriority;

  /**
   * Make a TreapMap.
   */
  public TreapMap() {
    rand = new Random();
  }

  /**
   * Make a TreapMap with the specific seed.
   * @param seed The seed value
   */
  public TreapMap(int seed) {
    rand = new Random(seed);
  }

  private void newMax(Node<K, V> node) {
    if (size == 0) {
      maxPriority = node.priority;
    } else {
      if (node.priority > maxPriority) {
        maxPriority = node.priority;
      }
    }
  }

  // return 1 if need rotateLeft, return -1 if need rotateRight, return 0 if no rotation is needed
  private int needRotation(Node<K,V> node) {
    if (node.right != null && node.right.priority < node.priority) {
      // right is better than parent, rotateLeft
      return 1;
    }
    if (node.left != null && node.left.priority < node.priority) {
      // left is better than parent, rotateRight
      return -1;
    }
    return 0;
  }

  private Node<K,V> rotateLeft(Node<K,V> node) {
    Node<K, V> rightChild = node.right;
    node.right = rightChild.left;
    rightChild.left = node;
    return rightChild;
  }

  private Node<K,V> rotateRight(Node<K,V> node) {
    Node<K, V> leftChild = node.left;
    node.left = leftChild.right;
    leftChild.right = node;
    return leftChild;
  }

  private Node<K, V> rotateThis(Node<K, V> node) {
    if (needRotation(node) == 1) {
      node = rotateLeft(node);
    } else if (needRotation(node) == -1) {
      node = rotateRight(node);
    }
    return node;
  }

  // Insert given key and value into subtree rooted at given node;
  // return changed subtree with a new node added.
  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
      // [functioning code]
      Node<K, V> insertNode = new Node<>(k, v);

      // [testing]: uncomment this and comment [functioning code] to run Treapmap.java
      // Node<K, V> insertNode = new Node<>(k, v, 24);

      newMax(insertNode);
      return insertNode;
    }

    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }

    if (needRotation(n) != 0) { // rotate only when needed
      n = rotateThis(n);
    }
    return n;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    V priorValue = node.value;
    root = remove(root, node);
    size--;
    return priorValue;
  }

  // Remove node with given key from subtree rooted at given node;
  // Return changed subtree with given key missing.
  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }
    subtreeRoot = rotateThis(subtreeRoot);
    return subtreeRoot;
  }

  private Node<K, V> remove(Node<K, V> node) {
    if (node.left == null) {
      return node.right;
    } else if (node.right == null) {
      return node.left;
    }

    node.priority = maxPriority + 1; // change the priority of to-remove node
    Node<K, V> sbr = rotateThis(node);
    node = remove(sbr, node);
    return node;
  }


  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    return n.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  // Return node for given key.
  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  // Return node for given key,
  // throw an exception if the key is not in the tree.
  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  // Iterative in-order traversal over the keys
  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }


  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers. Since this is
   * a node class for a Treap we also include a priority field.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int priority;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      priority = generateRandomInteger();
    }

    // Constructor to make node with a specific seed
    Node(K k, V v, int limit) {
      key = k;
      value = v;
      priority = generateRandIntForTesting(limit);
    }

    // Use this function to generate random values
    // to use as node priorities as you insert new
    // nodes into your TreapMap.
    private int generateRandomInteger() {
      return rand.nextInt();
    }

    // randomly generated number start with 1 - limit
    private int generateRandIntForTesting(int limit) {
      return 1 + rand.nextInt(limit);
    }

    @Override
    public String toString() {
      return key + ":" + value + ":" + priority;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }
  }
}