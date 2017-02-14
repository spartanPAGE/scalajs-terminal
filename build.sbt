enablePlugins(ScalaJSPlugin)

name := "Example"

scalaVersion := "2.12.0"

jsDependencies += RuntimeDOM

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "com.lihaoyi" %% "scalatags" % "0.6.3",
  "com.lihaoyi" %%% "utest" % "0.4.4" % "test"
)

testFrameworks += new TestFramework("utest.runner.Framework")
