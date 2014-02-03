package org.crockeo.genericplatformer.game.traits

import org.crockeo.genericplatformer.game.World
import org.crockeo.genericplatformer.InputState

trait Updateable {
  def update(w: World, is: InputState, dt: Float): Unit
}