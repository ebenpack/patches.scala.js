package patches.Draw

import java.util.UUID

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import patches.IO.{Input, Message, Output}

object InputOutput {

  case class Props(
                    dispatch: AnyRef => Callback,
                    io: Either[Input[Message], Output[Message]],
                    name: String,
                    increment: Double,
                    index: Int
                  )

  class Backend($: BackendScope[Props, Unit]) {

    def startDragPatch(e: ReactMouseEvent) = {
      val p = $.props.runNow()
      p.dispatch(AddTempPatch(p.io, e.clientX.toInt, e.clientY.toInt))
    }

    def stopDragPatch(e: ReactMouseEvent) = {
      e.stopPropagation()
      val p = $.props.runNow()
      p.dispatch(AddTempPatchFOOBAR(p.io))
    }

    def render(props: Props) = {
      val (className, first, second) =
        if (props.io.isLeft) ("input", "", props.name)
        else ("output", props.name, "")
      <.div(
        ^.className := className,
        ^.onMouseDown ==> startDragPatch,
        ^.onMouseUp ==> stopDragPatch,
        ^.top := props.increment * (props.index + 1) + "%",
        first,
        <.span(
          <.span(
            ^.className := "connect",
            ^.cursor := "default",
            ^.width := "8px",
            ^.height := "8px"
          )
        ),
        second
      )
    }
  }

  val inputOutput = ReactComponentB[Props]("Node")
    .renderBackend[Backend]
    .build

  def apply(
             dispatch: AnyRef => Callback,
             io: Either[Input[Message], Output[Message]],
             name: String,
             increment: Double,
             index: Int,
             uuid: UUID
           ) =
    inputOutput
      .withKey(uuid.toString + "." + name + "." + index)(
        Props(dispatch, io, name, increment, index)
      )
}
