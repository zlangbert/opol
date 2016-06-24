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

  def concat(buffers: js.Array[Buffer]): Buffer = js.native
}

object BufferBuilder {
  def apply(data: String, encoding: String): Buffer =
    js.Dynamic.newInstance(
      Buffer.asInstanceOf[js.Dynamic]
    )(data, encoding).asInstanceOf[Buffer]
}
