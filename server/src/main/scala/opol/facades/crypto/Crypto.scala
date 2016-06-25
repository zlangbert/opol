package opol.facades.crypto

import opol.facades.Buffer

import scala.concurrent.{Promise, Future}
import scala.scalajs.js
import scala.scalajs.js.Dynamic.global

@js.native
trait Crypto extends js.Object {

  def createHash(name: String): js.Dynamic = js.native

  def createDecipheriv(algorithm: String, key: Buffer, iv: Buffer): js.Dynamic = js.native

  def pbkdf2Sync(password: String, salt: Buffer, iterations: Int, keyLength: Int,
             digest: js.UndefOr[String]): Buffer = js.native

  def createHmac(algorithm: String, key: Buffer): js.Dynamic = js.native
}

object Crypto {

  val _crypto = global.require("crypto")

  def apply(): Crypto = _crypto.asInstanceOf[Crypto]

  def pbkdf2(password: String, salt: Buffer, iterations: Int, keyLength: Int,
             digest: String): Buffer = {
    Crypto().pbkdf2Sync(password, salt, iterations, keyLength, digest)
  }

  def aesDecrypt(key: Buffer, iv: Buffer, data: Buffer, cipher: String): Buffer = {
    val decipher = Crypto._crypto.createDecipheriv(cipher, key, iv)
    decipher.update(data).asInstanceOf[Buffer]
  }
}
