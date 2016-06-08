package patches.Node.Generators

import monix.reactive.subjects.BehaviorSubject
import monix.execution.Scheduler.Implicits.global
import patches.IO._
import org.scalajs.dom

class Interval(var period: Int = 1000) extends Generator("Interval") {

  val leftInput = Input[Message]("A", DoubleMessage(period))
  val resultOutput = Output[Message]("B", default)

  protected val in = BehaviorSubject(default)
  private val cancelIn = leftInput.in.subscribe(in)

  protected val out = BehaviorSubject(default)
  private val cancelOut = resultOutput.out.subscribe(out)

  def destroy = {
    cancelIn.cancel()
    cancelOut.cancel()
    leftInput.destroy
    resultOutput.destroy
  }

  in.collect({
    case i: DoubleMessage => i
  }).foreach(i=>
    period=i.value.toInt
  )
  private var count = 0
  private def tick(): Unit = {
    resultOutput.update(DoubleMessage(count))
    count += 1
    dom.window.setTimeout(tick _, period)
  }
  tick

  val inputs = List[Input[Message]](
    leftInput
  )
  val outputs = List[Output[Message]](
    resultOutput
  )
  val value = out.collect({
    case m: DoubleMessage => m
  }).map(_.value.toString)

}

object Interval {
  def apply(): Interval = new Interval()
}
