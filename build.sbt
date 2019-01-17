
// language features
scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)

//
// common settings
//
lazy val commonSettings = Seq(
  organization := "com.digitalcipher.spiked",
  version := "0.0.1-snapshot",
  scalaVersion := "2.12.7"
)

lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "spikes-parsing",

    // package as a jar file
    exportJars := true,

    //
    // dependencies
    //
    libraryDependencies ++= Seq(
      // scala modules
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",

      // logging
      "ch.qos.logback" % "logback-classic" % "1.2.3",

      // dimensions
      "org.typelevel" %% "squants" % "1.3.0",

      // parsing for spikes
      "com.digitalcipher.spiked" % "spikes-coords_2.12" % "0.1.1",
      "com.digitalcipher.spiked" % "spikes-descriptions_2.12" % "0.0.1-snapshot",

      // testing
      //      "org.scalactic" %% "scalactic" % "3.0.1",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )

// publishing to sonatype
ThisBuild / organization := "com.digitalcipher"
ThisBuild / organizationName := "digitalcipher"
ThisBuild / organizationHomepage := Some(url("https://github.com/robphilipp"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/robphilipp/parsing"),
    "scm:git:git://github.com/robphilipp/parsing"
  )
)
ThisBuild / developers := List(
  Developer(
    id    = "rob.philipp",
    name  = "Rob Philipp",
    email = "rob.philipp@gmail.com",
    url   = url("https://github.com/robphilipp")
  )
)

ThisBuild / description := "parsing for building spikes networks"
ThisBuild / licenses := List("MIT" -> new URL("https://opensource.org/licenses/MIT"))
ThisBuild / homepage := Some(url("https://github.com/robphilipp/parsing"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true