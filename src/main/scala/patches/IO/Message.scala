package patches.IO

sealed abstract class Message

case class TypedMessage[T](value: T) extends Message

case class DoubleMessage(value: Double) extends Message

case class IntMessage(value: Int) extends Message {
  def toDoubleMessage = DoubleMessage(value)
}

case class StringMessage(value: String) extends Message

case class BooleanMessage(value: Boolean) extends Message

case class BigIntMessage(value: BigInt) extends Message

case class BigDecimalMessage(value: BigDecimal) extends Message
