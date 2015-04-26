package opl.client

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.all._
import org.scalajs.dom
import org.scalajs.dom.Event

import scala.scalajs.js.JSApp

object Main extends JSApp {

  override def main(): Unit = {

    dom.onload = (e: Event) => {
      React.render(h2("Hi from scala"), dom.document.body)
    }
  }
}