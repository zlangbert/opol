package opol.facades.ipc

import scala.scalajs.js

trait Sender extends js.Object {
  def send(channel: String, data: Any): Unit = js.native
}
