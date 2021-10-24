# Discussion

## Flawed Deque
*In the README.md file, please 1) give a list of the flaws you found in our implementation 
and 2) list the unit tests (using the names you gave them) that uncovered each flaw. 
For each flaw you identify, please 3) share an "educated guess" as to the mistake that led to the flaw.*

**1. back() does not throw EmptyException when Deque is empty.**

I found this flaw in `backThrowsEmptyException()`, where it fails this test. \
*My educated guess*:\
The result is shown as `exceptions.LengthException`. Obviously it means when the Deque is empty,
instead of throwing an `EmptyException`, the wrong exception `LengthException`is thown in
back().
   
**2. removeFront() and removeBack() does not throw EmptyException when Deque is empty.**

I found these removeFront() and removeBack() does not throw EmptyException when Deque is empty 
in `removeFrontThrowsEmptyException()` and `removeBackThrowsEmptyException()`, respectively.\
*My educated guess*:\
For both these two flaws, no exception is reported. This means the Exception case
is forgotten to be handled, i.e., when Deque is empty, instead of throwing `EmptyException`,
the removeFront() and removeBack() does not throw any kind of exception.
   
**3. insertBack() fail, capacity doubling is unsuccessful**

[I.] I first found insertBack() is problematic when testing `backNonEmpty()`.
When returning the back of non-empty deques that is build out of a series 
of insertFront() ONLY, back() behaves correctly. When the construction of
non-empty deques is involved with insertBack(), back() does not return what
is expected. That is very surprising because back() should be rather simple.
Therefore I suspect there's something wrong during the construction of
the non-empty deque, so the InsertBack() could be problematic since it could be much more complicated than back().\
[II.] To make sure I'm correct, I go on test on the changes in the length of the 
deque after insertBack() through `lengthIncAfterInsertBack()`. Without any surprises, it failed. I compare the value
of expected length and actual length and found a very weird behavior. I insertBack()
to the deque 9 times, instead of showing a pattern of
`1 2 3 4 5 6 7 8 9`, it shows a weird pattern of `1 1 2 2 3 4 4 5 6 `.
This reflects the length changing ininsertBack() is problematic.\
[III.] Before finding the final problem of insertBack(), I wrote a test on a complex and comprehensive
test case in `testComplexWithoutInsertBack()` on all other functions 
except insertBack(), i.e., all insertions are done by insertFront(). 
Indeed the Complex case passes and I'm perfectly sure now 
that the problem narrows down to InsertBack(). Therefore, I also go back and change deque
contructions that once relied on insertBack() to be solely insertFront() so that
the testing of other functionalities, will not be affected by the wrongness of insertBack(),
like those in frontNonEmpty(), and backNonEmpty().\
[IV.] Finally, in `ExperimentOnInsertBack()`, I experiment on InsertBack() and check the back() value it returns when
inserted back n times, n = 1, 2, 3, ... 17. And insertBack() fails expectation
on n = 2, 4, 7, 12. In all of those cases back() returns the string inserted just before this
insertBack(). These means insertions on n = 2, 4, 7, 12 are unsuccessful,
meaning it's missing 1, 2, 3, and 4 insertions respectively.
With a little math we know when there shuold be 2, 4, 7, 12 insertions,
there is only (2-1), (4-2), (7-3), (12-4) = 1, 2, 4, 8 insertions, a series of power of 2.
Combining with my earlier discovery in `lengthIncAfterInsertBack()`, it leads to the following
conclusion.\
*My educated guess*:\
With all the analysis above, I guess the deque is implemented as a dynamically-grown array 
and it's length needs to be doubled when the capacity is full. 
However, insertBack() fails to double the capacity correctly, leading to
the failures in back-insertions and length updates.

## Hacking Linear Search

*please note if you encounter any issues, unexpected/surprising,
or interesting observations as you execute the main method step by step. 
Hint: look more carefully at the values after removal!*

What I'm not expecting initially is the following.\
A.1）For `MoveToFrontLinkedSet`, 
when trying to insert a duplicate to set, the element will not be inserted.
Instead, it will bring the duplicated value to the front.\
A.2) For `TransposeArraySet.java`,
when trying to insert a duplicate to set, the element will not be inserted.
Instead, it will swap the duplicated value with the one before it.\
B) For `TransposeArraySet.java`, The behavior of `remove()` is not only removes the element, but also affect
ordering of elements after the removed one.

For A), this is obvious after a little bit of thinking.
Set does not allow duplicate values, so the correct insert
should never insert a duplicate in, and since insert calls "renovated" find method,
and find also call moveToFront() and transposeSeq() respectively.
That's why the order will be changed, and so for `MoveToFrontLinkedSet.java`,
insert will bring the duplicated value to the front and
for `TransposeArraySet.java`, insert will swap the duplicated value with the one before it.


For B), when debugging through `TransposeArraySet.java`, 
I found that whenever an element is removed, 
the elements after it has some kind of unexpected order
changes. For example, in my main I have the following result:
```
3 1 2 4     [Original]
(remove 2)
3 4 1       [Expected: 3 1 4]
(remove 3)
4 1         [Expected: 1 4]
```
Taking a closer look, I found there's actually an deeper
reason lying behind this. `remove()` calls the "renovated" 
`find()`, besides finding the element, also calls `transposeSeq()` because
we are applying the transpose-sequential search here. So,
it would exchange the order of the removed element 
with the element before it. But this still does not
explain why the element after the removed element has order changes.
This is because the remove() approach we take for a set is
exchanging the last element with removed element.
So for the above example, the process under the hood is
actually`3 1 2 4` becomes `3 2 1 4` and finally
exchange `2` with `4` ,`2` is removed, so we have `3 4 1`.
Very intriguing indeed.

## Profiling

*In your README, please describe the experiment and setup 
which you have used. Please discuss the results from using 
the heuristic "improvements" to the Set implementations. 
Were there any noticeable differences compared to the 
implementations we provided (ArraySet and LinkedSet)? 
If they didn't perform as expected, 
why do you think that was the case? 
Were your data sets big enough? 
Suppose you didn't see any measurable differences.
What do you think it would take to measure the
relative performance of each implementation?*

Here I approach this using a control-of-variable approach
to find which condition that heuristics would
give tellable improvements to the performance of Array/Linked Set.

Since `insert` is needed for the initial construction,
we can not eliminate/avoid `insert` in all of our processes.
Therefore, there are 4 general conditions as followed.
1. expI: Insert alone
2. expIS: Insert, Search
3. expIR: Insert, Remove
4. expISR: Insert, Search, Remove

Also, the data could be different
1. without duplicates
2. with duplicates

So in total, 4 * 2 = 8 conditions

The experiment setup I used is described as the following.\
The data set has a LENGTH of 10000.\
DIVFAC is the division factor that I use to create
duplicated data. In this case, I have DIVFAC = 3,
so the data will be 0, 1, 2.\
SEARCHOPS are the number of search operations to be performed,
since searching just one time and leave would not be very representative.\
LOOPINC is the the increment step it with in loops for search
and remove. Again, always incrementing by 1 would not be
very representative.
I mix and match 2 different data types with the previous 4 conditions,
and here are the experiment results.
I will define a ratio of scores:\
mtf = score of linkedSet / score of moveToFront\
tps = score of arraySet / score of transposeSequence\
So the larger `mtf` and `tps`, the better the performance will be,
meaning the improvements brought by heuristics are greater.

Exp1: Insert alone, without duplicates
```
JmhRuntimeTest.arraySet           avgt    2   46.481          ms/op
JmhRuntimeTest.linkedSet          avgt    2  110.827          ms/op
JmhRuntimeTest.moveToFront        avgt    2  108.238          ms/op
JmhRuntimeTest.transposeSequence  avgt    2   45.616          ms/op
```
Exp2: Insert alone, with duplicates
```
JmhRuntimeTest.arraySet           avgt    2  0.147          ms/op
JmhRuntimeTest.linkedSet          avgt    2  0.146          ms/op
JmhRuntimeTest.moveToFront        avgt    2  0.122          ms/op
JmhRuntimeTest.transposeSequence  avgt    2  0.127          ms/op
```
Exp3: Insert and Search, without duplicates
```
JmhRuntimeTest.arraySet           avgt    2  13335.054          ms/op
JmhRuntimeTest.linkedSet          avgt    2  34278.022          ms/op
JmhRuntimeTest.moveToFront        avgt    2  11891.065          ms/op
JmhRuntimeTest.transposeSequence  avgt    2  10961.442          ms/op
```
Exp4: Insert and Search, with duplicates
```
JmhRuntimeTest.arraySet           avgt    2  16.701          ms/op
JmhRuntimeTest.linkedSet          avgt    2  17.667          ms/op
JmhRuntimeTest.moveToFront        avgt    2  13.852          ms/op
JmhRuntimeTest.transposeSequence  avgt    2  18.542          ms/op
```
Exp5: Insert and Remove, without duplicates
```
JmhRuntimeTest.arraySet           avgt    2   53.898          ms/op
JmhRuntimeTest.linkedSet          avgt    2  114.615          ms/op
JmhRuntimeTest.moveToFront        avgt    2  113.494          ms/op
JmhRuntimeTest.transposeSequence  avgt    2   50.340          ms/op
```
Exp6: Insert and Remove, with duplicates
```
JmhRuntimeTest.arraySet           avgt    2  0.120          ms/op
JmhRuntimeTest.linkedSet          avgt    2  0.114          ms/op
JmhRuntimeTest.moveToFront        avgt    2  0.151          ms/op
JmhRuntimeTest.transposeSequence  avgt    2  0.136          ms/op
```

Exp7: Insert, Search and Remove, without duplicates
```
JmhRuntimeTest.arraySet           avgt    2  13290.346          ms/op
JmhRuntimeTest.linkedSet          avgt    2  33726.945          ms/op
JmhRuntimeTest.moveToFront        avgt    2  11977.814          ms/op
JmhRuntimeTest.transposeSequence  avgt    2  10962.553          ms/op
```
Exp8: Insert, Search and Remove, with duplicates
```
JmhRuntimeTest.arraySet           avgt    2  21.346          ms/op
JmhRuntimeTest.linkedSet          avgt    2  14.028          ms/op
JmhRuntimeTest.moveToFront        avgt    2  13.928          ms/op
JmhRuntimeTest.transposeSequence  avgt    2  17.697          ms/op
```

```
Table               mtf                 tps
Exp1                1.02392             1.01896
Exp2                1.19672             1.15748
Exp3                2.88267             1.21654
Exp4                1.27541             1.11023
Exp5                1.00988             1.07068
Exp6                0.754967            0.882353
Exp7                2.81578             1.21234
Exp8                1.00718             1.20619
```

From the above analysis, we can see that the ratios (mtf, tps) are mostly > 1,
(except for one experiment exp6, which I guess is probably an experimental/random error), 
which means the heuristics actually have noticable improvements in the general trend! (WOW).

If the difference is unnoticeable, the best way is
always to focus on the design of the experiment.
I would suggest first doing some environment parameter changes
such as change the size the data size, change the 
step within for loop, change the number of searches
in company with insert (and/or) remove.
If that still does not work, I would suggest redesign
the experiment.
