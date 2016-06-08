package patches.IO

import monix.execution.Scheduler.Implicits.global
import monix.reactive.OverflowStrategy.DropOld
import monix.reactive.subjects.ConcurrentSubject

import scala.reflect.ClassTag

class Output[T](val name: String, initial: T)(implicit val tag: ClassTag[T]) extends IO[T] {

  val out = ConcurrentSubject.behavior(initial, DropOld(100))
  def update(value: T) = out.onNext(value)
  def destroy = out.onComplete()
}

object Output {
  def apply[T: ClassTag](name: String, initial: T): Output[T] =
    new Output[T](name, initial)
}