package patches.Node


abstract class Message[T](name: String) {
  val value: T
}
case class AudioMessage(name: String, value: AudioNode) extends Message[AudioNode](name)
case class DoubleMessage(name: String, value: Double) extends Message[Double](name)
case class IntMessage(name: String, value: Int) extends Message[Int](name)
case class StringMessage(name: String, value: String) extends Message[String](name)
case class BooleanMessage(name: String, value: Boolean) extends Message[Boolean](name)