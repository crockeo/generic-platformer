package org.crockeo.learninglibgdx.game.world

import org.crockeo.learninglibgdx.game.geom.Vector
import org.crockeo.learninglibgdx.game.World
import org.crockeo.learninglibgdx.{ InputState, Graphics }

class Block(pos: Vector, size: Vector) extends WorldObject(pos, size) {
  // Renderable
  def render { Graphics.rect(pos, size) }
  
  // Updateable
  def update(w: World, is: InputState, dt: Float) { }
}