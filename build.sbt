// Turn this project into a Scala.js project by importing these settings
enablePlugins(ScalaJSPlugin)

name := "Example"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

persistLauncher in Compile := true

persistLauncher in Test := false

//testFrameworks += new TestFramework("utest.runner.Framework")

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.2",
  "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
  //"org.scala-lang.modules" %% "scala-async" % "0.9.5",
  "org.monifu" %%% "monifu" % "1.2"
  //"com.thoughtworks.binding" %%% "dom" % "latest.release"
)

//addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)