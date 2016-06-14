package patches.IO

import jsactor.JsActor

class Output(val name: String) extends JsActor {
  var value = ""

  override def receive: PartialFunction[Any, Unit] = {
    case m => context.parent ! m
  }
}

object Output {
  def apply(name: String): Output = new Output(name)
}