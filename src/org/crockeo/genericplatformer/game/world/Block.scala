package org.crockeo.genericplatformer.game.world

import com.badlogic.gdx.graphics.g2d.TextureRegion

import org.crockeo.genericplatformer.assets.TextureManager
import org.crockeo.genericplatformer.geom.Vector
import org.crockeo.genericplatformer.game.World
import org.crockeo.genericplatformer.Graphics

class Block(sp: Vector, ss: Vector) extends WorldObject(sp, ss) {
  // Renderable
  def render: Unit =
    Graphics.render("blocks", TextureManager.rLoad("res/block.png"), pos.x, pos.y, size.x, size.y)
  
  // Updateable
  def update(w: World, rd: Map[String, Boolean], dt: Float) { }
}