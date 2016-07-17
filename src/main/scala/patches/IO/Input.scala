package patches.IO

import patches.Actor.Actor

class Input[T](default: T) extends Actor {
  var value = default
  def receive = {
    case _ => ;
  }
}

object Input {
  def apply[T](default: T): Input[T] = new Input(default)
}