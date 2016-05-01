package patches.util

trait EventEmitter[T] {
  private var events = Map[String, Set[T => Unit]]()

  def on(topic: String, listener: T => Unit) =
    events = events updated(
      topic,
      events.getOrElse(
        topic,
        Set[T => Unit]()
      ) + listener
      )

  def unsubscribe(topic: String, listener: T => Unit) =
    events = events updated(
      topic,
      events.getOrElse(
        topic,
        Set[T => Unit]()
      ) filterNot (_ == listener)
      )

  def emit(topic: String, info: T) = {
    events get topic foreach (_ foreach (_ (info)))
  }
}
