package opol.facades.stream

import scala.scalajs.js

@js.native
trait StreamReadable extends js.Object {

  def on(event: String, callback: js.Function): Unit = js.native

  def pipe(destination: StreamWriteable): Unit = js.native
}
