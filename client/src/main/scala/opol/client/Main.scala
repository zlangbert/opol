package opol.client

import japgolly.scalajs.react._
import opol.client.controllers.LockScreenController
import org.scalajs.dom

import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.JSApp

object Main extends JSApp {

  global.React = global.require("react")
  global.ReactDOM = global.require("react-dom")

  override def main(): Unit = {
    ReactDOM.render(
      LockScreenController(),
      dom.document.getElementById("main")
    )
  }
}
