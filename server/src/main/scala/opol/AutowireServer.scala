package opol

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object AutowireServer extends autowire.Server[String, Decoder, Encoder] {

  def read[Result : Decoder](p: String): Result = {
    decode[Result](p).valueOr(e ⇒ throw e.getCause)
  }
  def write[Result : Encoder](r: Result): String = {
    r.asJson.noSpaces
  }
}
