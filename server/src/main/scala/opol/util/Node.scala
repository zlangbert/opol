package opol.util

import scala.scalajs.js

object Node {

  def require(module: String): js.Dynamic = {
    js.Dynamic.global.require(module)
  }

  def log(value: js.Any): Unit = {
    js.Dynamic.global.console.log(value)
  }

  def log(format: String, value: js.Any): Unit = {
    js.Dynamic.global.console.log(format, value)
  }
}
