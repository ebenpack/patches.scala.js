package patches

import scala.reflect.ClassTag


class Input[A](name: String)(implicit val tag: ClassTag[A]) {

}


//Node(type, patch, def, render, callback)
//  turnOn
//  turnOff
//  addInlet
//  addOutlet
//  removeInlet
//  removeOutlet
//  move
//
//Inlet(type, node, alias, def, render)
//  receive
//  stream
//  toDefault
//  allows
//
//Outlet(type, node, alias, def, render)
//  connect
//  disconnect
//  send
//  stream
//  toDefault
//
//Link(outlet, inlet, label)
//  pass
//  enable
//  disable
//  disconnect