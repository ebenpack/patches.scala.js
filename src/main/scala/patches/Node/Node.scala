package patches.Node

abstract class Node() {
  val inputs: List[Input[_]]
  val outputs: List[Output[_]]
}