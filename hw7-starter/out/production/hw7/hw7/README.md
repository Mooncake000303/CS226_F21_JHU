# Discussion

data1 JdkHashMap
```
Benchmark                                                                 (fileName)  Mode  Cnt          Score   Error   Units
JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2        255.600           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                       apache.txt  avgt    2       1247.722          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                  apache.txt  avgt    2  353331928.877            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space              apache.txt  avgt    2       1251.765          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm         apache.txt  avgt    2  354516823.959            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space          apache.txt  avgt    2          9.978          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm     apache.txt  avgt    2    2825171.656            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                            apache.txt  avgt    2         22.000          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc          apache.txt  avgt    2  785383424.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2   98477568.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                             apache.txt  avgt    2        170.500              ms
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2          0.337           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                          jhu.txt  avgt    2       1664.058          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                     jhu.txt  avgt    2     620792.016            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space                 jhu.txt  avgt    2       1662.844          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm            jhu.txt  avgt    2     620334.689            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space             jhu.txt  avgt    2          0.179          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm        jhu.txt  avgt    2         66.697            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                               jhu.txt  avgt    2        115.500          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc             jhu.txt  avgt    2  805306368.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2   22314416.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                                jhu.txt  avgt    2         88.500              ms
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2          0.135           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                       joanne.txt  avgt    2       1527.066          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                  joanne.txt  avgt    2     228112.007            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space              joanne.txt  avgt    2       1526.684          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm         joanne.txt  avgt    2     228062.509            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space          joanne.txt  avgt    2          0.180          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm     joanne.txt  avgt    2         26.817            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                            joanne.txt  avgt    2         89.500          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc          joanne.txt  avgt    2  805568512.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2   21254136.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                             joanne.txt  avgt    2         92.500              ms
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2        123.166           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                       newegg.txt  avgt    2       1344.947          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                  newegg.txt  avgt    2  183521920.132            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space              newegg.txt  avgt    2       1358.685          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm         newegg.txt  avgt    2  185382458.739            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space          newegg.txt  avgt    2          9.133          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm     newegg.txt  avgt    2    1246528.328            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                            newegg.txt  avgt    2         23.500          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc          newegg.txt  avgt    2  796688384.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2   73654860.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                             newegg.txt  avgt    2        116.000              ms
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2        669.370           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                    random164.txt  avgt    2        940.583          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm               random164.txt  avgt    2  696512949.967            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space           random164.txt  avgt    2        934.293          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm      random164.txt  avgt    2  692023022.933            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space       random164.txt  avgt    2         46.909          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm  random164.txt  avgt    2   34768759.467            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                         random164.txt  avgt    2         23.500          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc       random164.txt  avgt    2  813334528.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2  288389680.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                          random164.txt  avgt    2        672.500              ms
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2          0.044           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                         urls.txt  avgt    2        649.252          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                    urls.txt  avgt    2      31440.002            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space                urls.txt  avgt    2        659.909          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm           urls.txt  avgt    2      31958.818            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space            urls.txt  avgt    2          0.025          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm       urls.txt  avgt    2          1.217            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                              urls.txt  avgt    2         10.500          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc            urls.txt  avgt    2  814481408.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2   28920088.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                               urls.txt  avgt    2        164.500              ms

```

data2 OpenAddressingHashMap();
```
Benchmark                                                                 (fileName)  Mode  Cnt          Score   Error   Units
JmhRuntimeTest.buildSearchEngine                                          apache.txt  avgt    2        298.594           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                       apache.txt  avgt    2       1070.628          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                  apache.txt  avgt    2  353903883.373            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space              apache.txt  avgt    2       1068.774          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm         apache.txt  avgt    2  353474742.635            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space          apache.txt  avgt    2          8.746          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm     apache.txt  avgt    2    2891658.642            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                            apache.txt  avgt    2         19.000          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc          apache.txt  avgt    2  755204096.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               apache.txt  avgt    2  102856552.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                             apache.txt  avgt    2        177.000              ms
JmhRuntimeTest.buildSearchEngine                                             jhu.txt  avgt    2          0.418           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                          jhu.txt  avgt    2       1344.707          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                     jhu.txt  avgt    2     623544.019            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space                 jhu.txt  avgt    2       1342.729          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm            jhu.txt  avgt    2     622646.907            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space             jhu.txt  avgt    2          0.170          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm        jhu.txt  avgt    2         78.911            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                               jhu.txt  avgt    2        106.000          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc             jhu.txt  avgt    2  805371904.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                  jhu.txt  avgt    2   21683564.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                                jhu.txt  avgt    2         89.500              ms
JmhRuntimeTest.buildSearchEngine                                          joanne.txt  avgt    2          0.170           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                       joanne.txt  avgt    2       1253.807          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                  joanne.txt  avgt    2     235928.009            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space              joanne.txt  avgt    2       1260.378          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm         joanne.txt  avgt    2     237161.352            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space          joanne.txt  avgt    2          0.158          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm     joanne.txt  avgt    2         29.763            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                            joanne.txt  avgt    2         98.500          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc          joanne.txt  avgt    2  805568512.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               joanne.txt  avgt    2   21455704.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                             joanne.txt  avgt    2         91.500              ms
JmhRuntimeTest.buildSearchEngine                                          newegg.txt  avgt    2        152.709           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                       newegg.txt  avgt    2       1090.445          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                  newegg.txt  avgt    2  184425338.017            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space              newegg.txt  avgt    2       1092.908          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm         newegg.txt  avgt    2  184774670.994            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space          newegg.txt  avgt    2          5.950          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm     newegg.txt  avgt    2    1007764.626            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                            newegg.txt  avgt    2         21.000          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc          newegg.txt  avgt    2  794853376.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc               newegg.txt  avgt    2   74212384.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                             newegg.txt  avgt    2        115.500              ms
JmhRuntimeTest.buildSearchEngine                                       random164.txt  avgt    2        908.545           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                    random164.txt  avgt    2        698.806          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm               random164.txt  avgt    2  701171993.515            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space           random164.txt  avgt    2        693.250          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm      random164.txt  avgt    2  696043954.424            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Old_Gen              random164.txt  avgt    2         24.203          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Old_Gen.norm         random164.txt  avgt    2   25187754.909            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space       random164.txt  avgt    2         30.740          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm  random164.txt  avgt    2   31112748.545            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                         random164.txt  avgt    2         19.000          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc       random164.txt  avgt    2  994967552.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc            random164.txt  avgt    2  375614688.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                          random164.txt  avgt    2       1071.500              ms
JmhRuntimeTest.buildSearchEngine                                            urls.txt  avgt    2          0.047           ms/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate                         urls.txt  avgt    2        611.801          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.alloc.rate.norm                    urls.txt  avgt    2      31687.924            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space                urls.txt  avgt    2        596.639          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Eden_Space.norm           urls.txt  avgt    2      30884.747            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space            urls.txt  avgt    2          0.016          MB/sec
JmhRuntimeTest.buildSearchEngine:+c2k.gc.churn.PS_Survivor_Space.norm       urls.txt  avgt    2          0.839            B/op
JmhRuntimeTest.buildSearchEngine:+c2k.gc.count                              urls.txt  avgt    2          9.500          counts
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumCommittedAfterGc            urls.txt  avgt    2  813039616.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.maximumUsedAfterGc                 urls.txt  avgt    2   26672428.000           bytes
JmhRuntimeTest.buildSearchEngine:+c2k.gc.time                               urls.txt  avgt    2        151.000              ms
```

data3 