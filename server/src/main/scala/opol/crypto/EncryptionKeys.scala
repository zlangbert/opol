package opol.crypto

case class EncryptionKeys(list: Seq[EncryptionKey])

case class EncryptionKey(data: String, validation: String, identifier: String,
                         level: String, iterations: Int)