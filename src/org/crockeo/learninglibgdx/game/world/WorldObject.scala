package org.crockeo.learninglibgdx.game.world

import org.crockeo.learninglibgdx.game.traits._
import org.crockeo.learninglibgdx.game.geom._
import org.crockeo.learninglibgdx.game.World

abstract class WorldObject(pos: Vector, size: Vector) extends CollisionRectangle(pos, size)
                                                      with    Renderable
                                                      with    Updateable