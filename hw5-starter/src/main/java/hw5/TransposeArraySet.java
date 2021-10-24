package hw5;

/**
 * Set implemented using plain Java arrays and transpose-sequential-search heuristic.
 *
 * @param <T> Element type.
 */
public class TransposeArraySet<T> extends ArraySet<T> {

  // TODO: incorporate the transpose-sequential-search heuristic
  //  each time a value is accessed. Override the relevant method(s) from ArraySet.
  /**
   * A Driver program for TransposeArraySet (insert & remove & has).
   *
   * @param args No input arguments.
   */
  public static void main(String[] args) {
    TransposeArraySet<Integer> as = new TransposeArraySet<>();
    as.insert(1);
    as.insert(2);
    as.insert(3);
    as.insert(4);
    as.insert(3);

    as.has(2);
    as.has(1);
    as.has(3);
    as.has(3);
    as.has(4);
    as.has(24);
    as.has(2);

    as.remove(2);
    as.remove(24);
    as.remove(3);
    as.remove(2);
    as.remove(1);
  }

  // Pre: 0 < index < numElements
  private void transposeSeq(int index) {
    if (super.numElements > 1) {
      T before = super.data[index - 1];
      super.data[index - 1] = super.data[index];
      super.data[index] = before;
    }
  }

  @Override
  protected int find(T t) {
    int index = super.find(t);
    if (index > 0) {
      transposeSeq(index);
      return index - 1;
    }
    return index;
  }
}
