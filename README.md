# Java-FOPL

Java-FOPL is a Java-based meta-linguistic abstraction of the [first-order predicate calculus](http://en.wikipedia.org/wiki/First-order_logic) (FOPL). The approach taken in this work is to mirror the [predicate calculus formation rules](http://en.wikipedia.org/wiki/First-order_logic#Formation_rules) inasmuch as is practical. The point of this work is to make predicate calculus expressions clear (*read: not efficient*) and intuitive, and to have the structure of code reflect the language with which FOPL is typically explained in a classroom setting (for instance _Constants_ are implemented as _0-ary Functions_, not as their own class).

Most FOPL constructs are organized into the two sub-packages `fopl.formulas` and `fopl.terms`, with constructs that are used by all others placed in the top-level `fopl` package. The domain of discourse (i.e. the symbols used in FOPL expressions) is defined implicitly through the construction of FOPL expressions, but symbols are recorded as explicitly via the `fopl.Symbol` class (instead of implicitly through the use of `String` objects, which seems to be the standard way of doing so).

# License

Copyright 2015-present Rogelio E. Cardona-Rivera
