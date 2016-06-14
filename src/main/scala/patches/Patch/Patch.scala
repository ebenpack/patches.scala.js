package patches.Patch

import jsactor.JsActor

case class Patch(name: String) extends JsActor {
  var x1 = 0
  var y1 = 0
  var x2 = 0
  var y2 = 0

  def receive = {
    case _ => ;
  }
}

object Patch {
  def apply(): Patch = new Patch("bleh")
}