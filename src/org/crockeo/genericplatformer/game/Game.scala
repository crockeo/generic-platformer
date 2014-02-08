package org.crockeo.genericplatformer.game

import com.badlogic.gdx.graphics.{ OrthographicCamera, GL10 }
import com.badlogic.gdx.{ ApplicationListener, Gdx }

import org.crockeo.genericplatformer.{ InputConstructor, Graphics, Config }

class Game(cfg: Config) extends ApplicationListener {
  var update = true
  var cam: OrthographicCamera = null
  var world: World = null
  
  // Initializing the game
  def create {
    cam = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    cam.setToOrtho(true, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    
    
    Graphics.create(cam)
    world = WorldParser("world.txt")
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
      
      if (input("camLeft")) target.x -= (Gdx.graphics.getWidth  / 4)
      if (input("camRight")) target.x += (Gdx.graphics.getWidth  / 4)
      if (input("camUp"   )) target.y -= (Gdx.graphics.getHeight / 4)
      if (input("camDown" )) target.y += (Gdx.graphics.getHeight / 4)
      
      cam.position.x += (target.x - cam.position.x) * (Gdx.graphics.getDeltaTime * 6)
      cam.position.y += (target.y - cam.position.y) * (Gdx.graphics.getDeltaTime * 6)
                    
      cam.update
      cam.apply(gl)
      
      world.update(world, input, Gdx.graphics.getDeltaTime)
      world.render
    }
  }
  
  def pause { update = false }
  def resume { update = true }
  
  def resize(w: Int, h: Int) {}
}