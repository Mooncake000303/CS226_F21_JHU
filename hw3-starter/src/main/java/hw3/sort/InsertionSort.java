package hw3.sort;


import hw3.list.IndexedList;

/**
 * The Insertion Sort algorithm, with minimizing swaps optimization.
 *
 * @param <T> Element type.
 */
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

  // is a less than b?
  private boolean less(T a, T b) {
    return a.compareTo(b) < 0;
  }

  @Override
  public void sort(IndexedList<T> indexedList) {
    int i = 1;
    while (i < indexedList.length()) {
      T x = indexedList.get(i); // the first element of unsorted portion
      int j = i - 1;
      while (j >= 0 && less(x, indexedList.get(j))) { // go through elements before x that are bigger than x
        indexedList.put(j + 1,indexedList.get(j)); // move each of them rightward by 1
        j--;
      }
      indexedList.put(j + 1,x); // j denotes the first element smaller than x
      i++;
    }
  }

  @Override
  public String name() {
    return "Insertion Sort";
  }
}
