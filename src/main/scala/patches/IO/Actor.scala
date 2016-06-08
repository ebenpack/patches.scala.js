package patches.IO

trait Actor {
  def send(message: Any) =
    receive(message)
  def receive: PartialFunction[Any, Unit]
}
