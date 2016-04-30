package patches.Node

import org.scalajs.dom

class TestNode(
                var x: Int,
                var y: Int,
                var width: Int,
                var height: Int
              ) extends Node {
  var intValue = 0
  var strValue = "str"
  // inputs
  val intInput = new Input[Int]("one")
  val strInput = new Input[String]("two")
  // outputs
  val intOutput = new Output[Int]("one")
  val strOutput = new Output[String]("two")

  val inputs = List(intInput, strInput)
  val outputs = List(intOutput, strOutput)

  def connect(o: Output[_], i: Input[_]) = {
    o.connect(i)
  }

  intInput.addListener(a => {
    intValue = a
    dom.setTimeout(() => intOutput.send(a + 1), 1000)
  })
  strInput.addListener(a => {
    strValue = a
    dom.setTimeout(() => strOutput.send((a(0) + 1).toChar.toString), 1000)
  })
}