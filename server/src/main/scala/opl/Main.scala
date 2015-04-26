package opl

import scala.scalajs.js
import js.{JSApp, Dynamic => D, Object => JSObject}
import D.{global => g}

object Main extends JSApp {

  val app = g.require("app")
  val BrowserWindow = g.require("browser-window")
  var mainWindow: Option[D] = None

  def main(): Unit = {

    app.on("window-all-closed", { () =>
      app.quit()
    })

    app.on("ready", { () =>
      mainWindow = Some (
        D.newInstance(BrowserWindow)(D.literal(width = 800, height = 600))
      )

      mainWindow.foreach { window =>
        println("file://" + g.__dirname + "/../../../client/target/scala-2.11/classes/html/index.html")
        window.loadUrl("file://" + g.__dirname + "/../../../client/target/scala-2.11/classes/html/index.html")
        window.on("closed", { () =>
          mainWindow = None
        })
      }
    })
  }
}