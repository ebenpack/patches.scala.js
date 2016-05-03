package patches.IO

abstract class Message

case class DoubleMessage(value: Double) extends Message

case class IntMessage(value: Int) extends Message {
  def toDoubleMessage = DoubleMessage(value)
}

case class StringMessage(value: String) extends Message

case class BooleanMessage(value: Boolean) extends Message