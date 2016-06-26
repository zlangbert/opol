package opol.vault

class Vault[State <: Vault.State] {

  import Vault.State._

  def unlock()(implicit ev: State =:= Locked): Vault[Unlocked] = {
    ???
  }

  def lock()(implicit ev: State =:= Unlocked): Vault[Unlocked] = {
    ???
  }
}

object Vault {

  sealed trait State
  object State {
    sealed trait Locked extends State
    sealed trait Unlocked extends State
  }

  case class Profile(profileName: String,
                     lastUpdatedBy: String,
                     updatedAt: Long,
                     salt: String,
                     masterKey: String,
                     iterations: Int,
                     uuid: String,
                     overviewKey: String,
                     createdAt: Long)
}
