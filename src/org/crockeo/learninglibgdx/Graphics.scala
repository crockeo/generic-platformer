package org.crockeo.learninglibgdx

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.OrthographicCamera
import org.crockeo.learninglibgdx.game.geom.Vector

object Graphics {
  private var sr: ShapeRenderer = null
  private var cam: OrthographicCamera = null
  
  def create(cam: OrthographicCamera) {
    sr = new ShapeRenderer
    this.cam = cam
  }
  
  def start {
    sr.setProjectionMatrix(cam.combined)
    sr.begin(ShapeRenderer.ShapeType.Filled)
  }
  
  def end = sr.end
  def rect(pos: Vector, size: Vector) = sr.rect(pos.x, pos.y, size.x, size.y)
}