package opl.util

import java.util.UUID

case class AutowirePayload(id: UUID, path: Seq[String], args: Map[String, String])