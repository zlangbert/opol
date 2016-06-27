package opol.facades

import scala.scalajs.js

@js.native
trait Error extends js.Object {

  def message: String = js.native

  def stack: String = js.native
}
