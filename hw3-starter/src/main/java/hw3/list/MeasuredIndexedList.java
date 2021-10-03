package hw3.list;

import exceptions.IndexException;

/**
 * An ArrayIndexedList that is able to report the number of
 * accesses and mutations, as well as reset those statistics.
 *
 * @param <T> The type of the array.
 */
public class MeasuredIndexedList<T> extends ArrayIndexedList<T>
    implements Measured<T> {
  private int accessCounter;
  private int mutateCounter;

  /**
   * Constructor for a MeasuredIndexedList.
   *
   * @param size         The size of the array.
   * @param defaultValue The initial value to set every object to in the array..
   */
  public MeasuredIndexedList(int size, T defaultValue) {
    super(size, defaultValue);
    // TODO: Implement me
    accessCounter = 0;
    mutateCounter = 0;
  }

  @Override
  public int length() {
    return super.length();
  }

  @Override
  public T get(int index) {
    T result = super.get(index);
    accessCounter++;
    return result;
  }

  @Override
  public void put(int index, T value) throws IndexException {
    super.put(index,value);
    mutateCounter++;
  }

  @Override
  public void reset() {
    accessCounter = 0;
    mutateCounter = 0;
  }

  @Override
  public int accesses() {
    return accessCounter;
  }

  @Override
  public int mutations() {
    return mutateCounter;
  }

  @Override
  public int count(T value) {
    int count = 0;
    for (int i = 0; i < length(); i++) {
      if (value == get(i)) { // increment count only when the value is targeted value
        count++;
      }
    }
    return count;
  }

}
