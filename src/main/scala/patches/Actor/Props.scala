package patches.Actor


class Props(creator: => Actor) {
  def newActor(): Actor = creator
}
object Props {
  def apply[T <: Actor](creator: => T): Props = new Props(creator)
}
