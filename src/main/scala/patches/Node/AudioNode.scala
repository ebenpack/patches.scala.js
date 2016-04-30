package patches.Node

import org.scalajs.dom.{AudioNode => JSAudioNode, AudioContext}

abstract class AudioNode(val ctx: AudioContext) extends Node {
  val node: JSAudioNode

  private val connect = Input[JSAudioNode]("connect")
  connect.addListener(a => node.connect(a))

  private val disconnect = Input[JSAudioNode]("disconnect")
  disconnect.addListener(a => node.disconnect(a))

  val inputs = List[Input[_]](connect, disconnect)
  val outputs = List[Output[_]]()

  def getInputs = inputs

  def getOutputs = outputs
}