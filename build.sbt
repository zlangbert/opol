import java.io.{FileWriter, PrintWriter}

import scala.io.Source

name := "opol"

version in ThisBuild := "0.1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.8"

val shared = project.in(file("shared"))
  .enablePlugins(ScalaJSPlugin)

val server = project.in(file("server"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(shared)
  .settings(
    (fastOptJS in Compile) := {
      {

        def read(f: File): String = {
          Source.fromFile(f).mkString
        }

        val bootstrap = """var addGlobalProps = function(obj) {
                          |  obj.require = require;
                          |  obj.__dirname = __dirname;
                          |};
                          |
                          |if((typeof __ScalaJSEnv === "object") && typeof __ScalaJSEnv.global === "object") {
                          |  addGlobalProps(__ScalaJSEnv.global);
                          |} else if(typeof global === "object") {
                          |  addGlobalProps(global);
                          |} else if(typeof __ScalaJSEnv === "object") {
                          |  __ScalaJSEnv.global = {};
                          |  addGlobalProps(__ScalaJSEnv.global);
                          |} else {
                          |  var __ScalaJSEnv = { global: {} };
                          |  addGlobalProps(__ScalaJSEnv.global)
                          |}""".stripMargin

        val launcher =
          read((artifactPath in (Compile, packageScalaJSLauncher)).value)

        (fastOptJS in Compile).value.map { f =>
          val js = read(f)
          if (js.contains("addGlobalProps"))
            f
          else {
            f.delete()
            f.createNewFile()
            val writer = new PrintWriter(new FileWriter(f))
            writer.println(bootstrap)
            writer.println(js)
            writer.println(launcher)
            writer.flush()
            f
          }
        }
      }
    },
    persistLauncher in Compile := true,
    persistLauncher in Test := false,
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-async" % "0.9.2"
    ) ++ Dependencies.shared.value
  )

val client = project.in(file("client"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(shared)
  .settings(
    persistLauncher in Compile := true,
    persistLauncher in Test := false,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1",
      "com.github.japgolly.scalajs-react" %%% "core" % "0.11.1"
    ) ++ Dependencies.shared.value
  )
