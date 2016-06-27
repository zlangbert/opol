package opol.vault

import opol.facades.fs.Fs
import opol.facades.path.Path
import opol.util.{Files, JavascriptVirtualMachine}
import opol.vault.data.Profile

import scala.async.Async._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js.JSON

/**
  * Loads vault metadata and encrypted keys into memory
  */
class VaultLoader(path: String) {

  private val BasePath = Path().join(path, "default")

  def load()(implicit fs: Fs): Future[Vault[Vault.State.Locked]] = async {

    val profile = await (loadProfile())

    new Vault[Vault.State.Locked](this, profile)
  }

  private def loadProfile()(implicit fs: Fs): Future[Profile] = {

    Files.read(Path().join(BasePath, "profile.js")).map { data =>

      val vm = new JavascriptVirtualMachine
      val context = vm.runDynamic(data.toString("utf-8"))

      import io.circe.generic.auto._
      import io.circe.parser._

      val str = JSON.stringify(context.profile)
      decode[Profile](str).valueOr(e => throw e)
    }
  }
}

object VaultLoader {

  class VaultLoadingException()
}
