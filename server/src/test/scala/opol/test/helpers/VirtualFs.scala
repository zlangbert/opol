package opol.test.helpers

import opol.facades.fs.Fs
import opol.util.Node

import scala.scalajs.js.Dynamic

trait VirtualFs {

  def withFs[T](f: Fs => T): T = {
    val memfs = Node.require("memfs")
    val mem = Dynamic.newInstance(memfs.Volume)()
    mem.mountSync("./")

    f(mem.asInstanceOf[Fs])
  }
}
