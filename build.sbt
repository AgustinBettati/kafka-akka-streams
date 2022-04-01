ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val AkkaVersion = "2.6.9"
val AlpakkaKafkaVersion = "2.0.5"

lazy val root = (project in file("."))
  .settings(
    name := "akka-streams-kafka",
    libraryDependencies := List(
      // #deps
      "com.typesafe.akka" %% "akka-stream-kafka" % AlpakkaKafkaVersion,
      "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
      // for JSON in Scala
      "io.spray" %% "spray-json" % "1.3.5",
      // Logging
      "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )
