package patches.Draw

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.EventListener
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.extra.OnUnmount
import org.scalajs.dom
import dom.MouseEvent
import patches.Node.Node


object Node {

  case class Props(
                    node: NodeModel
                  )

  class Backend($: BackendScope[Props, Unit]) extends OnUnmount {

    def render(p: Props) = {
      val node = p.node
      <.div(
        ^.className := "node draggable",
        ^.left := node.x + "px",
        ^.top := node.y + "px",
        <.div(
          ^.className := "handle title",
          <.div(
            ^.position := "absolute",
            ^.right := "0",
            "✕"
          ),
          node.name
        ),
        <.div(
          ^.className := "body"
//          node.inputs.zipWithIndex.map {
//            case (input, index) =>
//              val increment = 100.0 / (node.inputs.length + 1)
//              patches.Draw.InputOutput(
//                Left(input),
//                input.name,
//                increment,
//                index
//              )
//          },
//          node.outputs.zipWithIndex.map {
//            case (output, index) =>
//              val increment = 100.0 / (node.outputs.length + 1)
//              patches.Draw.InputOutput(
//                dispatch,
//                Right(output),
//                output.name,
//                increment,
//                index,
//                uuid
//              )
//          }
        )
      )
    }
  }

  val node = ReactComponentB[Props]("Node")
    .renderBackend[Backend]
    .build

  def apply(n: NodeModel) =
    node(Props(n))
}