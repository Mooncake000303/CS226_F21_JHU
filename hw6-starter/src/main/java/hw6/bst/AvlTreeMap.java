package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  private int height(Node<K, V> node) {
    if (node == null) {
      return -1;
    } else if (node.left == null && node.right == null) {
      return 0;
    } else {
      return Math.max(height(node.left), height(node.right)) + 1;
    }
  }

  private int balanceFactor(Node<K, V> node) {
    return height(node.left) - height(node.right);
  }

  // return 1 if left heavy, -1 if right heavy, and 0 if balanced
  private int unbalanced(Node<K, V> node) {
    int bf = balanceFactor(node);
    if (bf > 1) {
      return 1;
    } else if (bf < -1) {
      return -1;
    } else {
      return 0;
    }
  }

  private Node<K, V> singleLeft(Node<K, V> node) {
    Node<K, V> rightChild = node.right;
    node.right = rightChild.left;
    rightChild.left = node;
    node.height = height(node);
    rightChild.height = height(rightChild);
    return rightChild;
  }

  private Node<K, V> singleRight(Node<K, V> node) {
    Node<K, V> leftChild = node.left;
    node.left = leftChild.right;
    leftChild.right = node;
    node.height = height(node);
    leftChild.height = height(leftChild);
    return leftChild;
  }

  private Node<K, V> leftRight(Node<K, V> node) {
    node.left = singleLeft(node.left);
    return singleRight(node);
  }

  private Node<K, V> rightLeft(Node<K, V> node) {
    node.right = singleRight(node.right);
    return singleLeft(node);
  }

  private Node<K, V> balanceThis(Node<K, V> node) {
    int blDirection = unbalanced(node);
    if (blDirection == 1) {
      if (balanceFactor(node.left) >= 0) { // LL, single right rotation
        node = singleRight(node);
      } else { // LR, left right rotation
        node = leftRight(node);
      }
    } else if (blDirection == -1) {
      if (balanceFactor(node.right) <= 0) { // RR, single left rotation
        node = singleLeft(node);
      } else { // RL, right left rotation
        node = rightLeft(node);
      }
    }
    return node;
  }

  private Node<K, V> insert(Node<K,V> n, K k, V v) {
    if (n == null) {
      return new Node<>(k, v);
    }

    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }

    if (unbalanced(n) != 0) {
      n = balanceThis(n);
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
    subtreeRoot = balanceThis(subtreeRoot);
    return subtreeRoot;
  }

  // Remove given node and return the remaining tree (structural change).
  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // If it has two children, find the predecessor (max in left subtree),
    Node<K, V> toReplaceWith = max(node);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);
    node = balanceThis(node);
    return node;
  }

  // Return a node with maximum key in subtree rooted at given node.
  private Node<K, V> max(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
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

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

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
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int height;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      height = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
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