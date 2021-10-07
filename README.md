<img src="https://www.yegor256.com/images/books/elegant-objects/cactus.svg" height="100px" />

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/yegor256/eo-files)](http://www.rultor.com/p/yegor256/eo-files)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![mvn](https://github.com/yegor256/eo-files/actions/workflows/mvn.yml/badge.svg?branch=master)](https://github.com/yegor256/eo-files/actions/workflows/mvn.yml)
[![PDD status](http://www.0pdd.com/svg?name=yegor256/eo-files)](http://www.0pdd.com/p?name=yegor256/eo-files)
[![Hits-of-Code](https://hitsofcode.com/github/yegor256/eo-files)](https://hitsofcode.com/view/github/yegor256/eo-files)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/yegor256/eo-files/blob/master/LICENSE.txt)
![Lines of code](https://img.shields.io/tokei/lines/github/yegor256/eo-files)
[![Maven Central](https://img.shields.io/maven-central/v/org.cactoos/eo-parent.svg)](https://maven-badges.herokuapp.com/maven-central/org.cactoos/eo-parent)

[EOLANG](https://www.eolang.org) objects for file system.

This is how you list all text files in a directory recursively:

```
+alias org.eolang.io.stdout
+alias org.eolang.txt.sprintf
+alias org.eolang.fs.directory

each. > @
  select.  
    walk.
      directory "/tmp"    
      **/*
    *
      [f] (f.isDir?.not > @)
      [f] (/\.txt$/.matches f > @)
  [f]
    stdout > @
      sprintf "file: %s" f
```

You are welcome to add more primitives to this lib.

Simple manipulations:

```
# Make a new object representing a file on disc
file > f
  "/tmp/foo.txt" 

# Get its name:
stdout
  sprintf
    "File name is: %s" 
    f

# Delete the file:
f.rm

# Rename/move it:
f.mv "/tmp/bar.txt"

# Get the size of it in bytes:
f.size > s
```

Reading:

```
# This is the acceptor of the data:
[] > target
  [data] > write

# Read binary content into the "target," in 1024-bytes chunks:
f.read target 1024
```

Writing:

```
# This is the source of the data:
[] > source
  [size] > read

# Write binary content, taking it from the "source":
f.write source
```

## How to Contribute

Fork repository, make changes, send us a pull request.
We will review your changes and apply them to the `master` branch shortly,
provided they don't violate our quality standards. To avoid frustration,
before sending us your pull request please run full Maven build:

```bash
$ mvn clean install -Pqulice
```

You will need Maven 3.3+ and Java 8+.

