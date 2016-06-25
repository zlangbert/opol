package opol.test.vault

import opol.crypto.Opdata
import opol.facades.BufferBuilder
import opol.facades.crypto.Crypto
import opol.util.JavascriptVirtualMachine
import opol.vault.Vault
import org.scalatest._

import scala.scalajs.js.JSON

class VaultLoaderSpec extends AsyncFlatSpec with Matchers with TestVault {

  implicit override def executionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  "VaultLoader" should "work" in {
    withVault { vault =>
      val profileFile = vault.readFileSync("./onepassword_data/default/profile.js").toString
      profileFile should not be empty

      val vm = new JavascriptVirtualMachine
      val context = vm.runDynamic(profileFile)

      val profile = {

        import io.circe.generic.auto._
        import io.circe.parser._

        val str = JSON.stringify(context.profile)
        decode[Vault.Profile](str).valueOr(e => throw e)
      }

      profile.profileName shouldEqual "default"

      val masterKey = BufferBuilder.base64(profile.masterKey)

      val salt = BufferBuilder.base64(profile.salt)
      val result = Crypto.pbkdf2("freddy", salt, profile.iterations, 64, "sha512")

      val derivedKey = result.slice(0, 32)
      val derivedMac = result.slice(32)

      val r = Opdata.decrypt(masterKey, derivedKey, derivedMac)

      r.toString("hex") should not be empty
    }
  }
}
