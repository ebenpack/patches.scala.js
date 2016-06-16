package patches

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom
import scala.scalajs.js

import Actor._

object ScalaJSExample extends js.JSApp {
  val d = dom.document.getElementById("playground")
  val f = Actor.ActorSystem()

  case class Foo(a: String, b: ActorRef)

  case class Name(a: String, b: Int)
 
  class ff(val name: String) extends Actor.Actor {
    var goo = List[ActorRef]()

    val NOPE: PartialFunction[Any, Unit] = {
      case Name("foo", n) => println("NOOOO!", n)
      case "YEP" => {unbecome(); println("very unbecoming");println(behaviorStack)}
    }

    def receive = {
      case "ping" => println("pong")
      case Name("foo", n) => println("Bar", n)
      case "NOPE" => {become(NOPE);println("Becoming");println(behaviorStack)}

    }
  }

  val g1 = f.addActor(Props(new ff("one")))
  val g2 = f.addActor(Props(new ff("two")))
  g2.tell(Name("foo", 1))
  g2.tell(Name("foo", 2))
  g2.tell(Name("foo", 3))
  g2.tell("BLRHWWW")
  g2.tell("NOPE")
  g2.tell(Name("foo", 4))
  g2.tell(Name("foo", 5))
  g2.tell(Name("foo", 6))
  g2.tell("YEP")
  g2.tell(Name("foo", 7))
  g2.tell(Name("foo", 8))
  g2.tell(Name("foo", 9))

  def main(): Unit = {
    //    val sys = jsactor.JsActorSystem("sdadsa")
    //    val g = sys.actorOf(JsProps(new ff))

  }
}