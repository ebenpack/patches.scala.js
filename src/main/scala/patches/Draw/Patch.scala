package patches.Draw

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.OnUnmount

object Patch {

  case class Props(
                    dispatch: AnyRef => Callback,
                    patch: PatchModel
                  )

  class Backend($: BackendScope[Props, Unit]) extends OnUnmount {

    def render(p: Props) = {

      var (x1, x2, y1, y2) = (p.patch.x1, p.patch.x2, p.patch.y1, p.patch.y2)

      if (x2 < x1) {
        var temp = x1
        x1 = x2
        x2 = temp
        temp = y1
        y1 = y2
        y2 = temp
      }
      val length = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
      val angle = Math.atan2(y2 - y1, x2 - x1)
      val top = y1 + 0.5 * length * Math.sin(angle) + "px"
      val left = x1 - 0.5 * length * (1 - Math.cos(angle)) + "px"
      <.hr(
        ^.className := "patch",
        ^.position := "absolute",
        ^.width := length,
        ^.top := top,
        ^.left := left,
        ^.transform := s"rotate(${angle}rad)"
      )
    }
  }

  val component = ReactComponentB[Props]("Patch").renderBackend[Backend].build

  def apply(dispatch: AnyRef => Callback, p: PatchModel) =
    component.withKey(p.uuid.toString)(Props(dispatch, p))
}
