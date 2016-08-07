package patches

import akka.actor.{ActorRef, Props}

package object Messages {

  case class Move(x: Double, y: Double)

  case object Reorder

  case class Reorder(n: Int)

  case class AddNode[T](n: Props)

}
