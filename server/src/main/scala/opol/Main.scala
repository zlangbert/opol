package opol

import opol.api.{Api, Channels}
import opol.facades.ipc
import opol.facades.ipc._
import opol.util.AutowirePayload

import scala.scalajs.js.Dynamic.{global ⇒ g}
import scala.scalajs.js.{JSApp, Dynamic}
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends JSApp {

  val electron = g.require("electron")

  val app = electron.app
  val BrowserWindow = electron.BrowserWindow
  var mainWindow: Option[Dynamic] = None

  def main(): Unit = {

    app.on("window-all-closed", { () =>
      app.quit()
    })

    app.on("ready", { () =>
      mainWindow = Some (
        Dynamic.newInstance(BrowserWindow)(
          Dynamic.literal(width = 1920, height = 1200)
        )
      )

      mainWindow.foreach { window =>
        window.loadURL("file://" + g.__dirname +
          "/../../../client/target/scala-2.11/classes/html/index.html")
        window.on("closed", { () =>
          mainWindow = None
        })
      }
    })

    val api = new ApiImpl
    Ipc.on(Channels.Autowire, (event: ipc.Event, data: Any) => {

        import io.circe.generic.auto._
        import io.circe.parser._

        val payload = decode[AutowirePayload](data.toString)
          .valueOr(e ⇒ throw e)

        AutowireServer.route[Api](api)(
          autowire.Core.Request(payload.path, payload.args)
        ).foreach(event.sender.send(Channels.Autowire, _))
    })
  }
}
