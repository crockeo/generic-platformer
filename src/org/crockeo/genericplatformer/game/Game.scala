package org.crockeo.genericplatformer.game

import com.badlogic.gdx.graphics.{ OrthographicCamera, GL10 }
import com.badlogic.gdx.{ ApplicationListener, Gdx }

import org.crockeo.genericplatformer.assets._
import org.crockeo.genericplatformer.geom.Lerps
import org.crockeo.genericplatformer.{ InputConstructor, Graphics, Config }

class Game(cfg: Config) extends ApplicationListener {
  var update = true
  var cam: OrthographicCamera = null
  var world: World = null
  
  // Initializing the game
  def create {
    // Creating the camera
    cam = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    cam.setToOrtho(true, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    
    // Creating the graphics
    Graphics.create(cam)
    
    // Loading the world
    world = WorldParser("world.txt")
    
    // Loading all the assets
    AnimationManager.init
    TextureManager.init
    MusicManager.init
    SoundManager.init
  }
  
  // Cleaning up
  def dispose {}
  
  // Updating and rendering
  def render {
    if (update) {
      val input = InputConstructor(cfg)
      val gl = Gdx.gl10
    
      gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
      gl.glViewport(0                    , 0,
                    Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    
      // Calculating the target for the camera
      val target = world.player.pos + (world.player.size / 2)
      
      if (input("camLeft" )) target.x -= (Gdx.graphics.getWidth  / 4)
      if (input("camRight")) target.x += (Gdx.graphics.getWidth  / 4)
      if (input("camUp"   )) target.y -= (Gdx.graphics.getHeight / 4)
      if (input("camDown" )) target.y += (Gdx.graphics.getHeight / 4)
      
      cam.position.x = Lerps.lerp(cam.position.x, target.x, 0.2f, Gdx.graphics.getDeltaTime)
      cam.position.y = Lerps.lerp(cam.position.y, target.y, 0.2f, Gdx.graphics.getDeltaTime)
                    
      cam.update
      cam.apply(gl)
      
      AnimationManager.update(Gdx.graphics.getDeltaTime)
      world.update(world, input, Gdx.graphics.getDeltaTime)
      world.render
    }
  }
  
  def pause { update = false }
  def resume { update = true }
  
  def resize(w: Int, h: Int) {}
}