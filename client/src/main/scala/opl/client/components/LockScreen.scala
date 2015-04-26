package opl.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.html.Input

object LockScreen {

  case class Props(onSubmit: String => Unit)

  class Backend($: BackendScope[Props, _]) {
    val inputRef = Ref[Input]("input")

    def onSubmit(e: ReactEventH): Unit = {
      e.preventDefault()
      val pass = inputRef($).get.getDOMNode().value
      if (pass.nonEmpty)
        $.props.onSubmit(pass)
    }
  }

  val containerStyle = Seq(
    ^.display := "flex",
    ^.alignItems := "center",
    ^.justifyContent := "center",
    ^.height := "100%"
  )

  val inputStyle = Seq(
    ^.padding := "1rem",
    ^.fontSize := "3rem"
  )

  val component =
    ReactComponentB[Props]("LockScreen")
      .stateless
      .backend(new Backend(_))
      .render((props, _, backend) =>
        <.form(containerStyle, ^.onSubmit ==> backend.onSubmit,
          <.input(^.ref := backend.inputRef, ^.tpe := "password", inputStyle)
        )
      )
      .build

  def apply(onSubmit: String => Unit) =
    component(Props(onSubmit))
}