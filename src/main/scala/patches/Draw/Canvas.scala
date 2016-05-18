package patches.Draw

import patches.Node.{Node => N}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.OnUnmount
import patches.Node.Math.Sum

object Canvas {

  case class State(nodes: List[N])

  class Backend($: BackendScope[Unit, State]) extends OnUnmount {

    def addNode(e: ReactMouseEvent) =
      $.modState(s => s.copy(Sum() :: s.nodes))

    def render(s: State) = {
      <.div(
        <.div(
          s.nodes.map(n =>
            patches.Draw.Node(patches.Draw.Node.Props(n))()
          ).toJsArray
        ),
        <.button(
          ^.onMouseDown ==> addNode,
          "Add Node"
        )
      )
    }
  }

  val component = ReactComponentB[Unit]("Canvas")
    .initialState[State](State(List[N]()))
    .renderBackend[Backend]
    .build

  def apply() =
    component
}
