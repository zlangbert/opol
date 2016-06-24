package opol.test.crypto

import org.scalatest._

class CryptoTest extends AsyncFlatSpec with Matchers with TestVault {

  implicit override def executionContext =
    scala.concurrent.ExecutionContext.Implicits.global

  "Crypto" should "work" in {

    vault.map { mem =>
      val profile = mem.readFileSync("./onepassword_data/default/profile.js").toString
      profile should not be empty
    }
  }
}
