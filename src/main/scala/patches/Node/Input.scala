package patches.Node

import scala.reflect.ClassTag

class Input[T](name: String)(implicit val tag: ClassTag[T]) {
  private var listeners = Set[T => Unit]()

  def receive(a: T) = for (l <- listeners) l(a)

  def addListener(a: T => Unit) = listeners = listeners + a

  def removeListener(a: T => Unit) = listeners = listeners - a
}

object Input {
  def apply[T: ClassTag](name: String): Input[T] = {
    new Input[T](name)
  }
}