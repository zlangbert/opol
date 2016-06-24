package opol.client.controllers

import autowire._
import japgolly.scalajs.react._
import opol.api.Api
import opol.client.Client
import opol.client.components.LockScreen

import scala.concurrent.ExecutionContext.Implicits._

object LockScreenController {

  case class State(foo: String)

  class Backend($: BackendScope[_, _]) {

    def onSubmit(pass: String): Unit = {
      Client[Api].unlock(pass).call().foreach(println)
    }

    def render(): ReactElement = {
      LockScreen(onSubmit)
    }
  }

  val component =
    ReactComponentB[Unit]("Lock")
      .initialState(State(""))
      .renderBackend[Backend]
      .build

  def apply() = component()
}
