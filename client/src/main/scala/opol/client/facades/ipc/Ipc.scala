package opol.client.facades.ipc

import opol.facades.ipc.Event

import scala.scalajs.js

@js.native
trait Ipc extends js.Object {

  def on(channel: String, onMessage: js.Function2[Event, js.Any, _]): Unit = js.native

  def once(channel: String, listener: js.Function2[Event, js.Any, _]): Unit = js.native

  def send(channel: String, data: js.Any): Unit = js.native
}
