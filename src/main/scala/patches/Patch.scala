package patches


import org.scalajs.dom
import org.scalajs.dom.raw.{AudioContext, AudioNode}

//import patches.pubsub.PubSub

abstract class Patch(val label: String) {
  var width = 50
  var height = 30
  var x: Int
  var y: Int
  val node: AudioNode
  val canvasCtx: dom.CanvasRenderingContext2D
  var highlighted = false
  val nodeRadius = 4

  lazy val inputs = {
    val inputSpace = width / (node.numberOfInputs + 1)
    1.to(node.numberOfInputs)
      .map(n => () => new Point(x + (inputSpace * n), y)).toVector
  }

  lazy val outputs = {
    val outputSpace = width / (node.numberOfOutputs + 1)
    1.to(node.numberOfOutputs)
      .map(m => () => new Point(x + (outputSpace * m), y + height)).toVector
  }

  lazy val nodeSites = inputs ++ outputs

  if (label.nonEmpty) width = canvasCtx.measureText(label).width.toInt + 10

  def topCenter() =
    new Point(x + (width / 2), y)

  def bottomCenter() =
    new Point(x + (width / 2), y + height)

  def nodeSites(x: Int, y: Int): Option[() => Point] =
    nodeSites.find(point => {
      val p = point()
      Math.pow(x - p.x, 2) +
        Math.pow(y - p.y, 2) <=
        Math.pow(nodeRadius, 2)
    })


  def connect(other: Patch) =
    node.connect(other.node)


  def disconnect(other: Patch) =
    node.disconnect(other.node)


  def draw() = {
    // Draw box
    canvasCtx.beginPath()
    canvasCtx.fillStyle = "white"
    if (highlighted)
      canvasCtx.strokeStyle = "green"
    else
      canvasCtx.strokeStyle = "gray"
    canvasCtx.fillRect(x, y, width, height)
    canvasCtx.strokeRect(x, y, width, height)
    canvasCtx.textAlign = "center"
    canvasCtx.textBaseline = "middle"
    canvasCtx.fillStyle = "black"
    // Draw label
    if (label.nonEmpty) canvasCtx.fillText(
      label,
      x + width / 2,
      y + height / 2
    )
  }

}

class Oscillator(var x: Int,
                 var y: Int,
                 val canvasCtx: dom.CanvasRenderingContext2D,
                 val ctx: AudioContext) extends Patch("Oscillator") {
  val node = ctx.createOscillator()

  def start() = node.start()

  def stop() = node.stop()
}

class Destination(var x: Int,
                  var y: Int,
                  val canvasCtx: dom.CanvasRenderingContext2D,
                  val ctx: AudioContext) extends Patch("Destination") {
  val node = ctx.destination
}

class Gain(var x: Int,
           var y: Int,
           val canvasCtx: dom.CanvasRenderingContext2D,
           val ctx: AudioContext) extends Patch("Gain") {
  val node = ctx.createGain()
}