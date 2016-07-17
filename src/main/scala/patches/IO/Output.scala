package patches.IO

import patches.Actor.Actor

class Output[T](default: T) extends Actor {
  var value = default
  def receive = {
    case _ => ;
  }
}

object Output {
  def apply[T](default: T): Output[T] = new Output(default)
}