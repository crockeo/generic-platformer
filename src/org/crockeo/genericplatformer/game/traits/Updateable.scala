package org.crockeo.genericplatformer.game.traits

import org.crockeo.genericplatformer.game.World

trait Updateable {
  def update(w: World, kd: Map[String, Boolean], dt: Float): Unit
}