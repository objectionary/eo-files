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
      [f] (f.is-dir.not > @)
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

# Is it a directory?
f.is-dir

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

Smart objects to help read and write:

```
# This is the entire binary content of the file:
content f

# This is the entire text content of the file in UTF-8:
text-content f "utf-8"

# This is the binary content written to the file:
written f data

# This is the text content written to the file:
text-written f text
```

Directories:

```
# Make an object representing a directory on disc:
directory > d
  "/tmp"

# Make it if not exists:
d.mkdir

# Delete it with all files:
d.rm-rf

# List all files recursively:
d.walk "**/*.txt"
```

Name manipulations:

```
# Get directory name:
dir-name f

# Get base name (no directory, not extension):
base-name f

# Get extension:
ext-name f
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

