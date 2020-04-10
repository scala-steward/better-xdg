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
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "UTF-8", // Specify character encoding used by source files.
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Ybackend-parallelism",
    s"${sys.runtime.availableProcessors()}", // maximum worker threads for backend
    "-Ybackend-worker-queue",
    s"${sys.runtime.availableProcessors() * 2}", // backend threads worker queue size
    "-Wdead-code", // Warn when dead code is identified.
    "-Wvalue-discard", // Warn when non-Unit expression results are unused.
    "-Wnumeric-widen", // Warn when numerics are widened.
    "-Woctal-literal", // Warn on obsolete octal syntax.
    "-Wunused:imports", // Warn if an import selector is not referenced.
    "-Wunused:patvars", // Warn if a variable bound in a pattern is unused.
    "-Wunused:privates", // Warn if a private member is unused.
    "-Wunused:locals", // Warn if a local definition is unused.
    "-Wunused:explicits", // Warn if an explicit parameter is unused.
    "-Wunused:implicits", // Warn if an implicit parameter is unused.
    "-Wextra-implicit", // Warn when more than one implicit parameter section is defined.
    "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
    "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
    "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
    "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
    "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:option-implicit", // Option.apply used implicit view.
    "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
    "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:nonlocal-return", // A return statement used an exception for flow control.
    "-Xlint:implicit-not-found", // Check @implicitNotFound and @implicitAmbiguous messages.
    "-Xlint:serial", // @SerialVersionUID on traits and non-serializable classes.
    "-Xlint:valpattern", // Enable pattern checks in val definitions.
    "-Xlint:eta-zero", // Warn on eta-expansion (rather than auto-application) of zero-ary method.
    "-Xlint:eta-sam", // Warn on eta-expansion to meet a Java-defined functional interface that is not explicitly annotated with @FunctionalInterface.
    "-Xlint:deprecation" // Enable linted deprecations.
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
