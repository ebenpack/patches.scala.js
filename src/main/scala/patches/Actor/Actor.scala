package patches.Actor

object Actor {
  type Receive = PartialFunction[Any, Unit]
}


abstract class Actor {
  def receive: Actor.Receive

  var behaviorStack = List[Actor.Receive]()

  def become(behavior: Actor.Receive): Unit =
    behaviorStack = behavior :: behaviorStack

  def unbecome(): Unit =
    if (behaviorStack.nonEmpty)
      behaviorStack = behaviorStack.tail
}


