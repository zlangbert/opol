package opol.facades.ipc

import scala.scalajs.js

@js.native
trait Event extends js.Object {
  def sender: Sender = js.native
}
