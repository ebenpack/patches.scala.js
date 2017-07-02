enablePlugins(ScalaJSPlugin)

name := "patches"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.2"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "eu.unicredit" %%% "shocon" % "0.1.8",
  "com.lihaoyi" %%% "scalatags" % "0.6.5",
  "org.akka-js" %%% "akkajsactor" % "1.2.5.2",
  "org.scalaz" %%% "scalaz-core" % "7.2.8"
)

jsDependencies ++= Seq()