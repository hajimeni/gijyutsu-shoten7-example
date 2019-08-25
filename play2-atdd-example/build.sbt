name := """play2-atdd-example"""
organization := "com.github.hajimeni"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
  "org.pegdown" % "pegdown" % "1.4.2" % Test,
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "org.xerial" % "sqlite-jdbc" % "3.23.1",
  "org.flywaydb" %% "flyway-play" % "5.3.2"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.github.hajimeni.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.github.hajimeni.binders._"
