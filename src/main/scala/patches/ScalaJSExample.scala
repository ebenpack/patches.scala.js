package patches

import akka.actor._
import patches.Canvas.CanvasActor
import akkajs.Config.default
import com.typesafe.config
import scala.scalajs.js

object Patches extends js.JSApp {

  def main(): Unit = {
    val conf = config.ConfigFactory.parseString(
      """
    akka {
      loglevel = "DEBUG"
      stdout-loglevel = "DEBUG"
    }""").withFallback(default)

    val system = ActorSystem("akkajsapp", conf)
    val bod = system.actorOf(
      Props(CanvasActor("patches")),
      "body"
    )
  }

}
