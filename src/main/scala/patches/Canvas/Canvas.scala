package patches.Canvas

import jsactor.{JsActor, JsActorSystem, JsProps}
import jsactor.logging.impl.JsNullActorLoggerFactory
import patches.IO.Messages.{AddNode, MoveNode, RemoveNode, SystemMessage}
import patches.Patch.Patch
import patches.Node.Node

class Canvas {

  object Listener extends JsActor {
    def receive = {
      case AddNode(n) => addNode(n)
      case MoveNode(n, x, y) => moveNode(n, x, y)
      case RemoveNode(n) => removeNode(n)
    }
  }

  var nodes = List[Node]()
  var patches = List[Patch]()
  val sys = JsActorSystem("canvas", JsNullActorLoggerFactory)
  val listener = sys.actorOf(JsProps(Listener))
  sys.eventStream.subscribe(listener, classOf[SystemMessage])

  def addNode(n: Node) = {
    val f = sys.actorOf(JsProps(n))
    nodes = nodes :+ n
  }


  def moveNode(n: Node, x: Int, y: Int) =
    nodes.find(_ == n).foreach(a => {
      a.x = x
      a.y = y
    })

  def reorderNode(n: Node) =
    nodes = nodes.span(_ != n) match {
      case (as, h :: bs) => as ++ bs :+ h
      case _ => nodes
    }

//  def updateNodeValue(n: Node, str: String) =
//  nodes.find(_ == n).foreach(_.value = str)

  def removeNode(n: Node) =
    nodes = nodes.filterNot(_ == n)

  def addPatch(p: Patch) =
    patches = patches :+ p

  def removePatch(p: Patch) =
    patches = patches.filterNot(_ == p)
}
