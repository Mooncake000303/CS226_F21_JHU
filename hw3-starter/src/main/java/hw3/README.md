# Discussion

## PART I: MEASURED IndexedList

1. **Discuss from a design perspective whether or not iterating over a `MeasuredIndexedList` should affect the accesses and mutation counts. Note that for the purposes of this assignment we are NOT asking you to rewrite the `ArrayIndexedListIterator` to do so. However, if you wanted to include the `next()` and/or `hasNext()` methods in the statistics measured, can you inherit `ArrayIndexedListIterator` from `ArrayIndexedList` and override the relevant methods, or not? Explain.**

In this actual case, the iterator does NOT affect the access and mutation counts,
since it inherits Iterator from ArrayIndexList, and it is not related to
get() and put(), which is the only source for change of access counters
and mutation counters, respectively.
From a design perspective though, I think it's better to keep access counter and
mutation counter unchanged when iterating through a MeasuredIndexedList. 
Since the MeasuredIndexedList is used to help measure access and mutation counts in
other algorithms, the counts should measure what is actually caused by the algorithms.
So when iterating through a list, access counters shoule be kept unchanged to reflect the true
access counts of an algorithm, and mutation counters should not change because a simple iterate
-through does not change the value of the list.

You can inherit ArrayIndexedListIterator from ArrayIndexedList and override, because 
MeasuredIndexedList extends ArrayIndexedList, which extends IndexedList which extends Iterable<T>.
The Iterator<T> iterator() in ArrayIndexedList is public and not static, which makes its children class
have access to inherit and override it.



## PART II: ALL SORT OF SORTS

1. **Explain the mistake in the setup/implementation of the experiment is set up and implemented which resulted in a discrepancy between the results and what is expected from each sorting algorithm.**

When I run the experiment using SortingAlgorithmDriver several times, I found something weird is going on. 
In SortingAlgorithmDriver.java, the output sentence is `String.format("%-17s %-16s %-8b %,-12d %,-12d %,-12f\n",
dataFile, algo, isSorted((IndexedList<String>) array), array.accesses(), array.mutations(), time))`, so it is
outputing an already-sorted array, which could have positive access count. But it should not have positive mutation 
count. Instead, the mutation count is expected to be 0 since the array is already sorted. Since put() is the only source 
of mutation count, I search for put() in all these sorts and locate the problem to "swap". Using a debugger, 
I found the statement that is problematic is at the if (less(a,b)){swap} statement. Sometimes 
even though a is greater than b in "value", i.e. when it's already sorted, it still go into the if statement and 
perform the swap. Recalling that in the file SortingAlgorithmDriver, the data is read in as String. So the problem lies 
in using a.compareTo(b) to construct the less() function. Integers can be correctly compared using CompareTo, but 
Strings can not be correctly compare about their "value" using compareTo. For example, when input as ints, we
can expect compareTo(24, 156) should be < 0. But when read in as "24" and "156", the compareTo("24", "156") will compare
according to ASCII and return a value > 0, which is problematic.
To sum up, the compareTo() in less() function can only compare integers, while the data is read in as String type
in SortingAlgorithmDriver, causing incorrect comparison and thus fail our expectation that a sorted list should
have a mutation count of 0.

2. **Does the actual running time correspond to the asymptotic complexity as you would expect?**

Here are the result one of my experiments. I run 5 times for each of different N values and get the average.

When N = 50,
```
Data file         Algorithm        Sorted?  Accesses     Mutations    Seconds     

ascending.data    Null Sort        false    0            0            0.000003    
ascending.data    Gnome Sort       true     1,658        520          0.001540    
ascending.data    Selection Sort   true     2,542        92           0.001944    
ascending.data    Bubble Sort      true     1,330        520          0.001026    
ascending.data    Insertion Sort   true     618          309          0.000141    

descending.data   Null Sort        false    0            0            0.000001    
descending.data   Gnome Sort       true     7,350        2,450        0.000697    
descending.data   Selection Sort   true     2,500        50           0.000123    
descending.data   Bubble Sort      true     4,900        2,450        0.000492    
descending.data   Insertion Sort   true     2,499        1,274        0.000109    

random.data       Null Sort        false    0            0            0.000001    
random.data       Gnome Sort       true     3,544        1,150        0.000170    
random.data       Selection Sort   true     2,540        90           0.000134    
random.data       Bubble Sort      true     3,444        1,150        0.000175    
random.data       Insertion Sort   true     1,246        624          0.000076
```
When N = 500,
```
Data file         Algorithm        Sorted?  Accesses     Mutations    Seconds     

ascending.data    Null Sort        false    0            0            0.000004    
ascending.data    Gnome Sort       true     185,558      61,520       0.008896    
ascending.data    Selection Sort   true     250,488      988          0.008785    
ascending.data    Bubble Sort      true     149,818      61,520       0.005289    
ascending.data    Insertion Sort   true     62,518       31,259       0.003285    

descending.data   Null Sort        false    0            0            0.000001    
descending.data   Gnome Sort       true     748,500      249,500      0.010532    
descending.data   Selection Sort   true     250,000      500          0.003624    
descending.data   Bubble Sort      true     499,000      249,500      0.004693    
descending.data   Insertion Sort   true     249,999      125,249      0.005675    

random.data       Null Sort        false    0            0            0.000001    
random.data       Gnome Sort       true     379,402      126,136      0.002336    
random.data       Selection Sort   true     250,480      980          0.000999    
random.data       Bubble Sort      true     375,594      126,136      0.011753    
random.data       Insertion Sort   true     127,132      63,567       0.005415 
```

When N = 5000
```
Data file         Algorithm        Sorted?  Accesses     Mutations    Seconds     

ascending.data    Null Sort        false    0            0            0.000004    
ascending.data    Gnome Sort       true     18,886,058   6,292,020    0.074002    
ascending.data    Selection Sort   true     25,004,988   9,988        0.100276    
ascending.data    Bubble Sort      true     15,267,014   6,292,020    0.051871    
ascending.data    Insertion Sort   true     6,302,018    3,151,009    0.033488    

descending.data   Null Sort        false    0            0            0.000001    
descending.data   Gnome Sort       true     74,985,000   24,995,000   0.211217    
descending.data   Selection Sort   true     25,000,000   5,000        0.087491    
descending.data   Bubble Sort      true     49,990,000   24,995,000   0.123279    
descending.data   Insertion Sort   true     24,999,999   12,502,499   0.115365    

random.data       Null Sort        false    0            0            0.000002    
random.data       Gnome Sort       true     37,476,550   12,488,852   0.147336    
random.data       Selection Sort   true     25,004,980   9,980        0.087598    
random.data       Bubble Sort      true     37,477,046   12,488,852   0.176657    
random.data       Insertion Sort   true     12,498,848   6,249,425    0.027614    
```
[Gnome Sort]

**ascending** T(500) / T(50) = 5.77662 ；T(5000) / T(50) = 48.0532

**descending** T(500) / T(50) = 15.1105；T(5000) / T(50) = 304.405

**random** T(500) / T(50) = 13.7412；T(5000) / T(50) = 866.682

[Selection sort]

**ascending** T(500) / T(50) = 4.51；T(5000) / T(50) = 71.4228

**descending** T(500) / T(50) = 5.576；T(5000) / T(50) = 60.8938

**random** T(500) / T(50) = 3.8673；T(5000) / T(50) = 50.783

[Bubble sort]

**ascending** T(500) / T(50) = 3.5489；T(5000) / T(50) = 25.82

**descending** T(500) / T(50) = 19.783；T(5000) / T(50) = 704.763

**random** T(500) / T(50) = 23.48；T(5000) / T(50) = 576.23

[Insertion sort]

**ascending** T(500) / T(50) = 3.56；T(5000) / T(50) = 10.49

**descending** T(500) / T(50) = 18.32；T(500) / T(50) = 236.73

**random** T(500) / T(50) = 11.23；T(5000) / T(50) = 123.65

Note that although the general trends matches, i.e. if there's less data to deal with, the sorting takes shorter time.
However, it does NOT quite matches the asymptotic trend of O(N^2). While T(5000) / T(50) should be 100^2, and
T(500) / T(50) = 10 ^ 2, almost all of real results is smaller. 
One thing that can explain this is that the data we are processing are not necessarily worst-case, so it makes sense
that the result is smaller.

3. **What explains the practical differences between these algorithms? (Theoretically, the algorithm runs in O(X) time, where X is a function of the input size, but in practice (i.e running it on datasets), you may observe that it is slower/faster compared to the other algorithms)**

Because some sets of data might not be the worst-case for certain algorithms, and therefore runs faster. 
For example, when the data is not reversely ordered, the bubbleSort algorithm will not achieve its worst case
time complexity of O(N^2).

4. **Does it matter what kind of data (random, already sorted in ascending order, sorted in descending order) you are sorting? How should each algorithm behave (in terms of performance) based on the type of data it receives?**

Yes. Let us arbitrarily assume we want ascending order data. 

Bubble sort behaves the best (O(N)) when the data is almost / already sorted, i.e ascending data
and behave the worst when the data is reversely sorted, i.e descending data.

Insertion sort behaves the best (O(N)) when the data is almost / already sorted, i.e ascending data
and behave the worst when the data is reversely sorted, i.e descending data.

Genome sort behaves the best (O(N)) behaves the best (O(N)) when the data is almost / already sorted, i.e ascending data
and behave the worst when the data is reversely sorted, i.e descending data.

Selection sort always perform O(n) swaps no matter what the data is like.

## PART III: ANALYSIS OF SELECTION SORT

1. Determine exactly how many comparisons C(n) and assignments A(n) are performed by this implementation of selection sort in the worst case. 
   Both of those should be polynomials of degree 2 since you know that the asymptotic complexity of selection sort is O(n^2).
   
NOTE: Please swipe to the END OF RIGHT to see full explanations line by line!
```java
public static void starter selectionSort(int[] a) { // let a.length = n
    int max, temp;                                  // A(n) = 0, C(n) = 0;
    for (int i = 0; i < a.length - 1; i++) {        // A(n) = 2, C(n) = 1. (A: int i = 0; i++) (C: i < a.length - 1)    // Multiple = n [N: (n-1) - 0 + 1 = n] "outer for-loop"
        max = i;                                    // A(n) = 1, C(n) = 0. (A: max = i) (C: )                           // Multiple = n [N: same as outer for-loop]
        for (int j = i + 1; j < a.length; j++) {    // A(n) = 2, C(n) = 1. (A: j = i + 1; j++) (C: j < a.length)        // Multiple = n (n - 1) / 2 [N: n - (i + 1), where i goes from 0 to (n - 1), so n - 1, n-2, ... 1, 0] "Inner for-loop"
            if (a[j] > a[max]) {                    // A(n) = 0, C(n) = 1. (A: ) (C: a[j] > a[max])                     // Multiple = n (n - 1) / 2 [N: same as inner for-loop]
                max = j;                            // A(n) = 1, C(n) = 0. (A: max = j) (C: )                           // Multiple = n (n - 1) / 2 [N: same as inner for-loop]
            }
        }
        temp = a[i];                                // A(n) = 1, C(n) = 0. (A: temp = a[i]) (C: )                       // Multiple = n [N: same as outer for-loop]
        a[i] = a[max];                              // A(n) = 1, C(n) = 0. (A: a[i] = a[max]) (C: )                     // Multiple = n [N: same as outer for-loop]
        a[max] = temp;                              // A(n) = 1, C(n) = 0. (A: a[max] = temp) (C: )                     // Multiple = n [N: same as outer for-loop]
    }
}
```

A(n) = [ Line 3 + Line 4 + Line 5 + Line 7 + Line 10 + Line 11 + Line 12 ]\
= 2n + 1n + 2n(n-1)/2 + 1n(n-1)/2 + 1n + 1n + 1n\
= 3/2 n^2 + 9/2 n

C(n) = [ Line 3 + Line 5 + Line 6 ]\
= 1n + 1n(n-1)/2 + 1n(n-1)/2\
= n^2