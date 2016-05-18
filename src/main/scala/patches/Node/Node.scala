package patches.Node

import monix.execution.Scheduler.Implicits.global
import monix.execution.Cancelable
import monix.reactive.Observable
import patches.IO.{Input, Message, Output}

abstract class Node(val name: String) {
  val inputs: List[Input[Message]]
  val outputs: List[Output[Message]]
  private var disconnects = Map[(Input[_], Output[_]), Cancelable]()

  def value: Observable[String]

  def connect(i: Input[Message], o: Output[Message]) = (i, o) match {
    case (in, out) if in.canConnect(out) && !disconnects.contains((in, out)) => {
      val cancel = out.out.subscribe(in.in)
      disconnects = disconnects +
        ((in, out) -> cancel)
    }
    case _ => ;
  }

  def disconnect(i: Input[Message], o: Output[Message]) = {
    disconnects.get((i, o)).foreach(_.cancel())
    disconnects = disconnects - ((i, o))
  }

  def disconnectAll() = {
    for ((_, c) <- disconnects)
      c.cancel()
    disconnects = disconnects.empty
  }
}