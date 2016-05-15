package patches.Draw

import patches.Node.{Node => N}
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.OnUnmount

object Canvas {

  case class Props(nodes: List[N])

  class Backend($: BackendScope[Props, Unit]) extends OnUnmount {

    def render(props: Props) = {
      <.div(
        props.nodes.map(n=>
          patches.Draw.Node(patches.Draw.Node.Props(n))()
        ).toJsArray
      )
    }
  }

  val component = ReactComponentB[Props]("DrawNode")
    .renderBackend[Backend]
    .build

  def apply(P: Props) =
    component.withProps(P)
}
