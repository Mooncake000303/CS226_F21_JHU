# Homework 8

## Discussion 


Here's the experimental data.

1. JHU to Druid Lake

```
~~~ SystemRuntimeTest ~~~
Config: baltimore.streets.txt from -76.6175,39.3296 to -76.6383,39.3206
Loading network took 185 milliseconds.
Finding shortest path took 19 milliseconds.
~~~~~~     END     ~~~~~~

~~~ MemoryMonitorTest ~~~
Config: baltimore.streets.txt from -76.6175,39.3296 to -76.6383,39.3206
	Used memory: 6046.32 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 6218.26 KB (Δ = 171.938)
Loading the network
	Used memory: 16116.75 KB (Δ = 9898.492)
Finding the shortest path
	Used memory: 16907.03 KB (Δ = 790.281)
Setting objects to null (so GC does its thing!)
	Used memory: 8650.55 KB (Δ = -8256.477)
~~~~~~     END     ~~~~~~
```


2. 7-11 to Druid Lake

```
~~~ SystemRuntimeTest ~~~
Config: baltimore.streets.txt from -76.6214,39.3212 to -76.6383,39.3206
Loading network took 192 milliseconds.
Finding shortest path took 18 milliseconds.
~~~~~~     END     ~~~~~~

~~~ MemoryMonitorTest ~~~
Config: baltimore.streets.txt from -76.6214,39.3212 to -76.6383,39.3206
	Used memory: 6035.35 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 6213.80 KB (Δ = 178.453)
Loading the network
	Used memory: 16125.92 KB (Δ = 9912.117)
Finding the shortest path
	Used memory: 16853.16 KB (Δ = 727.242)
Setting objects to null (so GC does its thing!)
	Used memory: 8624.88 KB (Δ = -8228.289)
~~~~~~     END     ~~~~~~
```

3. Inner Harbor to JHU

```
~~~ SystemRuntimeTest ~~~
Config: baltimore.streets.txt from -76.6107,39.2866 to -76.6175,39.3296
Loading network took 203 milliseconds.
Finding shortest path took 51 milliseconds.
~~~~~~     END     ~~~~~~

~~~ MemoryMonitorTest ~~~
Config: baltimore.streets.txt from -76.6107,39.2866 to -76.6175,39.3296
	Used memory: 6045.17 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 6195.18 KB (Δ = 150.008)
Loading the network
	Used memory: 16109.58 KB (Δ = 9914.398)
Finding the shortest path
	Used memory: 17112.70 KB (Δ = 1003.125)
Setting objects to null (so GC does its thing!)
	Used memory: 8645.90 KB (Δ = -8466.805)
~~~~~~     END     ~~~~~~
```

From the data above, we can see that the runtime follows
T(JHU->Druid Lake) < T(7-11->Druid Lake) < T(Inner Harbor to JHU),
which make sense to me as it is proportional to the length
of the real-world paths. However, memory result is similar for all
three and does not show a clearly distinctive pattern.

My design of the SparseGraph is the following.
- HashMap of vertex values and vertex
- HashSet for edges
- LinkedList of incoming and outgoing edges

My implementation of the graph search is the following.
- HashMap of vertex and distance
- HashSet of explored
- PriorityQueue in the order of search

I would say this design it the best I can achieve, since HashMap and Hashset has 
mostly O(1), which will be the efficiency of a lot of core operations like search.
Also, the incident list (linked list) design is good compared to an
adjacency matrix, which it will waste a lot spaces, especially 
in this case for a sparse graph.

Note that for efficiency concerns, 
I choose a Hashset (which has an expected O(1))
efficiency instead of simple DS like ArrayList (which
has O(n) efficiency) to store edges.

Although there's nothing "wrong" (like fails to find a path) happening,
I found the similarity of memory spaces for 3 
pairs of start and end points quite interesting.



