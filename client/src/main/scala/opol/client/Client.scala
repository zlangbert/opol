package opol.client

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import opol.api.Channels
import opol.client.facades.ipc._
import opol.facades.ipc.Event
import opol.util.AutowirePayload

import scala.concurrent.{Future, Promise}
import scala.scalajs.js

object Client extends autowire.Client[String, Decoder, Encoder] {

  override def doCall(req: Client.Request): Future[String] = {

    val promise = Promise[String]()
    val payload = AutowirePayload(req.path, req.args)

    val listener = (event: Event, data: js.Any) => {
      promise.success(data.asInstanceOf[String])
    }

    Ipc.once(Channels.Autowire, listener)
    Ipc.send(Channels.Autowire, payload.asJson.noSpaces)

    promise.future
  }

  override def write[Result: Encoder](r: Result): String = {
    r.asJson.noSpaces
  }

  override def read[Result: Decoder](p: String): Result = {
    decode[Result](p).valueOr(e ⇒ throw e)
  }
}
