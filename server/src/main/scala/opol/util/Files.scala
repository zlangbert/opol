package opol.util

import opol.facades.fs.Fs
import opol.facades.{Buffer, Error}

import scala.concurrent.{Future, Promise}

object Files {

  class FilesException(message: String) extends Exception(message)

  def read(path: String)(implicit fs: Fs): Future[Buffer] = {
    val promise = Promise[Buffer]

    fs.readFile(path, (err: Error, data: Buffer) => {
      if (err != null) {
        promise.failure(new FilesException(err.message))
      } else {
        promise.success(data)
      }
    })

    promise.future
  }
}
