package org.crockeo.learninglibgdx.game.traits

import org.crockeo.learninglibgdx.game.World
import org.crockeo.learninglibgdx.InputState

trait Updateable {
  def update(w: World, is: InputState, dt: Float): Unit
}