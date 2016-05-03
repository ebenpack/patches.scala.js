package patches.Node

import monifu.concurrent.cancelables.BooleanCancelable
import patches.IO.{Input, Message, Output}

abstract class Node(val name: String) {
  val inputs: List[Input[Message]]
  val outputs: List[Output[Message]]
  //private var disconnects = Map[(Input[Message], Output[Message]), BooleanCancelable]()

  def value: String

//  def connect(i: Input[Message], o: Output[Message], topic: String) = (i, o) match {
//    case (in, out) if in.canConnect(out) => disconnects = disconnects +
//      ((in, out) -> out.out.connect())
//    case _ => ;
//  }
//
//  def disconnect(i: Input[Message], o: Output[Message], topic: String) = {
//    disconnects.get((i, o)).map(_.cancel())
//    disconnects = disconnects - ((i, o))
//  }
//
//  def disconnectAll() = {
//    for ((io, c) <- disconnects) c.cancel()
//    disconnects = Map[(Input[Message], Output[Message]), BooleanCancelable]()
//  }
}