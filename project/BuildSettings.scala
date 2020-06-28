import sbt._
import sbt.Keys._

object BuildSettings {
  def module(name: String): Project = {
    val projectId = name.split('/').last
    val dir = file(name)

    val baseConfig = (project: Project) =>
      project
        .disablePlugins(plugins.JUnitXmlReportPlugin)
        .settings(defaultSettings)

    val orgConfig = (project: Project) =>
      if (name.contains('/')) {
        val org = name.split('/').dropRight(1).mkString(".")
        project.settings(
          organization := s"${organization.value}.$org"
        )
      } else {
        project
      }

    val testOpts = (project: Project) =>
      sys.env
        .get("TEST_OPTS")
        .map(opts => project.settings(Test / javaOptions += opts))
        .getOrElse(project)

    val itConfig = (project: Project) =>
      if ((project.base / "src" / "it").isDirectory) {
        project
          .configs(IntegrationTest)
          .settings(
            Defaults.itSettings,
            IntegrationTest / logBuffered := false
          )
      } else {
        project
      }

    val itOpts = (project: Project) =>
      sys.env
        .get("IT_OPTS")
        .map(opts => project.settings(IntegrationTest / javaOptions += opts))
        .getOrElse(project)

    val config =
      baseConfig andThen
        orgConfig andThen
        testOpts andThen
        itConfig andThen
        itOpts

    config(Project(projectId, dir))
  }

  final val DefaultScalacOptions = Seq(
    "-encoding",
    "UTF-8", // Specify character encoding used by source files.
    "-Ybackend-parallelism",
    s"${sys.runtime.availableProcessors()}", // maximum worker threads for backend
    "-Ybackend-worker-queue",
    s"${sys.runtime.availableProcessors() * 2}", // backend threads worker queue size
    "-Woctal-literal", // Warn on obsolete octal syntax.
    "-Xlint:nonlocal-return", // A return statement used an exception for flow control.
    "-Xlint:implicit-not-found", // Check @implicitNotFound and @implicitAmbiguous messages.
    "-Xlint:serial", // @SerialVersionUID on traits and non-serializable classes.
    "-Xlint:valpattern", // Enable pattern checks in val definitions.
    "-Xlint:eta-zero", // Warn on eta-expansion (rather than auto-application) of zero-ary method.
    "-Xlint:eta-sam" // Warn on eta-expansion to meet a Java-defined functional interface that is not explicitly annotated with @FunctionalInterface.
  )

  final val DefaultJavacOptions = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-Werror",
    "-Xlint:all",
    "-Xlint:-serial"
  )

  lazy val defaultSettings: Seq[Setting[_]] = Seq(
    scalacOptions ++= DefaultScalacOptions,
    scalacOptions ++= (
      // Fail the compilation if there are any warnings.
      if (sys.env.contains("STRICT")) {
        Seq("-Xfatal-warnings")
      } else {
        Nil
      }
    ),
    Compile / console / scalacOptions --= Seq(
      "-Wunused:imports",
      "-Xfatal-warnings"
    ),
    javacOptions ++= DefaultJavacOptions,
    Test / logBuffered := false
  )
}
