package patches.Node

import monifu.concurrent.Implicits.globalScheduler
import monifu.concurrent.cancelables.BooleanCancelable
import monifu.reactive.Ack
import monifu.reactive.Ack.Continue
import patches.IO.{Input, Message, Output}

import scala.concurrent.Future

abstract class Node(val name: String) {
  val inputs: List[Input[Message]]
  val outputs: List[Output[Message]]
  private var disconnects = Map[(Input[_], Output[_]), BooleanCancelable]()

  def value: String

  def connect(i: Input[Message], o: Output[Message]) = (i, o) match {
    case (in, out) if in.canConnect(out) && !disconnects.contains((in, out)) => {
      val cancel = out.out.subscribe(f => {
        in.update(f)
        Continue
      })
      disconnects = disconnects +
        ((in, out) -> cancel)
    }
    case _ => ;
  }

  def disconnect(i: Input[Message], o: Output[Message]) = {
    disconnects.get((i, o)).map(_.cancel())
    disconnects = disconnects - ((i, o))
  }

  def disconnectAll() = {
    for ((_, c) <- disconnects)
      c.cancel()
    disconnects = disconnects.empty
  }
}