package patches.IO

import patches.util.EventEmitter

abstract class Input(name: String) extends EventEmitter[Message] {
  def receive(message: Message): Unit

  def canConnect(o: Output): Boolean
}

case class StringInput(name: String) extends Input(name) {
  def canConnect(out: Output) = out match {
    case o: StringOutput => true
    case _ => false
  }

  def receive(message: Message) = message match {
    case m: StringMessage => this.emit(m.name, m)
    case _ => ;
  }
}

case class IntInput(name: String) extends Input(name) {
  def canConnect(out: Output) = out match {
    case o: IntOutput => true
    case _ => false
  }

  def receive(message: Message) = message match {
    case m: IntMessage => this.emit(m.name, m)
    case _ => ;
  }
}

case class DoubleInput(name: String) extends Input(name) {
  def canConnect(out: Output) = out match {
    case o: DoubleOutput => true
    case o: IntOutput => true
    case _ => false
  }

  def receive(message: Message) = message match {
    case m: DoubleMessage => this.emit(m.name, m)
    case m: IntMessage => this.emit(m.name, m)
    case _ => ;
  }
}

case class BooleanInput(name: String) extends Input(name) {
  def canConnect(out: Output) = out match {
    case o: BooleanOutput => true
    case _ => false
  }

  def receive(message: Message) = message match {
    case m: BooleanMessage => this.emit(m.name, m)
    case _ => ;
  }
}