package patches.Draw

import monix.reactive.MulticastStrategy
import monix.reactive.OverflowStrategy.DropOld
import monix.reactive.subjects.ConcurrentSubject
import monix.execution.Scheduler.Implicits.global

trait Draggable {
  def getXY(): (Double, Double)

  def onMove(x: Double, y: Double): Unit

  class Position(val x: Double, val y: Double)

  class Mouse(
               val clientX: Double,
               val clientY: Double,
               val eventType: String
             )



  val mouseDownChannel = ConcurrentSubject[Mouse](MulticastStrategy.publish, DropOld(100))
  val mouseMoveChannel = ConcurrentSubject[Mouse](MulticastStrategy.publish, DropOld(100))

  mouseDownChannel
    .flatMap(downEvent => {
      val (startPosX, startPosY) = getXY()
      val startMouseX = downEvent.clientX
      val startMouseY = downEvent.clientY
      mouseMoveChannel
        .takeWhile(
          _.eventType == "mousemove"
        )
        .map(m => {
          val deltaX = m.clientX - startMouseX
          val deltaY = m.clientY - startMouseY
          new Mouse(startPosX + deltaX, startPosY + deltaY, m.eventType)
        })
    })
    .foreach(m => onMove(m.clientX, m.clientY))
}