package patches.IO

import monix.execution.Scheduler.Implicits.global
import monix.reactive.OverflowStrategy.DropOld
import monix.reactive.subjects.ConcurrentSubject

import scala.reflect.ClassTag

class Output[T](name: String, initial: T)(implicit val tag: ClassTag[T]) {

  val out = ConcurrentSubject.behavior(initial, DropOld(100))
  def update(value: T) = out.onNext(value)
}

object Output {
  def apply[T: ClassTag](name: String, initial: T): Output[T] =
    new Output[T](name, initial)
}