package opol.crypto

import opol.facades.{Buffer, BufferBuilder}
import opol.facades.crypto.Crypto
import opol.util.Node

import scala.scalajs.js

object Opdata {

  val HeaderIndex = 0
  val HeaderSize = 8
  val PlaintextLengthSize = 8
  val IVSize = 16
  val MacSize = 32

  val MinimumLength =
    HeaderSize +
      PlaintextLengthSize +
      IVSize +
      16 +
      MacSize

  class InvalidOpdataDataException(msg: String) extends Exception(msg)

  def decrypt(ciphertext: Buffer,
              encryptionKey: Buffer,
              macKey: Buffer): Buffer = {

    val data = ciphertext.dropRight(MacSize)
    val mac = ciphertext.takeRight(MacSize)

    if (ciphertext.length < MinimumLength)
      throw new InvalidOpdataDataException("cipher text is too short")

    if (!verifyHmac(data, mac, macKey))
      throw new InvalidOpdataDataException("HMAC verification failed")

    if (!verifyMetadata(ciphertext))
      throw new InvalidOpdataDataException("invalid metadata")

    val payloadLength = getPayloadLength(data)
    val iv = data.slice(16, 32)
    val payload = data.drop(32)

    if (payload.length < payloadLength)
      throw new InvalidOpdataDataException("payload length did not match header")

    val plaintext = {
      val padded = Crypto.aesDecrypt(encryptionKey, iv, payload, cipher = "aes-256-cbc")
      stripPadding(payloadLength, padded)
    }

    plaintext
  }

  private def verifyHmac(data: Buffer, mac: Buffer, key: Buffer): Boolean = {

    val hmac = Crypto().createHmac("sha256", key)
    hmac.update(data)
    val calculated = hmac.digest().asInstanceOf[Buffer]
    mac.equals(calculated)
  }

  private def verifyMetadata(data: Buffer): Boolean = {
    val expected = Buffer.from("opdata01")
    expected.equals(data.take(8))
  }

  private def getPayloadLength(data: Buffer): Long = {

    val Bignum = js.Dynamic.global.require("bignum")

    val bn = Bignum.fromBuffer(data.slice(8, 16), js.Dynamic.literal(
      endian = "little",
      size = "auto"
    ))
    val n = bn.toNumber()

    // really this could be a 64-bit int (not supported natively on node)
    // but for now just fail if > 32 bits
    if ((n > Int.MaxValue.asInstanceOf[js.Dynamic]).asInstanceOf[Boolean])
      throw new InvalidOpdataDataException("payload size out of range")

    n.asInstanceOf[Int]
  }

  /**
    * Strips padding from decrypted plaintext
    *
    * If the data's length is a multiple of the block size (16 bytes),
    * removes the first block of data. Otherwise throws for now
    *
    * @param plaintextLength length of plaintext
    * @param padded padded data
    * @return a slice of `data` without padding
    */
  private def stripPadding(plaintextLength: Long, padded: Buffer): Buffer = {

    if (plaintextLength % 16 == 0) {
      padded.drop(16)
    } else {
      throw new InvalidOpdataDataException("Unhandled payload length")
    }
  }
}
