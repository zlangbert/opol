package opol.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.html.Input

object LockScreen {

  case class Props(onSubmit: String => Unit)

  class Backend($: BackendScope[Props, _]) {

    val inputRef = Ref[Input]("input")

    def onSubmit(e: ReactEventH)(implicit props: Props) = Callback {
      e.preventDefault()
      val pass = inputRef($).get.value
      if (pass.nonEmpty)
        props.onSubmit(pass)
    }

    def render(implicit props: Props): ReactElement = {

      val containerStyle = Seq(
        ^.display := "flex",
        ^.alignItems := "center",
        ^.justifyContent := "center",
        ^.height := "100%"
      )

      val inputStyle = Seq(
        ^.padding := "1rem",
        ^.fontSize := "3rem",
        ^.autoFocus := true
      )

      <.form(containerStyle, ^.onSubmit ==> onSubmit,
        <.input(^.ref := inputRef, ^.tpe := "password", inputStyle)
      )
    }
  }

  val component =
    ReactComponentB[Props]("LockScreen")
      .renderBackend[Backend]
      .build

  def apply(onSubmit: String => Unit) =
    component(Props(onSubmit))
}
