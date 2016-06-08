package patches.Draw

import japgolly.scalajs.react.vdom.prefix_<^._

/**
  * Created by ebenpack on 5/24/16.
  */
class InputOutput {

  case class In()
//  case clase Out

  def render() = {
    <.div(
      ^.className := "input",
      ^.left := "-3px",
      ^.top := increment * (index + 1) + "%",
      <.span(
        ^.className := "connect",
        ^.onMouseDown ==> startDragPatch,
        ^.marginRight := "5px",
        ^.width := "6px",
        ^.height := "6px"
      ),
      input.name
    )
  }
}
