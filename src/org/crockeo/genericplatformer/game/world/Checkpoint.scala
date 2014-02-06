package org.crockeo.genericplatformer.game.world

import org.crockeo.genericplatformer.game.geom.Vector
import org.crockeo.genericplatformer.game.World
import org.crockeo.genericplatformer.{ InputState, Graphics }

class Checkpoint(sp: Vector) extends WorldObject(sp, new Vector(32, 64)) {
  var activated: Boolean = false
  
  // Renderable
  def render {
    if (activated) Graphics.color(1, 0, 0)
    else           Graphics.color(0, 1, 0)
    
    Graphics.rect(pos, size)
  }
  
  // Updateable
  def update(w: World, is: InputState, dt: Float) {
    
  }
}