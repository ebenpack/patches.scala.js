package patches.Draw

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import dom.MouseEvent
import patches.Draw.{Node => DrawNode, Patch => DrawPatch}

object Canvas {

  class Backend($: BackendScope[ModelProxy[CanvasModel], Unit]) extends OnUnmount {

    def render(proxy: ModelProxy[CanvasModel]) = {
      val nodes = proxy().nodes
      val patches = proxy().patches
      val dispatch = proxy.dispatch
      <.div(
        ^.className := "canvas",
        nodes.map(n => DrawNode(
          n
        )),
        patches.patches.map(p => DrawPatch(
          p
        )),
        Controls(dispatch, proxy().controls)
      )
    }
  }


  val canvas = ReactComponentB[ModelProxy[CanvasModel]]("Canvas")
    .renderBackend[Backend]
    .build

  def apply(p: ModelProxy[CanvasModel]) =
    canvas(p)
}