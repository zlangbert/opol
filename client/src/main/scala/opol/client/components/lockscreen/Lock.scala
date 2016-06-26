package opol.client.components.lockscreen

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import opol.client.facades.{Node, ReactMotion}

import scala.scalajs.js.Dynamic.literal

object Lock {

  case class Props(size: Int, unlocked: Boolean)

  class Backend($: BackendScope[Props, _]) {

    def Circle(size: String,
               padding: String,
               background: String,
               border: String = "",
               other: Seq[TagMod] = Seq.empty)(children: ReactElement*): ReactElement = {

      <.div(
        ^.height := "100%",
        ^.padding := padding,
        ^.background := background,
        ^.border := border,
        ^.borderRadius := size,
        other,
        children
      )
    }

    val keyHole: Seq[ReactElement] = {

      val hole =
        <.div(
          ^.width := 24.px,
          ^.height := 75.px,
          ^.backgroundColor := "#666666",
          ^.borderBottom := "3px solid white",
          ^.boxShadow := "inset 0px 8px 20px 2px #000000"
        )

      def tooth(flip: Boolean = false) = {

        val factor = if (flip) -1 else 1
        val size = 8

        <.div(
          ^.backgroundColor := "#e6e6e6",
          ^.width := 8.px,
          ^.height := 8.px,
          ^.transform := s"translate(${(size / 2 * factor).px}) rotate(45deg)",
          ^.marginBottom := (12*factor).px
        )
      }

      Seq(
        tooth(),
        hole,
        tooth(flip = true)
      )
    }

    def render(implicit props: Props): ReactElement = {

      val size = s"${props.size}px"

      val containerStyle = Seq(
        ^.height := size,
        ^.width := size,
        ^.border := "1px solid #434343",
        ^.borderRadius := size,
        ^.zIndex := "1"
      )

      val innerContainerStyle = Seq(
        ^.height := "100%",
        ^.padding := 0.6.rem,
        ^.background := "radial-gradient(circle at center, " +
          "rgba(200,200,200,0.9) 90%," +
          "rgba(150,150,150,1) 100%)",
        ^.borderTop := "1px solid #414141",
        ^.borderRight := "1px solid #515151",
        ^.borderBottom := "1px solid #5e5e5e",
        ^.borderRadius := size
      )

      ReactMotion.Motion(
        style = literal(
          rotation = ReactMotion.spring({
            if (props.unlocked) -90 else 0
          }, ReactMotion.Presets.Gentle)
        )
      ) { style =>

        <.div(containerStyle, ^.transform := "rotate(" + style.rotation + "deg)",
          <.div(innerContainerStyle,

            Circle(size, padding = "1.5rem", background = "#ffffff")(
              Circle(size, padding = "1.5rem", background = "#d9d9d9")(

                Circle(size,
                  padding = "1rem",
                  background =
                    "radial-gradient(circle at center, " +
                      "rgba(132,186,224,0.9) 90%," +
                      "rgba(104,146,176,1) 100%)",
                  border = "1px solid #354a5a"
                )(

                  Circle(size, padding = "2.8rem", background = "#f2f2f2")(

                    Circle(size,
                      padding = 0.px,
                      background = "#e6e6e6",
                      border = "1px solid #cfcfcf",
                      other = Seq(
                        ^.display := "flex",
                        ^.justifyContent := "center",
                        ^.alignItems := "center"
                      )
                    )(keyHole: _*)
                  )
                )
              )
            )
          )
        )
      }
    }
  }

  val component = ReactComponentB[Props](getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build

  def apply(size: Int, unlocked: Boolean) = component(Props(size, unlocked))
}
