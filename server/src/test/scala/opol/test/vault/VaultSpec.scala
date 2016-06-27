package opol.test.vault

import opol.vault.Vault.InvalidPasswordException
import opol.vault.{Vault, VaultLoader}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import scala.async.Async._
import scala.concurrent.{ExecutionContext, Future}

class VaultSpec extends AsyncFlatSpec with Matchers with ScalaFutures
  with TestVault {

  override implicit def executionContext: ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  def testVault: Future[Vault[Vault.State.Locked]] = async {
    implicit val fs = await { vaultFs }
    val loader = new VaultLoader("freddy.opvault")
    await { loader.load() }
  }

  "Vault" should "unlock" in async {

    val vault = await(testVault).unlocked("freddy")

    vault.isLocked should be (false)
  }

  it should "reject an invalid password" in async {

    val vault = await { testVault }

    an [InvalidPasswordException] should be thrownBy {
      vault.unlocked("password")
    }
  }

  it should "lock" in async {

    implicit val fs = await { vaultFs }
    val loader = new VaultLoader("freddy.opvault")
    val vault = {
      val v = await { loader.load() }
      v.unlocked("freddy")
    }

    vault.isLocked should be (false)
    vault.locked().isLocked should be (true)
  }
}
