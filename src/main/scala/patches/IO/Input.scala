package patches.IO

import jsactor.JsActor

class Input(val name: String) extends JsActor {

  var value = ""

  override def receive: PartialFunction[Any, Unit] = {
    case m => context.parent ! m
  }
}

object Input {
  def apply(name: String): Input = new Input(name)
}