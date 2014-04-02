package org.crockeo.genericplatformer.game.world

import org.crockeo.genericplatformer.assets.TextureManager
import org.crockeo.genericplatformer.geom.Vector
import org.crockeo.genericplatformer.game.World
import org.crockeo.genericplatformer.Graphics

class Checkpoint(sp: Vector) extends WorldObject(sp, new Vector(32, 64)) {
  var activated: Boolean = false
  
  // Renderable
  def render {
    if (activated) Graphics.render("checkpoints", TextureManager.rLoad("res/checkpointActive.png"), pos.x, pos.y, size.x, size.y)
    else           Graphics.render("checkpoints", TextureManager.rLoad("res/checkpoint.png"      ), pos.x, pos.y, size.x, size.y)
  }
  
  // Updateable
  def update(w: World, rd: Map[String, Boolean], dt: Float) { }
}