package opol.vault

class Vault {}

object Vault {

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
