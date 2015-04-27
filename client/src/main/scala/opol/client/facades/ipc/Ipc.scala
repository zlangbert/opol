package opol.client.facades.ipc

import scala.scalajs.js

trait Ipc extends js.Object {

  def on(channel: String, onMessage: js.Function1[Any, _]): Unit = js.native

  def removeListener(listener: js.Function1[Any, _]): Unit = js.native

  def send(channel: String, data: Any): Unit = js.native
}