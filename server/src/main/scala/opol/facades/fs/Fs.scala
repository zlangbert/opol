package opol.facades.fs

import opol.facades.stream.StreamReadable
import opol.facades.{Buffer, Error}

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global

@js.native
trait Fs extends js.Object {

  def readFile(path: String,
               callback: js.Function2[Error, Buffer, _]): Unit = js.native

  def readFileSync(path: String): Buffer = js.native

  def writeFileSync(path: String, data: Buffer): Unit = js.native

  def createReadStream(path: String): StreamReadable = js.native
}

object Fs {

  val _fs = global.require("fs")

  def apply(): Fs = _fs.asInstanceOf[Fs]

  def readFile(filename: String): String = {
    val b = Fs().readFileSync(filename)
    b.toString("utf8")
  }
}
