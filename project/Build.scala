import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "techApp"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.typesafe.slick" %% "slick" % "2.0.0",
    "commons-validator" % "commons-validator" % "1.4.0"
    )

  val main = play.Project(appName, appVersion, appDependencies).settings()

}