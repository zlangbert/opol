package opl.facades.ipc

import scala.scalajs.js

trait Event extends js.Object {
  def sender: Sender = js.native
}
