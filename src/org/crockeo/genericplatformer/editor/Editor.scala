package org.crockeo.genericplatformer.editor

import com.badlogic.gdx.graphics.{ OrthographicCamera, GL10 }
import com.badlogic.gdx.{ ApplicationListener, Gdx }

import org.crockeo.genericplatformer.game.world.{ Checkpoint, Block }
import org.crockeo.genericplatformer.game.Game
import org.crockeo.genericplatformer.geom.Vector
import org.crockeo.genericplatformer.{ InputConstructor, Graphics, Config }

class Editor(cfg: Config) extends ApplicationListener {
  private var game: Game = null
  
  var newblock: Block      = null
  var newcp   : Checkpoint = null
  
  // Initializing the game
  def create {
    game = new Game(cfg)
    game.create
    
    newblock = null
    newcp    = null
  }
  
  // Cleaning up
  def dispose { game.dispose }
  
  // Updating and rendering
  def render {
    val input = InputConstructor(cfg)
    val gl = Gdx.gl10
    
    ButtonEvent.update(input)
    
    // Updating the game
    def update {
      val cp = new Vector(game.cam.position.x - (Gdx.graphics.getWidth / 2), game.cam.position.y - (Gdx.graphics.getHeight / 2))
      val mp = new Vector(Gdx.input.getX, Gdx.input.getY)
      val ep = cp + mp
      
      // Handling events from the left mouse button
      ButtonEvent("mouseLeft") match {
        case NoEvent        => Unit
        case ButtonPressed  => game.world.blocks = new Block(ep, new Vector(0, 0)) +: game.world.blocks
        case ButtonHeld     => game.world.blocks.head.size = ep - game.world.blocks.head.pos
        case ButtonReleased => {
          // Fixing negative blocks
          if (game.world.blocks.head.size.x < 0) {
            game.world.blocks.head.pos.x = game.world.blocks.head.pos.x + game.world.blocks.head.size.x
            game.world.blocks.head.size.x = -game.world.blocks.head.size.x
          }
          
          // Fixing negative blocks
          if (game.world.blocks.head.size.y < 0) {
            game.world.blocks.head.pos.y = game.world.blocks.head.pos.y + game.world.blocks.head.size.y
            game.world.blocks.head.size.y = -game.world.blocks.head.size.y
          }
        }
      }
      
      // Handling events from the right mouse button
      ButtonEvent("mouseRight") match {
        case NoEvent        => Unit
        case ButtonPressed  => game.world.checkpoints = new Checkpoint(ep) +: game.world.checkpoints
        case ButtonHeld     => game.world.checkpoints.head.pos = ep
        case ButtonReleased => Unit
      }
      
      // Handling events from the middle mouse button
      ButtonEvent("mouseMiddle") match {
        case NoEvent        => Unit
        case ButtonPressed  => Unit
        case ButtonHeld     => Unit
        case ButtonReleased => {
          val fb = game.world.blocks.find(_ contains ep)
          val fc = game.world.blocks.find(_ contains ep)
          
          fb match {
            case Some(b) => ???
            case None =>
              fc match {
                // TODO: FINISH
              }
          }
        }
      }
    }
    
    update
    if (newblock != null) {
      Graphics.start
        newblock.render
      Graphics.end
    }
    
    game.render
  }
  
  // UNUSED \\
  def pause { game.pause }
  def resume { game.resume }
  
  def resize(w: Int, h: Int) { game.resize(w, h) }
}