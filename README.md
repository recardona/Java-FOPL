# Java-FOPL

Java-FOPL is a Java-based meta-linguistic abstraction of the [first-order predicate logic](http://en.wikipedia.org/wiki/First-order_logic) (FOPL). The approach taken in this work is to mirror the [predicate logic formation rules](http://en.wikipedia.org/wiki/First-order_logic#Formation_rules) inasmuch as is possible. The point of this work is to make predicate logic expressions clear (*read: not efficient*) and intuitive, and to have the structure of code reflect the language with which FOPL is typically explained in a classroom setting (for instance _Constants_ are implemented as _0-ary Functions_, not as their own class).

Most FOPL constructs are organized into the two sub-packages `fopl.formulas` and `fopl.terms`, with constructs that are used by all others placed in the top-level `fopl` package. The domain of discourse (i.e. the symbols used in FOPL expressions) is defined implicitly through the construction of FOPL expressions, but symbols are recorded explicitly via the `fopl.Symbol` class (instead of implicitly through the use of `String` objects, which seems to be the standard way of doing so).

## Getting Started

# Build instructions

To build run

```
./gradlew assemble
```

You will find a brand new jar built at the 'build/libs' directory.

To test

```
./gradlew test
```

To see the list of tasks available through gradle, see

```
./gradlew tasks
```

# Example code

If you want to see an example of things you can do with this code base, consult the `testsrc` folder for example codes. In there you'll find examples of both [unification](https://en.wikipedia.org/wiki/Unification_%28computer_science%29) and [resolution](https://en.wikipedia.org/wiki/SLD_resolution), under `UnifiableTest.java` and `AbstractSolutionNodeTest.java` respectively.

## Special Thanks

To [AI Algorithms, Data Structures, and Idioms in Prolog, Lisp, and Java by Luger and Stubblefield](http://www.amazon.com/Algorithms-Data-Structures-Idioms-Prolog/dp/0136070477), which outlined a great deal of the architecture for the code base in this repository.

# License

The MIT License (See LICENSE.md)

