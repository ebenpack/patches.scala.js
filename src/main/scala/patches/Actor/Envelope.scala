package patches.Actor

case class Envelope(sender: ActorRef, recipient: ActorRef, msg: Any)