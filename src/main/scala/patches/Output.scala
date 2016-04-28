package patches

import scala.reflect.ClassTag

class Output[T](name: String)(implicit val tag: ClassTag[T]) {
  private var listeners = Set[Input[T]]()

  def canConnect(i: Input[_]) = i.tag == tag

  def connect(i: Input[_]) = if (canConnect(i)) listeners = listeners + i.asInstanceOf[Input[T]]

  def disconnect(i: Input[_]) = if (canConnect(i)) listeners = listeners - i.asInstanceOf[Input[T]]

  def send(a: T) = for (l <- listeners) l.receive(a)
}