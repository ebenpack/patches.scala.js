package patches.Actor

import patches.Actor.Actor.Receive

import scala.collection.immutable.Queue
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ActorRef(private val actor: Actor, sys: ActorSystem) {

  private var q = Queue[Envelope]()

  def processMsg(): Unit =
    if (q.nonEmpty) {
      val (env, newQ) = q.dequeue
      sys.dispatch(env)
      q = newQ
      Future(processMsg())
    }

  def tell(msg: Any)(implicit sender: ActorRef = null) = {
    q = q.enqueue(Envelope(sender, this, msg))
    Future(processMsg())
  }

  def !(msg: Any)(implicit sender: ActorRef = null) =
    tell(msg)(sender)

  def forward(recipient: ActorRef) = {
    this.become({
      case m: Any => recipient ! m
    })
  }

  def become(behavior: Receive) = actor.become(behavior)

  def unbecome() = actor.unbecome()
}

object ActorRef {
  def apply(
             actor: Actor,
             sys: ActorSystem
           ): ActorRef = new ActorRef(actor, sys)
}