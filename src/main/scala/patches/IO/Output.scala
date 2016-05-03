package patches.IO

import monifu.concurrent.Implicits.globalScheduler
import monifu.reactive.OverflowStrategy
import monifu.reactive.channels.{BehaviorChannel, PublishChannel}

import scala.reflect.ClassTag

class Output[T](name: String, initial: T)(implicit val tag: ClassTag[T]) {
  private val output = BehaviorChannel[T](initial, OverflowStrategy.DropOld(100))
  val out = output.publish
  out.connect()

  def update(value: T) = output.pushNext(value)
}

object Output {
  def apply[T: ClassTag](name: String, initial: T): Output[T] =
    new Output[T](name, initial)
}