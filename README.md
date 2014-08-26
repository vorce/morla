# morla

morla is a clojure shell prototype. It is very rough and unfinished.

## Usage

    lein uberjar
    java -jar target/morla-0.1.0-SNAPSHOT-standalone.jar

### Available commands

- quit
- cd
- pwd
- ls

You can also run some clojure stuff, which is the point of morla. Ex:
`println hello`
First and last parens are automatically added.

## License

Copyright © 2014 Joel Carlbark

Distributed under the Eclipse Public License, the same as Clojure.
