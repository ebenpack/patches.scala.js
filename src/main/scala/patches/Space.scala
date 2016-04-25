package patches

import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.AudioContext

import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

class Space(width: Int,
            height: Int,
            canvas: html.Canvas,
            canvasCtx: dom.CanvasRenderingContext2D,
            audioCtx: AudioContext) {
  var nodes = List[Patch]()
  var edges = List[Connection]()

  def canConnect(a: Patch, b: Patch): Boolean =
    a.node.numberOfOutputs > 0 && b.node.numberOfInputs > 0

  def connect(a: Patch, aIndex: Int, b: Patch, bIndex: Int) = {
    if (canConnect(a, b)) {
      edges = new Connection(a, aIndex, b, bIndex, canvasCtx) :: edges
      a.connect(b)
    }
  }

  def disconnect(a: Patch, b: Patch) = {
    // TODO: Make check here?
    a.disconnect(b)
  }

  def removeNode(r: Patch) = {
    // TODO: Disconnect
    edges = edges.filter(a => a.start == r || a.end == r)
    nodes = nodes.filterNot((p) => p.eq(r))
  }

  def addNode(node: String, x: Int, y: Int) = {
    val add = node match {
      case "Oscillator" => new Oscillator(x, y, canvasCtx, audioCtx)
      case "Destination" => new Destination(x, y, canvasCtx, audioCtx)
      case "Gain" => new Gain(x, y, canvasCtx, audioCtx)
      case _ => new Destination(x, y, canvasCtx, audioCtx)
    }
    nodes = add :: nodes
  }

  def getPatch(x: Int, y: Int): Option[Patch] = {
    val n = nodes.filter(
      a => {
        x >= a.x &&
          x <= a.x + a.width &&
          y >= a.y &&
          y <= a.y + a.height
      }
    )
    n.headOption
  }

  def getConnect(x: Int, y: Int): Option[Patch] =
    nodes.find(n => n.nodeSites(x, y).isDefined)


  // async
  val rect = canvas.getBoundingClientRect()

  type ME = dom.MouseEvent
  val mousemove =
    new Channel[ME](canvas.addEventListener("mousemove", _))
  val mouseup =
    new Channel[ME](canvas.addEventListener("mouseup", _))
  val mousedown =
    new Channel[ME](canvas.addEventListener("mousedown", _))

  //  val mousemove2 =
  //    new Channel[ME](canvas.addEventListener("mousemove", _))

  async {
    while (true) {
      val start: ME = await(mousedown())
      val x = start.clientX - rect.left
      val y = start.clientY - rect.top
      val dragging = getPatch(x.toInt, y.toInt)
      val connecting = getConnect(x.toInt, y.toInt)
      if (dragging.isDefined) {
        // Move dragged patch to front
        nodes = dragging.get :: nodes.filterNot(p => p.eq(dragging.get))
        draw()
        val offsetX = x - dragging.get.x
        val offsetY = y - dragging.get.y
        var res: ME = await(mousemove | mouseup)
        while (res.`type` == "mousemove") {
          dragging.foreach(
            patch => {
              val newX = (res.clientX - rect.left).toInt
              val newY = (res.clientY - rect.top).toInt
              patch.x = (newX - offsetX).toInt
              patch.y = (newY - offsetY).toInt
              draw()
            }
          )
          res = await(mousemove | mouseup)
        }
      }
    }
  }

  private def d() = {
    canvasCtx.clearRect(0, 0, width, height)
    canvasCtx.beginPath()
    canvasCtx.fillStyle = "white"
    canvasCtx.strokeStyle = "black"

    for (n <- nodes.reverse) {
      n.draw()
    }
    canvasCtx.stroke()
    canvasCtx.fill()
    canvasCtx.closePath()
    for (e <- edges) {
      e.draw()
    }
    canvasCtx.stroke()
    canvasCtx.fill()
    canvasCtx.closePath()
  }

  def draw() = {
    dom.window.requestAnimationFrame((time: Double) => d())
  }
}
