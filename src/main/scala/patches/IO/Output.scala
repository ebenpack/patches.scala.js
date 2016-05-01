package patches.IO

import patches.util.EventEmitter

abstract class Output(name: String) extends EventEmitter[Message] {
  def send(message: Message)
}

case class StringOutput(name: String) extends Output(name) {
  def send(message: Message) = message match {
    case m: StringMessage => this.emit(m.name, m)
    case _ => ;
  }
}

case class IntOutput(name: String) extends Output(name) {
  def send(message: Message) = message match {
    case m: IntMessage => this.emit(m.name, m)
    case _ => ;
  }
}

case class DoubleOutput(name: String) extends Output(name) {
  def send(message: Message) = message match {
    case m: DoubleMessage => this.emit(m.name, m)
    case m: IntMessage => this.emit(m.name, m)
    case _ => ;
  }
}

case class BooleanOutput(name: String) extends Output(name) {
  def send(message: Message) = message match {
    case m: BooleanMessage => this.emit(m.name, m)
    case _ => ;
  }
}