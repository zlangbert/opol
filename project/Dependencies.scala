import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Dependencies {

  object Versions {
    val circe  = "0.5.0-M2"
  }

  val shared = Def.setting(Seq(
    "com.lihaoyi" %%% "autowire" % "0.2.5"
  ) ++ Seq(
    "io.circe" %%% "circe-core",
    "io.circe" %%% "circe-generic",
    "io.circe" %%% "circe-parser"
  ).map(_ % Versions.circe))
}
