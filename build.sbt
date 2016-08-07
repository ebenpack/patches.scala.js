enablePlugins(ScalaJSPlugin)

name := "patches"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

persistLauncher in Compile := true

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "com.lihaoyi" %%% "scalatags" % "0.6.0",
  "eu.unicredit" %%% "akkajsactor" % "0.1.2-SNAPSHOT"
)

jsDependencies ++= Seq()