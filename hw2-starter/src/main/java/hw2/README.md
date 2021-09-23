# Discussion

The `Roster` class uses `IndexedList` to store a list of students. The
`Roster.find` implements the binary search algorithm. Which
implementation of the `IndexedList` should be used to implement the
`Roster` class? (It could be one or more of `ArrayIndexedList`,
`LinkedIndexList`, `SparseIndexedList`). And why?
   
--------------- Write your answers below this line ----------------

The answer is definitely not `SparseIndexedList`. 
`SparseIndexedList` is based on the design that most objects within the list 
has the same "default value" and few has different data. 
However, it would not be suitable in this case because in `Roster` class, 
most students will definitely have different data, i.e. different names and emails 
instead of the same / default ones, so it won't make any sense to use `SparseIndexedList` here.

`ArrayIndexedList` should be preferred when `find` is more frequently
called than `add/remove`. It will be very efficient to find/locate Student
stored in an array at a specific index, since we can use arr[index] to 
access Student, which takes up constant time. When it comes to add/remove,
it will be less efficient because in order to keep the List ordered, 
every operation of these kind will cause a lot of data movement/relocation to
make space for the added student or to fill in the gap of the removed student,
which takes up linear time for every add/remove.

`LinkedIndexList` should be preferred when `add/remove` is more frequently
called than `find`. It will be very efficient to add/remove Students stored
in a linked list, since we can operate on a few pointers to achieve this,
which takes up constant time. When it comes to find Students, it will be
less efficient because in order to find the ith Student, we will need to
traverse from the start of the linked list (1st) until it gets to ith, 
which takes up linear time for every find.

Therefore I would say the answer will either be `ArrayIndexedList` or 
`LinkedIndexList`, depending on the which frequency is higher,`add/remove` 
or `find`. If `find` is more frequently called than `add/remove`, 
`ArrayIndexedList` is better. If `add/remove` is more frequently
called than `find`, `LinkedIndexList` is better.