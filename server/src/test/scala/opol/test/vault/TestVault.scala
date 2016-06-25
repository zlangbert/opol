package opol.test.vault

import opol.facades.Buffer

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.scalajs.js
import scala.scalajs.js.Dynamic
import scala.scalajs.js.Dynamic.global

trait TestVault {

  private val vault = readVault("freddy-2013-12-04.tar.gz")

  def withVault[T](f: Dynamic => T)(implicit ec: ExecutionContext): Future[T] = {
    vault.map(f)
  }

  private def readVault(filename: String): Future[Dynamic] = {

    val promise = Promise[Dynamic]()

    val require = global.require

    val path = require("path")
    val fs = require("fs")
    val targz = require("tar.gz")
    val memfs = require("memfs")

    val fileSystem = {
      val mem = Dynamic.newInstance(memfs.Volume)()
      mem.mountSync("./")
      mem
    }

    val read = fs.createReadStream(
      path.join(global.process.env.RESOURCE_PATH,
        "opol",
        "test",
        filename))

    val parse = targz().createParseStream()

    parse.on("entry", (entry: Dynamic) => {
      if (entry.`type`.asInstanceOf[String] == "File") {
        writeEntry(fileSystem, entry)
      }
    })

    parse.on("end", () => {
      promise.success(fileSystem)
    })

    read.pipe(parse)

    promise.future
  }

  private def writeEntry(fileSystem: Dynamic, entry: js.Dynamic): Unit = {
    val buffers = js.Array[Buffer]()
    entry.on("data", (data: Dynamic) => {
      buffers.push(data.asInstanceOf[Buffer])
    })
    entry.on("end", () => {
      val data = Buffer.concat(buffers)
      fileSystem.writeFileSync(entry.path, data)
    })
  }
}
