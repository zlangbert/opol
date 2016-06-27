package opol.vault

import opol.crypto.Opdata
import opol.facades.{Buffer, BufferBuilder}
import opol.facades.crypto.Crypto
import opol.vault.Vault.{InvalidPasswordException, Key, KeyBuildException, Keys}
import opol.vault.data.Profile

import scala.util.{Failure, Success, Try}

class Vault[State <: Vault.State](loader: VaultLoader,
                                  profile: Profile,
                                  keys: Option[Keys]) {

  import Vault.State._

  def this(loader: VaultLoader, profile: Profile) = {
    this(loader, profile, None)
  }

  def unlocked(password: String)(implicit ev: State =:= Locked): Vault[Unlocked] = {

    val keys = buildKeys(password)
    new Vault[Unlocked](loader, profile, Option(keys))
  }

  def locked()(implicit ev: State =:= Unlocked): Vault[Unlocked] = {
    new Vault[Unlocked](loader, profile)
  }

  def isLocked: Boolean = keys.isEmpty

  private def buildKeys(password: String): Keys = {

    val derived = {
      val salt = BufferBuilder.base64(profile.salt)
      val d = Crypto.pbkdf2(password, salt, profile.iterations, 64, "sha512")
      Key(d.take(32), d.takeRight(32))
    }

    val master = Try(buildKey(derived, profile.masterKey)) match {
      case Success(k) => k
      case Failure(e) => throw new InvalidPasswordException("incorrect password", e)
    }

    val overview = Try(buildKey(derived, profile.overviewKey)) match {
      case Success(k) => k
      case Failure(e) => throw new KeyBuildException("overview key decryption failure", e)
    }

    Keys(derived, master, overview)
  }

  private def buildKey(derived: Key, ciphertext: String): Key = {

    val plaintext = Opdata.decrypt(BufferBuilder.base64(ciphertext), derived.key, derived.mac)
    val hasher = Crypto().createHash("sha512")
    val hashed = hasher.update(plaintext).digest().asInstanceOf[Buffer]

    Key(hashed.take(32), hashed.takeRight(32))
  }
}

object Vault {

  sealed trait State
  object State {
    sealed trait Locked extends State
    sealed trait Unlocked extends State
  }

  case class Key(key: Buffer, mac: Buffer)
  case class Keys(derived: Key,
                  master: Key,
                  overview: Key)

  abstract class VaultException(msg: String, cause: Throwable) extends Exception(msg, cause)
  class InvalidPasswordException(msg: String, cause: Throwable) extends VaultException(msg, cause)
  class KeyBuildException(msg: String, cause: Throwable) extends VaultException(msg, cause)
}
