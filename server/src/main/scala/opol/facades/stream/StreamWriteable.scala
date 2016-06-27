package opol.facades.stream

import scala.scalajs.js

@js.native
trait StreamWriteable extends js.Object {

  def on(event: String, callback: js.Function): Unit = js.native
}
