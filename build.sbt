ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.1"

lazy val zioV = "2.1.9"
lazy val zioConfig = "4.0.2"

lazy val root = (project in file("."))
  .settings(
    assembly / mainClass := Some("twoGisTest/Main"),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case "application.conf" => MergeStrategy.concat
      case _ => MergeStrategy.first
    },
    assembly / assemblyJarName := "2gisTest.jar",
    name := "2gisTest",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioV,
      "dev.zio" %% "zio-streams" % zioV,
      "dev.zio" %% "zio-http" % "3.0.1",
      "dev.zio" %% "zio-json" % "0.7.3",
      "dev.zio" %% "zio-config" % zioConfig,
      "dev.zio" %% "zio-config-magnolia" % zioConfig,
      "dev.zio" %% "zio-config-typesafe" % zioConfig
    )
  )
