package opol.client.facades

import scalajs.js.Dynamic.global

package object ipc {

  val Ipc = global.require("electron").ipcRenderer.asInstanceOf[Ipc]
}
