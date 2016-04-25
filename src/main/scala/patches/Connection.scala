package patches

import org.scalajs.dom

class Connection(val start: Patch,
                 val startIndex: Int,
                 val end: Patch,
                 val endIndex: Int,
                 canvasCtx: dom.CanvasRenderingContext2D) {
  val nodeRadius = 4

  private def drawNode(p: Point, index: Int) = {
    canvasCtx.fillStyle = "white"
    canvasCtx.moveTo(p.x + nodeRadius, p.y)
    canvasCtx.arc(
      p.x,
      p.y,
      nodeRadius,
      0,
      2 * Math.PI
    )
  }

  def draw() = {
    // Draw connection
    val startPoint = start.outputs(startIndex)()
    val endPoint = end.inputs(endIndex)()
    canvasCtx.strokeStyle = "black"
    canvasCtx.moveTo(endPoint.x, endPoint.y)
    canvasCtx.lineTo(startPoint.x, startPoint.y)
    // Draw input/output nodes
    drawNode(startPoint, startIndex)
    drawNode(endPoint, endIndex)
  }
}
