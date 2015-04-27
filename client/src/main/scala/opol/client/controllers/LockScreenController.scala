package opol.client.controllers

import autowire._
import japgolly.scalajs.react._
import opol.api.Api
import opol.client.Client
import opol.client.components.LockScreen

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object LockScreenController {

  case class State()

  class Backend($: BackendScope[_, State]) {

    def onSubmit(pass: String): Unit = {
      Client[Api].unlock(pass).call().foreach(println)
    }
  }

  val component =
    ReactComponentB[Unit]("LockScreenController")
      .initialState(State())
      .backend(new Backend(_))
      .render((_, state, backend) =>
        LockScreen(backend.onSubmit)
      )
      .build

  def apply() = component(())
}