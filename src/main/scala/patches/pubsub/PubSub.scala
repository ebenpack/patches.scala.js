package patches.pubsub

trait PubSub[A, B] {
  private var topics = Map[String, List[A => B]]()

  def subscribe(topic: String, listener: A => B) =
    topics = topics updated(
      topic,
      listener ::
        topics.getOrElse(
          topic,
          List[A => B]()
        )
      )

  def unsubscribe(topic: String, listener: A => B) =
    topics = topics updated(
      topic,
        topics.getOrElse(
          topic,
          List[A => B]()
        ) filterNot(_ == listener)
      )

  def publish(topic: String, info: A) = {
    topics get topic foreach(_ foreach(_(info)))
  }
}