package patches.IO

abstract class Message

case class DoubleMessage(name: String, value: Double) extends Message

case class IntMessage(name: String, value: Int) extends Message

case class StringMessage(name: String, value: String) extends Message

case class BooleanMessage(name: String, value: Boolean) extends Message