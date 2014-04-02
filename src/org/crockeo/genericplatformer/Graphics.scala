package org.crockeo.genericplatformer

import com.badlogic.gdx.graphics.g2d.{ TextureRegion, SpriteBatch, Sprite }
import com.badlogic.gdx.graphics.OrthographicCamera

import org.crockeo.genericplatformer.geom.Vector

object Graphics {
  private var spriteBatches: Map[String, SpriteBatch] = Map[String, SpriteBatch]()
  private var cam: OrthographicCamera = null
  
  // Creating the graphics
  def create(cam: OrthographicCamera) { this.cam = cam }
  
  // Getting a SpriteBatch
  def getSpriteBatch(name: String): SpriteBatch = {
    if (!spriteBatches.contains(name))
      spriteBatches = spriteBatches + (name -> new SpriteBatch())
    spriteBatches(name)
  }
  
  // Starting a render
  def start(name: String) {
    getSpriteBatch(name).setProjectionMatrix(cam.combined)
    getSpriteBatch(name).begin
  }
  
  // Ending a render
  def end(name: String) = getSpriteBatch(name).end
  
  // Rendering a sprite
  def render(name : String , image: TextureRegion,
             x    : Float  , y    : Float        ,
             w    : Float  , h    : Float        ,
             flipX: Boolean, flipY: Boolean      ) {
    if (image.isFlipX() != flipX) image.flip(true , false);
    if (image.isFlipY() != flipY) image.flip(false, true )
    
    getSpriteBatch(name).draw(image, x, y, w, h);
  }
  
  // Rendering a sprite
  def render(name : String , image: TextureRegion,
             x    : Float  , y    : Float        ,
             w    : Float  , h    : Float        ): Unit =
    render(name, image, x, y, w, h, false, false)
  
  // Rendering a sprite
  def render(name : String , image: TextureRegion,
             x    : Float  , y    : Float        ,
             flipX: Boolean, flipY: Boolean      ): Unit =
    render(name, image, x, y, image.getRegionWidth(), image.getRegionHeight(), flipX, flipY)
  
  // Rendering a sprite
  def render(name : String , image: TextureRegion,
             x    : Float  , y    : Float        ): Unit =
    render(name, image, x, y, image.getRegionWidth(), image.getRegionHeight())
}