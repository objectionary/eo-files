<img src="https://www.yegor256.com/images/books/elegant-objects/cactus.svg" height="100px" />

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/yegor256/eo-files)](http://www.rultor.com/p/yegor256/eo-files)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![mvn](https://github.com/yegor256/eo-files/actions/workflows/mvn.yml/badge.svg?branch=master)](https://github.com/yegor256/eo-files/actions/workflows/mvn.yml)
[![PDD status](http://www.0pdd.com/svg?name=yegor256/eo-files)](http://www.0pdd.com/p?name=yegor256/eo-files)
[![codecov](https://codecov.io/gh/cqfn/eo/branch/master/graph/badge.svg)](https://codecov.io/gh/cqfn/eo)
[![Maven Central](https://img.shields.io/maven-central/v/org.eolang/eo-files.svg)](https://maven-badges.herokuapp.com/maven-central/org.eolang/eo-files)

[![Hits-of-Code](https://hitsofcode.com/github/yegor256/eo-files)](https://hitsofcode.com/view/github/yegor256/eo-files)
![Lines of code](https://img.shields.io/tokei/lines/github/yegor256/eo-files)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/yegor256/eo-files/blob/master/LICENSE.txt)

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
      "**/*"
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
+alias org.eolang.fs.file
file > f
  "/tmp/foo.txt"

# Get its name:
stdout
  sprintf
    "File name is: %s"
    f

# Does it exist?
f.exists

# Is it a directory?
f.is-dir

# Touch it, to make sure it exists
f.touch

# Delete the file:
f.rm

# Rename/move it:
f.mv "/tmp/bar.txt"

# Get the size of it in bytes:
f.size > s
```

Reading:

```
# Read binary content into the "output," in 1024-bytes chunks;
# the "memory-as-output" is an abstract object with one free attribute,
# which is the memory where the bytes will be stored:
file "/tmp/foo.txt" > f
memory > m
copied
  f.as-input
  memory-as-output m
  1024
```

Writing:

```
# Write binary content, taking it from the "input",
# until input turns into a non-empty "bytes"; here the
# "mode" is the same as the mode in POSIX fopen();
# if the file is absent, it will be created:
file "/tmp/foo.txt" > f
copied
  bytes-as-input
    "你好, world!".as-bytes
  f.as-ouput "w+"
```

Smart object to help read content fast:

```
# This is the entire binary content of the file:
+alias org.eolang.fs.content
content f
```

Directories:

```
# Make an object representing a directory on disc:
+alias org.eolang.fs.directory
directory > d
  "/tmp"

# Make it if doesn't exist:
d.mkdir

# Delete it recursively:
d.rm-rf

# List all files recursively:
d.walk "**/*.txt"
```

Temporary files:

```
# This is a system directory for temporary files:
+alias org.eolang.fs.tmpdir
tmpdir > d

# Create an empty temporary file in a directory
d.tmpfile.@ > f
```

Name manipulations:

```
# Add path segment to existing file:
file "/tmp" > f
f.resolve "foo.txt"

# Get directory name:
+alias org.eolang.fs.dir-name
dir-name f

# Get base name (no directory, not extension):
+alias org.eolang.fs.base-name
base-name f

# Get extension:
+alias org.eolang.fs.ext-name
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

