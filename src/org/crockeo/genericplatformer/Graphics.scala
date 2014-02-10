package org.crockeo.genericplatformer

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.OrthographicCamera

import org.crockeo.genericplatformer.geom.Vector

object Graphics {
  private var sr: ShapeRenderer = null
  private var cam: OrthographicCamera = null
  
  // Creating the graphics
  def create(cam: OrthographicCamera) {
    sr = new ShapeRenderer
    this.cam = cam
  }
  
  // Starting a render
  def start {
    sr.setProjectionMatrix(cam.combined)
    sr.begin(ShapeRenderer.ShapeType.Filled)
  }
  
  // Ending a render
  def end = sr.end
  
  // Changing the color
  def color(r: Float, g: Float, b: Float, a: Float) { sr.setColor(r, g, b, a) }
  def color(r: Float, g: Float, b: Float) { color(r, g, b, 1) }
  
  // Drawing a rectangle
  def rect(pos: Vector, size: Vector) = sr.rect(pos.x, pos.y, size.x, size.y)
}