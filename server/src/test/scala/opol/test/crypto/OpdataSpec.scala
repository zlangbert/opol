package opol.test.crypto

import opol.crypto.Opdata
import opol.facades.Buffer
import org.scalatest._

class OpdataSpec extends FlatSpec with Matchers {

  "Opdata" should "decrypt ciphertext" in {

    val ciphertext =
      "6f706461746130310001000000000000237c26e13beb237a85b8ea" +
      "cc4bddd111a7bb7bee7cf71f019df9268cb3751d563d1bebf0331e" +
      "7def4c26eeb90e61d2c2339b3c2d23ce75e969f250a1be82373282" +
      "3687950be19722f2dc92f02e614352c082d04358c421c1ddc90d07" +
      "d8c6c9fb46255846ef950f14547e5b72b32a0e64cf3d24646d41b7" +
      "fdd57534a1dd808d15e8dfe4299ef7ee8a3e923dc28496504cacb0" +
      "be647a4600797ade6cb41694c2eb4d41b674ce762d66e98895fde9" +
      "8dda862b84720874b09b080b50ef9514b4ea0e3a19f5d51ccb8850" +
      "cd26623e56dadef2bcbc625194dd107f663a7548f991803075874e" +
      "cc4fc98b785b4cd56c3ce9bcb23ccf70f1908fc85a5b9520cd20d9" +
      "d26a3bfb29ac289c1262302c82f6b0877d566369b98fb551fb9d04" +
      "4434c4cb1c50dcb5bb5a07ad0315fd9742d7d0edc9b9ed685bfa76" +
      "978e228fdaa237dae4152731"

    val key = Buffer.from("63b075de858949559d4faa9d348bf10bdaa0e567ad943d7803f2291c9342aaaa", "hex")
    val mac = Buffer.from("ff3ab426ce55bf097b252b3f2df1c4ba4312a6960180844d7a625bc0ab40c35e", "hex")

    val plaintext = Opdata.decrypt(Buffer.from(ciphertext, "hex"), key, mac)

    val expected = Buffer.from(
      "1b67a08f69b95ceba73c9522a3a9672e7343f737656e5f592bbb203294b6ff6a" +
      "bda1c07ec766666d52f0cad6dd1d47acd39cd496dcc66727947d0f3e8724a733" +
      "1e81e090509bce4056f20a7e0b1f4bb38dd1f4e904b4996e8db79a1ed3b62b89" +
      "4cdca19c3029270bfdd9c6f584ea0963da33696a01aabbdf1fb642a947c6f24d" +
      "4d1ad9a75c5f1675b60a7c015cbc6e4b436a6a388cde22be4ba52b22bce666c2" +
      "35edd6b86744870fd7da1609b2353c86c1c44aab8b9e3c9a2a44d3ee4013b5f1" +
      "88f4f5db96d8e997f977db077d3e9643c5a9c9cf71d0d024956fe8ed3e09c556" +
      "7b58703293be488f720da7d03767e718bcb845890f223551bc51016064bc8ada", "hex")

    plaintext.equals(expected) shouldBe true
  }
}

