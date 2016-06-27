package opol.facades.path

import opol.util.Node

import scala.scalajs.js

@js.native
trait Path extends js.Object {

  def join(paths: String*): String = js.native
}

object Path {

  val _path = Node.require("path")

  def apply(): Path = _path.asInstanceOf[Path]
}
