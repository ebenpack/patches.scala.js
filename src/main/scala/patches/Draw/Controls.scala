package patches.Draw

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object Controls {

  case class Props(
                    dispatch: AnyRef => Callback,
                    c: ControlsModel
                  )

  class Backend($: BackendScope[Props, Unit]) {

    def render(p: Props) = {
      val props = $.props.runNow()
      <.div(
        ^.className := "controls",
        props.c.controls.map {
          case (packageName, packageMap) =>
            <.div(
              ^.key := packageName,
              ^.className := "control",
              <.h3(
                packageName
              ),
              packageMap.map {
                case (a, b) =>
                  <.div(
                    ^.key := packageName + '.' + a,
                    <.a(
                      "Add " + a,
                      ^.onClick --> props.dispatch(AddNode(b()))
                    )
                  )
              }
            )
        }
      )
    }
  }

  val controls = ReactComponentB[Props]("Controls")
    .renderBackend[Backend]
    .build

  def apply(dispatch: AnyRef => Callback, c: ControlsModel) =
    controls(Props(dispatch, c))
}
