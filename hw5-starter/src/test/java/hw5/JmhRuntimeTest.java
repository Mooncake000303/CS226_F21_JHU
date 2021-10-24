package hw5;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class JmhRuntimeTest {

  private List<Integer> data;
  // You may add more private fields in here.
  private final int LENGTH = 10000;
  private final int DIVFAC = 3;
  private final int SEARCHOPS = 3000;
  private final int LOOPINC = 10;

  @Setup(Level.Invocation)
  public void setUp() {
    data = new ArrayList<>();
    // You may update this method.
    //insertWithoutDuplicates(data);
    insertWithDuplicates(data);
    Collections.shuffle(data);
  }

  // Experiment: perform a sequence of operations on an implementation of Set ADT.
  private void experiment(Set<Integer> set) {
    //You may update this method.
//    expI(set);
//    expIS(set);
//    expIR(set);
    expISR(set);
  }

  private void insertWithoutDuplicates(List<Integer> data) {
    for (int i = 0; i < LENGTH; i++) {
      data.add(i);
    }
  }

  private void insertWithDuplicates(List<Integer> data) {
    for (int i = 0; i < LENGTH; i++) {
      data.add(i % DIVFAC);
    }
  }

  // insert alone
  private void expI(Set<Integer> set) {
    for (Integer num : data) {
      set.insert(num);
    }
  }

  // insert and search
  private void expIS(Set<Integer> set) {
    for (Integer num : data) {
      set.insert(num);
    }

    for (int i = 0; i < SEARCHOPS; i++) {
      for (int j = 0; j < LENGTH; j += LOOPINC) {
        set.has(j);
      }
    }
  }

  // insert and remove
  private void expIR(Set<Integer> set) {
    for (Integer num : data) {
      set.insert(num);
    }
    for (int i = 0; i < SEARCHOPS; i+=LOOPINC) {
      set.remove(i);
    }
  }

  // insert, search, and remove
  private void expISR(Set<Integer> set) {
    for (Integer num : data) {
      set.insert(num);
    }
    for (int i = 0; i < SEARCHOPS; i++) {
      for (int j = 0; j < LENGTH; j += LOOPINC) {
        set.has(j);
      }
    }
    for (int k = 0; k < SEARCHOPS; k +=LOOPINC) {
      set.remove(k);
    }
  }


  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void linkedSet() {
    // Do not change this method.
    Set<Integer> set = new LinkedSet<>();
    experiment(set);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void moveToFront() {
    // Do not change this method.
    Set<Integer> set = new MoveToFrontLinkedSet<>();
    experiment(set);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void arraySet() {
    // Do not change this method.
    Set<Integer> set = new ArraySet<>();
    experiment(set);
  }

  @Benchmark
  @Fork(value = 1, warmups = 1)
  @Warmup(iterations = 1)
  @Measurement(iterations = 2)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void transposeSequence() {
    // Do not change this method.
    Set<Integer> set = new TransposeArraySet<>();
    experiment(set);
  }
}
