# Discussion

Here is my experimental data.

1.1 JdkHashMap(): Time
```
Benchmark                                                                 (fileName)  Mode  Cnt          Score   Error   Units
JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2        255.600           ms/op
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2          0.337           ms/op
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2          0.135           ms/op
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2        123.166           ms/op
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2        669.370           ms/op
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2          0.044           ms/op
```

1.2 JdkHashMap(): Space
```
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   98477568.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2   22314416.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2   21254136.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2   73654860.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2  288389680.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2   28920088.000           bytes
```

2.1 OpenAddressingHashMap(): Time
```
Benchmark                                                                 (fileName)  Mode  Cnt          Score   Error   Units
JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2        298.594           ms/op
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2          0.418           ms/op
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2          0.170           ms/op
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2        152.709           ms/op
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2        908.545           ms/op
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2          0.047           ms/op
```

2.2 OpenAddressingHashMap(): Space
```
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2  102856552.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2   21683564.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2   21455704.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2   74212384.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2  275614688.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2   26672428.000           bytes
```

3.1 ChainingHashMap(): Time
```
Benchmark                                                                 (fileName)  Mode  Cnt          Score   Error   Units
JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2        298.659           ms/op
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2          0.383           ms/op
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2          0.173           ms/op
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2        159.909           ms/op
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2       1004.996           ms/op
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2          0.046           ms/op
```

3.2 ChainingHashMap(): Space
```
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2  104605064.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2   24041788.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2   22276416.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2   83692892.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2  282285548.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2   26553752.000           bytes
```


```
Guide:
You should use your README file to explain which of your implementations 
(between OpenAddressingHashMap and ChainingHashMap) is a better choice 
to be used for the search engine. Which approaches did you try to implement each hash table 
(e.g., probing strategies, rehashing strategies, etc.),
in what order, and why did you choose them? What specific tweaks to your 
implementation improved or made things worse? 
Include failed or abandoned attempts, if any, and why.
Finally, summarize all the different ways you developed, evaluated, and improved your hash tables. 
Include all the benchmarking data, results, and analysis that contributed to your 
final decision on which implementation to use for the search engine.

Moreover, provide an analysis of your benchmark data and conclusions. 
For example, why did you choose your final Map implementation as the best one? 
What results were surprising, and which were expected?
```


If we need to compare all three HashMap methods, we will find

**Time Complexity:**
JdkHashMap() < OpenAddressingHashMap() < ChainingHashMap()

**Space Complexity:**
JdkHashMap() < OpenAddressingHashMap() < ChainingHashMap()

Among the three, no wonder JdkHashMap() will win, since
it is Java built-in. So let's just focus on
comparison between `OpenAddressingHashMap` and `ChainingHashMap`.

For `OpenAddressingHashMap`, I used an simple of array of
entry of key, value pairs, my probing strategy is
double hashing. My choice of load factor is 0.75, and my
rehashing strategy is whenever a load factor >= 0.75 
is reached, it will incurs a rehash with capcity that is larger,
next one in the table of prime numbers. 

I chooose double hashing in the end because I chose
linear probing initially, and it takes so long to run the
`JmhRuntimeTest` that I decided there must be something wrong
with my probing method. Linear Probing will lead to
primary clustering, and Quadratic Probing will lead to
secondary clustering. Therefore, I'm choosing double hashing,
which requires two different hashcodes to avoid clustering.

For rehashing strategies, I initially chose load factor of 0.75, 
since it is said 0.75 is the best. 
When I try other load factors like 0.9 and 0.5,
they all degrade the performance (time and space) to some extent. 
Therefore, I decide to stick with load factor of 0.75.
Also, during rehashing, the capacity of the hashMap should be
expanded. Following the lecture guides, I choose to expand
the capacity by looking up the prime number table.
Again, this is intended for decreasing the collision possiblities
and increase possible outcomes of indexes it can reach.

For `ChainingHashMap`, I used an simple of array of linkedLists
entry of key, value pairs, again with load factor 0.75.
During the rehashing, the capacity will be doubled and +1,
as suggested by the campuswire post.

To be honest, I would say my choice of buckets as LinkedList
is out of laziness, since there are a lot of furnished 
built-in functions for Linked Lists. But it turns out to perform
just as well.

After some thoughts, I choose `OpenAddressingHashMap` as
my final Map implementation, since experimentally, it performs
better both in space and time complexity. This is initially to my
surprise, since I didn't expect `OpenAddressingHashMap` to be so
performant, especially in space. Compared to`ChainingHashMap`,
every entry has an additional boolean field to identify
whether it is a tombStone or not, and also since I'm not
writing over tombStones, it will requires more rehashing.
But I will make the following educated guesses about possible
the underlying reasons.

First off, it employs a very great double hashing strategy,
and which rehashing from prime number tables, it will leads to
less collisions more efficient finds. Morever, since 
it is rehashed more often, it means it is more likely
to be efficient to insert/remove/parse through
at a given point of time compared to `ChainingHashMap`,
which is rehashed less often and who rehashed capacity
is a boring doubled and plus one.




