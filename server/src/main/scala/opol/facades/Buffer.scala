package opol.facades

import scala.scalajs.js

@js.native
class Buffer(size: Int) extends js.Object {

  def length: Int = js.native

  def slice(start: Int): Buffer = js.native
  def slice(start: Int, end: Int): Buffer = js.native

  def toString(encoding: String): String = js.native

  def equals(o: Buffer): Boolean = js.native
}

@js.native
object Buffer extends js.Object {

  def alloc(size: Int): Buffer = js.native

  def from(data: String, encoding: String = "utf-8"): Buffer = js.native

  def concat(buffers: js.Array[Buffer]): Buffer = js.native
}

object BufferBuilder {

  def empty: Buffer = Buffer.alloc(0)

  def base64(data: String): Buffer = Buffer.from(data, "base64")
}
