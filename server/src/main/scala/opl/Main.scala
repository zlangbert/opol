package opl

import opl.api.Api
import opl.facades.ipc
import opl.facades.ipc._
import opl.util.AutowirePayload

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.Dynamic.{global => g}
import scala.scalajs.js.{Dynamic => D, JSApp}

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
        window.loadUrl("file://" + g.__dirname +
          "/../../../client/target/scala-2.11/classes/html/index.html")
        window.on("closed", { () =>
          mainWindow = None
        })
      }
    })

    val api = new ApiImpl
    Ipc.on("autowire", (event: ipc.Event, data: Any) => {

        val payload = upickle.read[AutowirePayload](data.toString)

        AutowireServer.route[Api](api)(
          autowire.Core.Request(payload.path, payload.args)
        ).foreach(event.sender.send(s"autowire.${payload.id}", _))

    })
  }
}