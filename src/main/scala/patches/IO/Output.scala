package patches.IO

import monix.execution.Scheduler.Implicits.global
import monix.reactive.OverflowStrategy.DropOld
import monix.reactive.subjects.ConcurrentSubject

import scala.reflect.ClassTag

class Output[T](name: String, initial: T)(implicit val tag: ClassTag[T]) {

  private val output = ConcurrentSubject.behavior(initial, DropOld(100))
  val out = output.behavior(initial)
  out.connect()
  def update(value: T) = output.onNext(value)
}

object Output {
  def apply[T: ClassTag](name: String, initial: T): Output[T] =
    new Output[T](name, initial)
}