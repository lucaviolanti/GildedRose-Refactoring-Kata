name := "GildedRose"

version := "1.0"

scalaVersion := "2.13.5"

resolvers += DefaultMavenRepository

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "0.7.22" % Test,
  "org.scalameta" %% "munit-scalacheck" % "0.7.22" % Test
)
// Use %%% for non-JVM projects.
testFrameworks += new TestFramework("munit.Framework")
