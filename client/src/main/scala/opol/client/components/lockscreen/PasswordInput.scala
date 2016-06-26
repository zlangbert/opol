package opol.client.components.lockscreen

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

private object PasswordInput {

  case class Props()

  class Backend($: BackendScope[Props, _]) {

    def render(implicit props: Props): ReactElement = {

      val containerStyle = Seq(
        ^.alignSelf := "center",
        ^.marginLeft := "-30px",
        ^.borderTop := "1px solid #f0f0f0",
        ^.borderRight := "1px solid #f0f0f0",
        ^.borderBottom := "2px solid #ffffff",
        ^.borderRadius := "16px"
      )

      val innerContainerStyle = Seq(
        ^.padding := "0.7rem",
        ^.background := "linear-gradient(to bottom," +
          "rgba(175,175,175,1) 0%," +
          "rgba(204,204,204,0) 10%," +
          "rgba(204,204,204,1) 90%," +
          "rgba(196,196,196,1) 100%)",
        ^.borderTop := "1px solid #414141",
        ^.borderRight := "1px solid #515151",
        ^.borderBottom := "1px solid #5e5e5e",
        ^.borderRadius := "16px"
      )

      val inputStyle = Seq(
        ^.width := "600px",
        ^.padding := "1.5rem 2rem 1.5rem 3rem",
        ^.fontSize := "2rem",
        ^.border := "1px solid #474747",
        ^.borderRadius := "16px",
        ^.boxShadow := "inset 0px 8px 5px -5px #b8b8b8",
        ^.autoFocus := true,
        ^.placeholder := "Enter your Master Password"
      )

      <.div(containerStyle,
        <.div(innerContainerStyle,
          <.input(inputStyle, ^.tpe := "password")
        )
      )
    }
  }

  val component = ReactComponentB[Props](getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build

  def apply() = component(Props())
}
