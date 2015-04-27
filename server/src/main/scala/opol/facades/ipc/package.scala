package opol.facades

import scala.scalajs.js.Dynamic.global

package object ipc {

  val Ipc = global.require("ipc").asInstanceOf[Ipc]
}