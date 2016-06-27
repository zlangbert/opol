package opol.test.vault

import opol.vault.VaultLoader
import org.scalatest._

import scala.concurrent.ExecutionContext
import scala.async.Async._

class VaultLoaderSpec extends AsyncFlatSpec with Matchers with TestVault {

  override implicit def executionContext: ExecutionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  "VaultLoader" should "load a vault" in async {

    implicit val fs = await { vaultFs }
    val loader = new VaultLoader("freddy.opvault")
    val vault = await { loader.load() }

    vault.isLocked should be (true)
  }

  /*withVault { vault =>
      val profileFile = vault.readFileSync("./freddy.opvault/default/profile.js").toString
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

      /*val r = Opdata.decrypt(masterKey, derivedKey, derivedMac)
      r.toString("hex") should not be empty*/

      val (overviewKey, overviewMac) = {
        val plaintext = Opdata.decrypt(BufferBuilder.base64(profile.overviewKey), derivedKey, derivedMac)
        val hasher = Crypto().createHash("sha512")
        val hashed = hasher.update(plaintext).digest().asInstanceOf[Buffer]

        hashed.take(32) -> hashed.takeRight(32)
      }

      val o = {

        val file = vault.readFileSync("./freddy.opvault/default/band_F.js").toString
            .drop(3).dropRight(2)
        val json = io.circe.parser.parse(file).valueOr(e => throw e)

        val s = json.asObject.get.toMap.values.drop(5).head.asObject.get("o").get.asString.get
        BufferBuilder.base64(s)
      }

      val r = Opdata.decrypt(o, overviewKey, overviewMac)
      Node.log(r)

      succeed
    }*/
}
