package patches.IO

import monifu.reactive.OverflowStrategy
import monifu.reactive.channels.{BehaviorChannel, SubjectChannel}
import monifu.concurrent.Implicits.globalScheduler
import monifu.reactive.subjects.PublishSubject

import scala.reflect.ClassTag

class Input[T](name: String, initial: T)(implicit val tag: ClassTag[T]) {

  private val input = BehaviorChannel[T](initial, OverflowStrategy.DropOld(100))
  val in = input.behavior(initial)
  in.connect()

  def canConnect(o: Output[_]) = o.tag == tag

  def update(value: T) =
    input.pushNext(value)
}

object Input {
  def apply[T: ClassTag](name: String, initial: T): Input[T] =
    new Input[T](name, initial)
}