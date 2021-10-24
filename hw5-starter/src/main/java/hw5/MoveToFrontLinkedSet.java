package hw5;

/**
 * Set implemented using a doubly linked list and move-to-front heuristic.
 *
 * @param <T> Element type.
 */
public class MoveToFrontLinkedSet<T> extends LinkedSet<T> {

  // TODO: incorporate move-to-front heuristic each time a value is accessed.
  //  Override the relevant method(s) from LinkedSet.

  /**
   * A Driver program for MoveToFrontLinkedSet (insert & remove & has).
   *
   * @param args No input arguments.
   */
  public static void main(String[] args) {
    MoveToFrontLinkedSet<Integer> ls = new MoveToFrontLinkedSet<>();
    ls.insert(1);
    ls.insert(2);
    ls.insert(3);
    ls.insert(4);
    ls.insert(3);

    ls.has(2);
    ls.has(1);
    ls.has(3);
    ls.has(3);
    ls.has(4);
    ls.has(24);
    ls.has(2);

    ls.remove(2);
    ls.remove(24);
    ls.remove(3);
    ls.remove(2);
    ls.remove(1);
  }

  // Pre: target != null
  private void addBefore(Node<T> target) {
    target.next = this.head;
    target.prev = null;
    super.head.prev = target;
    super.head = target;
  }

  // Pre: target != null
  // No need to switch if only one element or already front
  private void moveToFront(Node<T> target) {
    if (!(super.head.data.equals(target.data) || super.numElements <= 1)) {
      super.remove(target);
      addBefore(target);
    }
  }

  // moveToFront during search: if found, moveToFront
  @Override
  protected Node<T> find(T t) {
    Node<T> target = super.find(t);
    if (target != null) {
      moveToFront(target);
    }
    return target;
  }
}
