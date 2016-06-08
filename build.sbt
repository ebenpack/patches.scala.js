// Turn this project into a Scala.js project by importing these settings
enablePlugins(ScalaJSPlugin)

name := "Example"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

persistLauncher in Compile := true

libraryDependencies ++= Seq(
  "io.monix" %%% "monix" % "2.0-RC5",
  "com.github.japgolly.scalajs-react" %%% "core" % "0.11.1",
  "com.github.japgolly.scalajs-react" %%% "extra" % "0.11.1",
  "me.chrons" %%% "diode-react" % "0.5.2"
)

jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % "15.0.2"
    / "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "15.0.2"
    / "react-dom.js"
    minified "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM",

  "org.webjars.bower" % "react" % "15.0.2"
    / "react-dom-server.js"
    minified "react-dom-server.min.js"
    dependsOn "react-dom.js"
    commonJSName "ReactDOMServer"
)