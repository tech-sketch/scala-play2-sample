name := "scala-play2-sample"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings
