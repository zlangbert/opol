package opol.client.facades

import japgolly.scalajs.react._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.literal

object ReactMotion {

  private val react = Node.require("react")
  private val motion = Node.require("react-motion")

  def spring(value: Double, config: js.UndefOr[js.Dynamic] = js.undefined): js.Object =
    motion.spring(value, config).asInstanceOf[js.Object]

  object Presets {

    val Gentle = motion.presets.gentle
  }

  object Motion {

    val factory = react.createFactory(motion.Motion)

    def apply(style: js.Object,
              defaultStyle: js.UndefOr[js.Object] = js.undefined,
              onRest: js.UndefOr[js.Object] = js.undefined)
             (children: js.Dynamic => ReactElement) =  {

      factory(
        literal(
          style = style,
          defaultStyle = defaultStyle,
          onRest = onRest
        ),
        children: js.Function
      ).asInstanceOf[ReactElement]
    }
  }

  object TransitionMotion {

    val factory = react.createFactory(motion.TransitionMotion)

    def apply(styles: js.Array[js.Dynamic],
              defaultStyles: js.UndefOr[js.Array[js.Object]] = js.undefined,
              willLeave: js.UndefOr[js.Dynamic => js.Dynamic] = js.undefined,
              willEnter: js.UndefOr[js.Dynamic => js.Dynamic] = js.undefined)
             (children: js.Dynamic => ReactElement) = {

      factory(
        literal(
          styles = styles,
          defaultStyles = defaultStyles,
          willLeave = willLeave.map(f => f: js.Function),
          willEnter = willEnter
        ),
        children: js.Function
      ).asInstanceOf[ReactElement]
    }
  }
}
