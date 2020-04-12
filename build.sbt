import BuildSettings._

ThisBuild / organization := "ph.samson.xdg"
ThisBuild / organizationName := "Edward Samson"
ThisBuild / startYear := Some(2020)
ThisBuild / licenses += ("GPL-3.0-or-later", url(
  "https://spdx.org/licenses/GPL-3.0-or-later.html"
))
ThisBuild / homepage := Some(url("https://github.com/esamson/better-xdg"))
ThisBuild / developers := List(
  Developer(
    "esamson",
    "Edward Samson",
    "edward@samson.ph",
    url("https://edward.samson.ph")
  )
)

ThisBuild / scalaVersion := "2.13.1"

lazy val root = Project(id = "better-xdg", base = file("."))
  .aggregate(
    basedir
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

def fileVars(vars: Map[String, File]): Map[String, String] =
  vars.mapValues(_.toString)

def dirsVars(vars: Map[String, Seq[File]]): Map[String, String] =
  vars.mapValues(_.map(_.toString).mkString(java.io.File.pathSeparator))
