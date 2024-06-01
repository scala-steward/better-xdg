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

    val config =
      baseConfig andThen
        orgConfig andThen
        testOpts

    config(Project(projectId, dir))
  }

  final val DefaultJavacOptions = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-Werror",
    "-Xlint:all",
    "-Xlint:-serial"
  )

  lazy val defaultSettings: Seq[Setting[_]] = Seq(
    javacOptions ++= DefaultJavacOptions,
    Test / logBuffered := false
  )
}
