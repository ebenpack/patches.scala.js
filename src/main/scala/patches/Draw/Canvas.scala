package patches.Draw

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object Canvas {

  val canvas = ReactComponentB[ModelProxy[CanvasModel]]("Canvas")
    .render_P(proxy => {
      val dispatch = proxy.dispatch
      val controls = proxy().controls
      <.div(
        ^.className := "canvas",
        proxy().nodes.map(n => patches.Draw.Node(
          dispatch,
          n
        )),
        Controls(dispatch, proxy().controls)
      )
    }
    )
    .build

  def apply(p: ModelProxy[CanvasModel]) =
    canvas(p)
}
