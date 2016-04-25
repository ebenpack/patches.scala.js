

package patches

import reflect.ClassTag
import scala.reflect._
import org.scalajs.dom
import org.scalajs.dom.{MouseEvent, html}
import org.scalajs.dom.html.{Button, Div, Pre}
import org.scalajs.dom.raw.Event

import scala.scalajs.js

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    class Box[T: ClassTag](
                            val x: Int,
                            val y: Int,
                            val width: Int,
                            val height: Int,
                            var message: T,
                            val name: String
                          )(implicit val tag: ClassTag[T]) {
      val html = dom.document.createElement("div").asInstanceOf[Div]
      html.style.width = width + "px"
      html.style.height = height + "px"
      html.style.position = "absolute"
      html.style.top = y + "px"
      html.style.left = x + "px"
      html.style.border = "1px solid black"
      var connected = List[Box[T]]()

      def render() = {
        html.textContent = message.toString + " - " +
          connected.foldLeft("")((p, c) => {
            c.name + p
          })
      }

      def update(msg: T) = {
        message = msg
        render()
      }

      def connect[O](other: Box[O]) = {
        if (tag.runtimeClass == other.tag.runtimeClass) {
          connected = other.asInstanceOf[Box[T]] :: connected
          println("CONNECTED")
        } else {
          println("NO CONNECTO!")
        }
        render()
      }

      def receive(msg: T) = {
        update(msg)
      }

      def send(msg: T) = {
        connected.foreach(b => b.receive(msg))
      }

      render()
    }

    val container = dom.document.getElementById("playground").asInstanceOf[Div]
    container.style.position = "relative"
    val output = dom.document.createElement("pre").asInstanceOf[Pre]
    var str = List[String]("a")
    var int = List[Int](1)
    var dub = List[Double](1.5)

    def update[T](v: T)(implicit tag: ClassTag[T]) = {
      tag.runtimeClass match {
        case t if t == classOf[String] => str = v.asInstanceOf[String] :: str
        case t if t == classOf[Int] => int = v.asInstanceOf[Int] :: int
        case t if t == classOf[Double] => dub = v.asInstanceOf[Double] :: dub
      }
      render()
    }

    def render() = {
      output.textContent = {
        str.toString() + "\n" +
          int.toString() + "\n" +
          dub.toString()
      }
    }

    val boxes = List(
      new Box[Int](50, 50, 100, 100, 69, "ONE"),
      new Box[Int](150, 50, 100, 100, 1729, "TWO"),
      new Box[Int](250, 50, 100, 100, 1, "THREE"),
      new Box[Int](350, 50, 100, 100, 2, "FOUR"),
      new Box[String](50, 150, 100, 100, "BLAH!", "FIVE"),
      new Box[String](150, 150, 100, 100, "BLOOP!", "SIX")
    )
    val i1 = dom.document.createElement("input").asInstanceOf[dom.html.Input]
    val i2 = dom.document.createElement("input").asInstanceOf[dom.html.Input]
    val b3 = dom.document.createElement("button").asInstanceOf[Button]
    b3.textContent = "CONNECT"
    b3.onclick = (e: MouseEvent) => {
      val inp1 = i1.value.toInt
      val inp2 = i2.value.toInt
      println(inp1, inp2)
      if (inp1 < boxes.length && inp2 < boxes.length) {
        boxes(inp1).connect(boxes(inp2))
      }

    }
    container.appendChild(i1)
    container.appendChild(i2)
    container.appendChild(b3)
    boxes.foreach(b => container.appendChild(b.html))
  }

}