package patches.Node.Generators

import patches.IO._
import patches.Node.Node

abstract class Generator(
                       name: String,
                       protected val default: Message = DoubleMessage(0)
                     ) extends Node(name) {
}