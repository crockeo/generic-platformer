package org.crockeo.genericplatformer.game.world

import org.crockeo.genericplatformer.game.traits._
import org.crockeo.genericplatformer.game.World
import org.crockeo.genericplatformer.geom._

abstract class WorldObject(pos: Vector, size: Vector) extends CollisionRectangle(pos, size)
                                                      with    Renderable
                                                      with    Updateable