# better-xdg


A Scala combo of
[better-files][1] +
[lightbend/config][2] +
[XDG Base Directory Specification][3].

## Add to your dependencies.

Latest release is available from Maven Central.

[![version](https://maven-badges.herokuapp.com/maven-central/ph.samson.xdg/basedir_2.13/badge.svg?subject=version&style=flat-square)](https://maven-badges.herokuapp.com/maven-central/ph.samson.xdg/basedir_2.13/)

```scala
libraryDependencies += "ph.samson.xdg" %% "basedir" % version
```

## Use in your code.

```scala
import ph.samson.xdg.basedir._

object MyApp {
  def main(args: Array[String]): Unit = {

    // Get the base data directory
    val dataDir = data("my_app").get

    // Get a file from the data directory
    val dataFile = data("my_app").get("file")
  
    // Lookup all files from data directories
    val dataFiles = data("my_app").lookup("file")

    // Load application.conf
    val appConf = config("my_app").load

    // Load other.conf
    val otherConf = config("my_app").load("other.conf")

    // Get a file from the cache directory
    val cacheFile = cache("my_app").get("file")

    // Runtime dir may not be available if $XDG_RUNTIME_DIR is not set
    val runFile = runtime("my_app").get("file")
  }
}
```

[1]: https://github.com/pathikrit/better-files
[2]: https://github.com/lightbend/config
[3]: https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html
