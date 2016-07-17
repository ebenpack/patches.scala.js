package patches.Actor

class ActorSystem {
  private var actors = Map[ActorRef, Actor]()

  private val noMatch: PartialFunction[Any, Unit] = {
    case _ =>
  }

  private[Actor] def dispatch(env: Envelope) =
    actors.get(env.recipient).foreach(a =>
      if (a.behaviorStack.nonEmpty) {
        a.behaviorStack.head.orElse(noMatch)(env.msg)
      }
      else {
        a.receive.orElse(noMatch)(env.msg)
      }
    )

  def addActor(a: Props) = {
    val newActor = a.newActor()
    val newActorRef = ActorRef(newActor, this)
    actors = actors + (newActorRef -> newActor)
    newActorRef
  }

  def removeActor(a: ActorRef) =
    actors = actors - a

}

object ActorSystem {
  def apply(): ActorSystem = new ActorSystem()
}