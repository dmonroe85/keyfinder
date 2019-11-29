# Keyfinder

Keyfinder is designed to help find candidates for a primary key in a stream of data.
The stream details are intentionally left to the user of this library because they can
come in many forms (SQL rows, json messages, CSV lines, etc).

##### Running the tests

`sbt clean test`

### `KeyAnalyzer`

This trait provides interfaces and basic functionality that allow analyzing a stream of rows.
Each analyzer processes one row at a time and operates on a single column subset that is a
candidate for primary key.

The analyzer can also produce statistics on the current state, including an estimated duplication
rate.

Included implementations:
* `SetAnalyzer`
    * Stores all previously seen key-combinations using sets.
    * This is good for exactly finding a unique primary key.
* `BloomFilterAnalyzer`
    * Stores previously seen row information in a bloom filter.
    * This is an inexact, probabilistic approach, but it is memory-efficient and can scale very well.

### `KeyFinder`

This class produces all of the possible candidate column subsets for analysis, builds analyzers for
each candidate subset, and performs the analysis.