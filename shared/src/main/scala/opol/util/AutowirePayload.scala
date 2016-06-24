package opol.util

import java.util.UUID

case class AutowirePayload(path: Seq[String], args: Map[String, String])
