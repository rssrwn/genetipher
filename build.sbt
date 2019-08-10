import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.2.0"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
    .settings(
        name := "genetipher",

        libraryDependencies += scalaTest % Test,

        libraryDependencies += "com.example" % "genetic-search" % "1.1.0" from "file:///Users/Ross/Documents/Programming/genetipher/lib/genetic-search_2.13-1.1.0.jar"
    )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
