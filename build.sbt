import BuildSettings._

ThisBuild / scalaVersion := "2.13.6"

lazy val root = Project(id = "better-xdg", base = file("."))
  .aggregate(
    basedir,
    basedirTests2
  )
  .settings(publish / skip := true)
  .disablePlugins(HeaderPlugin)

lazy val basedir = module("basedir")
  .settings(Dependencies.basedir)
  .settings(
    Test / fork := true,
    Test / forkOptions := {
      val testDirs = (Test / sourceDirectory).value / "dirs"
      ForkOptions()
        .withEnvVars(
          fileVars(
            Map(
              "XDG_DATA_HOME" -> testDirs / "home" / "local" / "share",
              "XDG_CONFIG_HOME" -> testDirs / "home" / "config"
            )
          ) ++ dirsVars(
            Map(
              "XDG_DATA_DIRS" -> Seq(
                testDirs / "usr" / "local" / "share",
                testDirs / "usr" / "share"
              ),
              "XDG_CONFIG_DIRS" -> Seq(
                testDirs / "etc" / "xdg",
                testDirs / "usr" / "share" / "settings" / "xdg"
              )
            )
          )
        )
        .withRunJVMOptions(Vector("-Dtest.conf1=sysprops"))
    }
  )

lazy val basedirTests2 = module("basedir-tests2")
  .dependsOn(basedir % "test->test")
  .settings(
    Test / fork := true,
    Test / forkOptions := {
      val testDirs = (Test / sourceDirectory).value / "dirs"
      ForkOptions()
        .withEnvVars(
          fileVars(
            Map(
              "XDG_DATA_HOME" -> testDirs / "home" / "local" / "share",
              "XDG_CONFIG_HOME" -> testDirs / "home" / "config"
            )
          ) ++ dirsVars(
            Map(
              "XDG_DATA_DIRS" -> Seq(
                testDirs / "usr" / "local" / "share",
                testDirs / "usr" / "share"
              ),
              "XDG_CONFIG_DIRS" -> Seq(
                testDirs / "etc" / "xdg",
                testDirs / "usr" / "share" / "settings" / "xdg"
              )
            )
          )
        )
    }
  )

def fileVars(vars: Map[String, File]): Map[String, String] =
  vars.mapValues(_.toString)

def dirsVars(vars: Map[String, Seq[File]]): Map[String, String] =
  vars.mapValues(_.map(_.toString).mkString(java.io.File.pathSeparator))
