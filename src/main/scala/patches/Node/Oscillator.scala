package patches.Node

import org.scalajs.dom.{
AudioContext,
AudioNode => JSAudioNode,
PeriodicWave
}

class Oscillator(ctx: AudioContext) extends AudioNode(ctx) {
  val node = ctx.createOscillator()

  private val start = Input[Boolean]("start")
  start.addListener(a => node.start())

  private val stop = Input[Boolean]("stop")
  stop.addListener(a => node.stop())

  private val setPeriodicWave = Input[PeriodicWave]("setPeriodicWave")
  setPeriodicWave.addListener(a => node.setPeriodicWave(a))

  override val inputs = super.getInputs ++ List[Input[_]]()
  override val outputs = super.getOutputs ++ List[Output[_]]()

}

object Oscillator {
  def apply(ctx: AudioContext): Oscillator = new Oscillator(ctx)
}