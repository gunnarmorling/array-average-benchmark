# array-average-benchmark

A JMH benchmark comparing different approaches for calculating the average of an array with 100M integer values.

## Build

```
mvn clean verify
```

## Running the Benchmarl

```
java --add-modules src.incubator.vector target/benchmarks.jar
```

## Licenese

This code base is available ander the Apache License, version 2.
