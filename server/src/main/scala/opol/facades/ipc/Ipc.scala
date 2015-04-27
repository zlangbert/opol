package opol.facades.ipc

import scala.scalajs.js

trait Ipc extends js.Object {

  def on(channel: String, onMessage: js.Function2[Event, Any, _]): Unit = js.native
}