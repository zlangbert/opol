package opol.client.controllers

import autowire._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import opol.api.Api
import opol.client.Client
import opol.client.components.lockscreen.LockScreen
import opol.client.facades.ReactMotion
import org.scalajs.dom

import scala.concurrent.ExecutionContext.Implicits._
import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal

object LockScreenController {

  case class State(unlocked: Boolean)

  class Backend($: BackendScope[_, State]) {

    def onSubmit(pass: String) = Callback.future {
      Client[Api].unlock(pass).call().map { r =>
        $.modState(_.copy(unlocked = true))
      }
    }

    def render(state: State): ReactElement = {

      val s: js.Dynamic =
        if (state.unlocked)
          literal(
            key = "unlocked",
            style = literal()
          )
        else literal(
          key = "locked",
          style = literal(
            leftPane = ReactMotion.spring(0),
            rightPane = ReactMotion.spring(0)
          )
        )

      ReactMotion.TransitionMotion(
        styles = js.Array(
          s
        ),
        willLeave = (style: js.Dynamic) => {
          literal(
            leftPane = ReactMotion.spring(
              dom.window.innerWidth / -3,
              ReactMotion.Presets.Gentle
            ),
            rightPane = ReactMotion.spring(
              dom.window.innerWidth / 2,
              ReactMotion.Presets.Gentle
            )
          )
        }
      ) { styles =>

        val children = styles.map { style: js.Dynamic =>

          if (style.key.asInstanceOf[String] == "locked") {

            LockScreen(
              onSubmit,
              LockScreen.AnimationValues(
                leftPane = style.style.leftPane.asInstanceOf[Double],
                rightPane = style.style.rightPane.asInstanceOf[Double]
              ),
              state.unlocked
            )
          } else
            <.div().render
        }.asInstanceOf[ReactComponentU_]

        <.div(^.height := "100%", children)
      }
    }
  }

  val component =
    ReactComponentB[Unit]("Lock")
      .initialState(State(unlocked = false))
      .renderBackend[Backend]
      .build

  def apply() = component()
}
