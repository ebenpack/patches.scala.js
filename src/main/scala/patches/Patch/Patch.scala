package patches.Patch

import monix.execution.Cancelable
import monix.execution.Scheduler.Implicits.global
import patches.IO.{Input, Message, Output}
import patches.Node.Node

class Patch {
  var cancel: Option[Cancelable] = None
  def connect(i: Input[Message], o: Output[Message]) = (i, o) match {
    case (in, out) if in.canConnect(out) =>
      cancel = Some(out.out.subscribe(in.in))
    case _ => ;
  }

  def disconnect(i: Input[Message], o: Output[Message]) =
    cancel.foreach(_.cancel())
}

object Patch {
  def apply(): Patch = new Patch()
}
