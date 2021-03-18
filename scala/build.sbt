name := "GildedRose"

version := "1.0"

scalaVersion := "2.13.5"

resolvers += DefaultMavenRepository
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.2" % "test",
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.5.0" % "test"
)
