package opol.client.components.lockscreen

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import opol.client.Styles
import org.scalajs.dom

object LockScreen {

  case class Props(onSubmit: String => Callback,
                   animationValues: AnimationValues,
                   unlocked: Boolean)
  case class AnimationValues(leftPane: Double, rightPane: Double)

  class Backend($: BackendScope[Props, _]) {

    def onSubmit(e: ReactEventI)(implicit props: Props) = {
      e.preventDefault()

      val input = e.target.querySelector("input").asInstanceOf[dom.html.Input]
      props.onSubmit(input.value)
    }

    def panels()(implicit props: Props) = {

      val bandStyle = Seq(
        ^.marginLeft := "auto",
        ^.width := "50px",
        ^.borderLeft := "1px solid #919191",
        ^.borderRight := "1px solid #808080",
        ^.backgroundColor := "#f2f2f2"
      )

      val paneStyle = Seq(
        ^.backgroundColor := "#e6e6e6"
      )

      def leftPane = {

        <.div(Styles.flex, paneStyle, ^.flex := "1",
          ^.transform := s"translate(${props.animationValues.leftPane}px)",

          <.div(^.borderRight := "2px solid #f9f9f9", ^.flex := "1"),
          <.div(Styles.flex, bandStyle,
            <.div(^.marginLeft := "auto", ^.borderRight := "2px solid #fcfcfc")
          )
        )
      }

      def rightPane = {

        val formStyle = Seq(
          ^.display := "flex",
          ^.transform := "translate(-150px)"
        )

        <.div(Styles.flex, paneStyle, ^.flex := "1.75",
          ^.transform := s"translate(${props.animationValues.rightPane}px)",

          <.div(Styles.flex, bandStyle,
            <.div(^.borderLeft := "2px solid #fcfcfc")
          ),
          <.div(Styles.flex, ^.borderLeft := "2px solid #f9f9f9", ^.flex := "1", ^.alignItems := "center",
            <.form(formStyle, ^.onSubmit ==> onSubmit,
              Lock(size = 200, unlocked = props.unlocked),
              PasswordInput()
            )
          )
        )
      }

      Seq(
        leftPane,
        rightPane
      )
    }

    def render(implicit props: Props): ReactElement = {

      <.div(Styles.flex, ^.height := "100%", ^.overflow := "hidden",
        panels()
      )
    }
  }

  val component =
    ReactComponentB[Props]("LockScreen")
      .renderBackend[Backend]
      .shouldComponentUpdate(_ => true)
      .build

  def apply(onSubmit: String => Callback,
            animationValues: AnimationValues,
            unlocked: Boolean) =
    component(Props(onSubmit, animationValues, unlocked))
}
