package opl.client

import japgolly.scalajs.react._
import opl.client.controllers.LockScreenController
import org.scalajs.dom
import org.scalajs.dom.Event

import scala.scalajs.js.JSApp

object Main extends JSApp {

  override def main(): Unit = {

    dom.onload = (e: Event) => {
      React.render(LockScreenController(), dom.document.body)
    }
  }
}