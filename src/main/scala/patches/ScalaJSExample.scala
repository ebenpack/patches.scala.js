package patches

import patches.Actor.AkkaConfig.config
import akka.actor._

import scala.scalajs.js

object Patches extends js.JSApp {

  def main(): Unit = {
    val system = ActorSystem("akkajsapp", config)
    val bod = system.actorOf(
      Props(Canvas("patches")),
      "body"
    )
  }

}
