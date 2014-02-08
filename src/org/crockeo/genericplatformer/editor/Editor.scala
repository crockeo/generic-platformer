package org.crockeo.genericplatformer.editor

import com.badlogic.gdx.graphics.{ OrthographicCamera, GL10 }
import com.badlogic.gdx.{ ApplicationListener, Gdx }

import org.crockeo.genericplatformer.game.World
import org.crockeo.genericplatformer.{ InputConstructor, Graphics, Config }

class Editor(cfg: Config) extends ApplicationListener {
  var cam: OrthographicCamera = null
  var world: World = null
  
  // Initializing the game
  def create {
    cam = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    cam.setToOrtho(true, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    
    Graphics.create(cam)
    world = World.empty
  }
  
  // Cleaning up
  def dispose {}
  
  // Updating and rendering
  def render {
    val input = InputConstructor(cfg)
    val gl = Gdx.gl10
    
    // TODO: Finish the editor
  }
  
  // UNUSED \\
  def pause {}
  def resume {}
  
  def resize(w: Int, h: Int) {}
}