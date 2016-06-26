package opol.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import org.scalajs.dom.html.Input

object LockScreen {

  case class Props(onSubmit: String => Unit)

  class Backend($: BackendScope[Props, _]) {

    val inputRef = Ref[Input]("input")

    def onSubmit(e: ReactEventH)(implicit props: Props) = Callback {
      e.preventDefault()
      dom.console.log(e.target)
      dom.console.log(e.currentTarget)
      val pass = inputRef($).get.value
      if (pass.nonEmpty)
        props.onSubmit(pass)
    }

    def renderInput()(implicit props: Props): ReactElement = {

      val lock = {
        val size = "200px"

        val containerStyle = Seq(
          ^.height := size,
          ^.width := size,
          ^.border := "1px solid #434343",
          ^.borderRadius := size,
          ^.zIndex := "1"
        )

        val innerContainerStyle = Seq(
          ^.height := "100%",
          ^.padding := "0.6rem",
          ^.background := "radial-gradient(circle at center, " +
            "rgba(200,200,200,0.9) 90%," +
            "rgba(150,150,150,1) 100%)",
          ^.borderTop := "1px solid #414141",
          ^.borderRight := "1px solid #515151",
          ^.borderBottom := "1px solid #5e5e5e",
          ^.borderRadius := size
        )

        <.div(containerStyle,
          <.div(innerContainerStyle,
            <.div(
              ^.height := "100%",
              ^.padding := "1.5rem",
              ^.background := "white",
              ^.borderRadius := size,

              <.div(
                ^.height := "100%",
                ^.padding := "1.5rem",
                ^.background := "#d9d9d9",
                ^.borderRadius := size,

                <.div(
                  ^.height := "100%",
                  ^.padding := "1rem",
                  ^.background := "radial-gradient(circle at center, " +
                    "rgba(132,186,224,0.9) 90%," +
                    "rgba(104,146,176,1) 100%)",
                  ^.border := "1px solid #354a5a",
                  ^.borderRadius := size,

                    <.div(
                      ^.display := "flex",
                      ^.justifyContent := "center",
                      ^.alignItems := "center",
                      ^.height := "100%",
                      ^.padding := "1rem",
                      ^.backgroundColor := "#f2f2f2",
                      ^.border := "1px solid #638BA8",
                      ^.borderRadius := size,

                      <.div(
                        ^.width := "24px",
                        ^.height := "75px",
                        ^.backgroundColor := "#666666",
                        ^.borderBottom := "3px solid white"
                      )
                    )
                )
              )
            )
          )
        )
      }

      val input = {

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
            "rgba(145,145,145,1) 0%," +
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
            <.input(^.ref := inputRef, ^.tpe := "password", inputStyle)
          )
        )
      }

      val containerStyle = Seq(
        ^.display := "flex",
        ^.transform := "translate(-150px)"
      )

      <.form(containerStyle, ^.onSubmit ==> onSubmit,
        lock,
        input
      )
    }

    def render(implicit props: Props): ReactElement = {

      val flex = Seq(
        ^.display := "flex"
      )

      val paneStyle = Seq(
        ^.backgroundColor := "#e6e6e6"
      )

      val bandStyle = Seq(
        ^.marginLeft := "auto",
        ^.width := "50px",
        ^.borderLeft := "1px solid #919191",
        ^.borderRight := "1px solid #808080",
        ^.backgroundColor := "#f2f2f2"
      )

      val leftPane =
        <.div(flex, paneStyle, ^.flex := "1",
          <.div(^.borderRight := "2px solid #f9f9f9", ^.flex := "1"),
            <.div(flex, bandStyle,
              <.div(^.marginLeft := "auto", ^.borderRight := "2px solid #fcfcfc")
            )
        )

      val rightPane =
        <.div(flex, paneStyle, ^.flex := "1.75",
          <.div(flex, bandStyle,
            <.div(^.borderLeft := "2px solid #fcfcfc")
          ),
          <.div(flex, ^.borderLeft := "2px solid #f9f9f9", ^.flex := "1", ^.alignItems := "center",
            renderInput()
          )
        )

      <.div(flex, ^.height := "100%",
        leftPane,
        rightPane
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
