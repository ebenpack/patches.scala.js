package patches.Node.Math

import jsactor.JsProps
import patches.IO.Messages._
import patches.Node.Node
import patches.IO._

import scala.util.Random


case class Sum() extends Node{
  val name = "Sum"
  var x = 0
  var y = 0
  var in = List[Double](0, 0)
  val inputs = List(Input("A"), Input("B"))
  val outputs = List(Output("C"))

  def receive = {
    case n: DoubleMessage =>
      if (Random.nextFloat() < 0.5)
        context.system.eventStream.publish(
          AddNode(this)
        )
      else
        context.system.eventStream.publish(
          MoveNode(this, 1, 2)
        )
    case n: Message =>
      context.system.eventStream.publish(this.self.path)
    case m => println(m)
  }

  def value = s"∑(${inputs.map(a => a.value).mkString(", ")} = ${outputs.head})"

}

object Sum {
  def props(): JsProps = JsProps[Node](new Sum())
}