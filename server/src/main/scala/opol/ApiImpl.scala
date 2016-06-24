package opol

import opol.api.Api
import opol.crypto.{EncryptionKey, EncryptionKeys}
import opol.facades._
import opol.facades.crypto._
import opol.facades.fs._

import scala.concurrent.Future
import scala.scalajs.js

class ApiImpl extends Api {

  val ek = "/Users/zach/Dropbox/1Password.agilekeychain/data/default/encryptionKeys.js"

  def masterKey(pass: String, key: EncryptionKey): Buffer = {

    val raw = BufferBuilder(key.data, "base64")
    val data = raw.slice(16)
    val salt = raw.slice(8, 16)

    val iv = Crypto.pbkdf2(pass, salt, key.iterations, 32, "sha1")
    Crypto.aesDecrypt(iv.slice(0, 16), iv.slice(16), data)
  }

  def validationKey(master: Buffer, key: EncryptionKey): Buffer = {

    val data = BufferBuilder(key.validation, "base64")
    val salt = data.slice(8, 16)

    var iv = new Buffer(0)
    var prev = new Buffer(0)

    while (iv.length < 32) {
      prev = {
        val md5 = Crypto._crypto.createHash("md5")
        md5.update(prev)
        md5.update(master)
        md5.update(salt)
        md5.digest().asInstanceOf[Buffer]
      }
      iv = Buffer.concat(js.Array(iv, prev))
    }

    Crypto.aesDecrypt(iv.slice(0, 16), iv.slice(16), data.slice(16))
  }

  override def unlock(pass: String): Future[Boolean] = {

    val keys = {
      val keyData = Fs.readFile(ek)
      import io.circe._
      import io.circe.generic.auto._
      import io.circe.parser._
      import io.circe.syntax._
      decode[EncryptionKeys](keyData).valueOr(e ⇒ throw e.getCause)
    }

    val authenticated = keys.list.forall { key =>
      val master = masterKey(pass, key)
      val validation = validationKey(master, key)
      master.equals(validation)
    }

    Future.successful(authenticated)
  }
}
