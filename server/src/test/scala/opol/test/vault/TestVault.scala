package opol.test.vault

import opol.facades.Buffer
import opol.facades.fs.Fs
import opol.facades.path.Path
import opol.facades.stream.StreamWriteable
import opol.test.helpers.VirtualFs
import opol.util.Node

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.scalajs.js
import scala.scalajs.js.Dynamic
import scala.scalajs.js.Dynamic.global

trait TestVault extends VirtualFs {

  val vaultFs = readVault("freddy-2013-12-04.tar.gz")

  def withVault[T](f: Fs => T)(implicit ec: ExecutionContext): Future[T] = {
    vaultFs.map(f)
  }

  private def readVault(filename: String): Future[Fs] = {
    withFs { implicit fs =>

      val promise = Promise[Fs]()

      val targz = Node.require("tar.gz")

      val read = Fs().createReadStream(
        Path().join(
          global.process.env.RESOURCE_PATH.asInstanceOf[String],
          "opol",
          "test",
          filename
        )
      )

      val parse = targz().createParseStream().asInstanceOf[StreamWriteable]

      parse.on("entry", (entry: Dynamic) => {
        if (entry.`type`.asInstanceOf[String] == "File") {
          writeEntry(entry)
        }
      })
      parse.on("end", () => {
        promise.success(fs)
      })

      read.pipe(parse)

      promise.future
    }
  }

  private def writeEntry(entry: js.Dynamic)(implicit fs: Fs): Unit = {
    val buffers = js.Array[Buffer]()
    entry.on("data", (data: Dynamic) => {
      buffers.push(data.asInstanceOf[Buffer])
    })
    entry.on("end", () => {
      val path = entry.path.asInstanceOf[String]
      val data = Buffer.concat(buffers)
      fs.writeFileSync(path, data)
    })
  }
}
