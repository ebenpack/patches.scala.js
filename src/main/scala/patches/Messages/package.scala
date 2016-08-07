package patches

package object Messages {

  case class Move(x: Double, y: Double)

  case object Reorder

  case class Reorder(n: Int)

}
