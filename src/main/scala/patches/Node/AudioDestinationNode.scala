package patches.Node

import org.scalajs.dom.AudioContext

class AudioDestinationNode(ctx: AudioContext) extends AudioNode(ctx) {
  val node = ctx.destination

  val destination = Input[AudioNode]("destination")

  override val inputs = super.getInputs ++ List[Input[_]](destination)
  override val outputs = super.getOutputs ++ List[Output[_]]()
}

object AudioDestinationNode {
  def apply(ctx: AudioContext): AudioDestinationNode = new AudioDestinationNode(ctx)
}
