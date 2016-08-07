package patches

import patches.Actor.DomActor

import scalatags.JsDom.all._


class Controls extends DomActor {
  def template = div(
    `class` := "controls"
  )
}

object Controls {
  def apply(): Controls = new Controls()
}
