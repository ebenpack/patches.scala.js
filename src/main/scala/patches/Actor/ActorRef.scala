package patches.Actor

import patches.Actor.Actor.Receive

class ActorRef(private val actor: Actor, sys: ActorSystem) {

  def tell(msg: Any)(implicit sender: ActorRef = null) = {
    sys.dispatch(sender, this, msg)
  }

  def !(msg: Any)(implicit sender: ActorRef = null) = tell(msg)(sender)

  def become(behavior: Receive) = actor.become(behavior)

  def unbecome() = actor.unbecome()
}

object ActorRef {
  def apply(
             actor: Actor,
             sys: ActorSystem
           ): ActorRef = new ActorRef(actor, sys)
}