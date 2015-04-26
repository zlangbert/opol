package opl.client.facades

import scalajs.js.Dynamic.global

package object ipc {

  val Ipc = global.require("ipc").asInstanceOf[Ipc]
}