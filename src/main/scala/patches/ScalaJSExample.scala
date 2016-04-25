package patches

import monifu.reactive.OverflowStrategy.{DropOld, DropNew}
import monifu.reactive.channels.PublishChannel
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.AudioContext


import monifu.concurrent.Implicits.globalScheduler
import monifu.reactive._

//import scala.concurrent.duration.FiniteDuration

import scala.reflect.runtime.universe._

//import org.scalajs.dom
//import org.scalajs.dom.html

import scala.scalajs.js

object ScalaJSExample extends js.JSApp {
  def main(): Unit = {
    val audioCtx = new AudioContext()
    val container = dom.document.getElementById("playground")
      .asInstanceOf[html.Div]
    val canvas = dom.document.createElement("canvas")
      .asInstanceOf[html.Canvas]
    val canvasCtx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]
    canvasCtx.lineWidth
    canvas.height = 400
    canvas.width = 800
    canvas.style.border = "1px solid black"
    container.appendChild(canvas)
    canvasCtx.font = "20px serif"

    val space = new Space(800, 400, canvas, canvasCtx, audioCtx)
    space.addNode("Oscillator", 100, 20)
    space.addNode("Gain", 100, 100)
    space.addNode("Destination", 100, 200)
    space.connect(space.nodes.tail.head, 0, space.nodes.head, 0)
    space.connect(space.nodes.tail.tail.head, 0, space.nodes.tail.head, 0)
    space.draw()
  }
}