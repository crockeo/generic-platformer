package org.crockeo.genericplatformer.game.world

import org.crockeo.genericplatformer.geom.Vector
import org.crockeo.genericplatformer.game.World
import org.crockeo.genericplatformer.Graphics

class Block(sp: Vector, ss: Vector) extends WorldObject(sp, ss) {
  // Renderable
  def render {
    Graphics.color(1, 1, 1)
    Graphics.rect(pos, size)
  }
  
  // Updateable
  def update(w: World, rd: Map[String, Boolean], dt: Float) { }
}