import sbt.Keys.libraryDependencies
import sbt._

object Dependencies {
  import Libs._

  lazy val basedir = Seq(
    libraryDependencies ++= Compile(
      betterFiles,
      config
    ),
    libraryDependencies ++= Test(
      scalaTest
    )
  )

  object Libs {

    val betterFiles = "com.github.pathikrit" %% "better-files" % "3.9.2"

    val config = "com.typesafe" % "config" % "1.4.3"

    val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"

    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

    val scalaTest = "org.scalatest" %% "scalatest" % "3.2.17"
  }

  class DepsBuilder(configuration: Configuration, dependencies: Seq[ModuleID]) {
    def result: Seq[ModuleID] = dependencies.map(_ % configuration)
  }

  /** Pimp [[Configuration]] so you can say:
    *
    * {{{
    *   Test(lib1, lib2)
    * }}}
    *
    * To create a DepsBuilder.
    */
  implicit class DepsBuilderFactory(val configuration: Configuration)
      extends AnyVal {
    def apply(dependencies: ModuleID*) =
      new DepsBuilder(configuration, dependencies)
  }

  /** [[Append.Values]] implementation so you can say:
    *
    * {{{
    *   libraryDependencies ++= Test(lib1, lib2)
    * }}}
    *
    * In your project settings.
    */
  implicit val depsAppend: Append.Values[Seq[ModuleID], DepsBuilder] =
    (a: Seq[sbt.ModuleID], b: DepsBuilder) => a ++ b.result
}
