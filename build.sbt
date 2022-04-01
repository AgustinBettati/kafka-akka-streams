ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val AkkaVersion = "2.6.9"
val AlpakkaKafkaVersion = "2.0.5"

lazy val root = (project in file("."))
  .settings(
    name := "akka-streams-kafka",
    libraryDependencies := List(
      // #deps
      // brings akka-actor and akka-stream transitively with version 2.6.9
      "com.typesafe.akka" %% "akka-stream-kafka" % AlpakkaKafkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
      // for JSON in Scala
      "io.spray" %% "spray-json" % "1.3.5",
      // Logging
      "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion
    )
  )
