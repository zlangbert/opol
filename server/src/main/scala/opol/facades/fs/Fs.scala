package opol.facades.fs

import opol.facades.Buffer

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global

trait Fs extends js.Object {

  def readFileSync(filename: String): Buffer = js.native
}

object Fs {

  val _fs = global.require("fs").asInstanceOf[Fs]

  def readFile(filename: String): String = {
    val b = _fs.readFileSync(filename)
    b.toString("utf8")
  }
}