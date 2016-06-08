package patches.IO

import monix.execution.Scheduler.Implicits.global
import monix.reactive.OverflowStrategy.DropOld
import monix.reactive.subjects.ConcurrentSubject

import scala.reflect.ClassTag

class Input[T](val name: String, initial: T)(implicit val tag: ClassTag[T]) extends IO[T] {

  val in = ConcurrentSubject.behavior(initial, DropOld(100))

  def canConnect(o: Output[_]) = o.tag == tag

  def update(value: T) = in.onNext(value)
  def destroy = in.onComplete()

}

object Input {
  def apply[T: ClassTag](name: String, initial: T): Input[T] =
    new Input[T](name, initial)
}