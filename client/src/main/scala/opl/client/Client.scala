package opl.client

import java.util.UUID

import opl.client.facades.ipc._
import opl.util.AutowirePayload
import upickle._

import scala.concurrent.{Future, Promise}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js

object Client extends autowire.Client[String, upickle.Reader, upickle.Writer] {

  override def doCall(req: Client.Request): Future[String] = {

    val promise = Promise[String]()
    val id = UUID.randomUUID()

    val payload = AutowirePayload(id, req.path, req.args)

    val complete: js.Function1[Any, _] = (data: Any) => {
      promise.success(data.asInstanceOf[String])
    }

    Ipc.on(s"autowire.$id", complete)

    Ipc.send("autowire", upickle.write(payload))

    // remove listener that will never be used again
    // TODO: this fails saying the "listener must be a function"
    /*promise.future.onSuccess {
      case _ => Ipc.removeListener(complete)
    }*/

    promise.future
  }

  override def write[Result: Writer](r: Result): String = {
    upickle.write(r)
  }

  override def read[Result: Reader](p: String): Result = {
    upickle.read[Result](p)
  }
}