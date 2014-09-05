# morla

morla is a clojure shell prototype. It is very rough and unfinished.

## Usage

    lein uberjar
    java -jar target/morla-0.1.0-SNAPSHOT-standalone.jar

### Available commands

Built in commands, which need not be surrounded by parens:

- quit
- cd
- pwd
- ls

To execute programs just type in the command followed by arguments.
Note that there is a current limitation for specifying files
in the current dir, to do that you need to pre-pend the file/dir with "./",
Ex: `cat ./myfile.txt`

You can also run some clojure stuff, which is the main point of morla. Ex:
`"hello"`, `(+ 1 4)`, `(map #(.toUpperCase %) (ls))` etc

## License

Copyright © 2014 Joel Carlbark

Distributed under the Eclipse Public License, the same as Clojure.
