package opol.client.facades

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global

object Node {

  def require(module: String): js.Dynamic = global.require(module)

  def log(value: js.Any): Unit = global.console.log(value)
}
