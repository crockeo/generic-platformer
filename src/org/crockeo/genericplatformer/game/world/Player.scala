package org.crockeo.genericplatformer.game.world

import org.crockeo.genericplatformer.assets.TextureManager
import org.crockeo.genericplatformer.game.World
import org.crockeo.genericplatformer.geom._
import org.crockeo.genericplatformer.Graphics

class Player(sp: Vector) extends WorldObject(sp, new Vector(32, 64)) {
  private val maxspeed    : Vector = new Vector(300f , 1200f)
  private val minspeed    : Vector = new Vector(50f  , 50f  )
  private val accelrate   : Vector = new Vector(0.25f, 1.75f)
  private val airaccelrate: Vector = new Vector(1f   , 1.75f)
  private val decelrate   : Vector = new Vector(0.2f , 1f   )
  private val airdecelrate: Vector = new Vector(2.f  , 1f   )
  private val jumpspeed   : Float  = 400
  
  private var speed: Vector = new Vector(0, 0)
  private var onground: Boolean = true
  private var justjumped: Boolean = false
  private var jumpsleft: Int = 2
  
  // Renderable
  def render = {
    Graphics.render("player", TextureManager.rLoad("res/player.png"), pos.x, pos.y, size.x, size.y)
  }
  
  // Updateable
  def update(w: World, rd: Map[String, Boolean], dt: Float) {    
    // Handling the input
    def handleInput {
      val as =
        if (onground) accelrate
        else          airaccelrate
        
      val ds =
        if (onground) decelrate
        else          airdecelrate
        
      // Moving leftward
      if (rd("left") )
        if (speed.x >  minspeed.x) speed.x = Lerps.lerp(speed.x, -maxspeed.x, Math.abs(as.x - ds.x), dt)
        else                       speed.x = Lerps.lerp(speed.x, -maxspeed.x, as.x                 , dt)
        
      // Moving rightward
      if (rd("right"))
        if (speed.x < -minspeed.x) speed.x = Lerps.lerp(speed.x,  maxspeed.x, math.abs(as.x - ds.x), dt)
        else                       speed.x = Lerps.lerp(speed.x,  maxspeed.x, as.x                 , dt)
      
      // Decelerating
      if (!rd("left") && !rd("right")) {
        if (speed.x >= -minspeed.x &&
            speed.x <=  minspeed.x &&
            onground                 ) speed.x = 0
        else                           speed.x = Lerps.lerp(speed.x, 0, ds.x, dt)
      }
      
      // Jumping
      if (rd("jump") && jumpsleft > 0) {
        if (!justjumped) {
          if (rd("left")  && speed.x > minspeed.x) speed.x = -maxspeed.x / 3
          if (rd("right") && speed.x < minspeed.x) speed.x =  maxspeed.x / 3
          
          speed.y = -jumpspeed
          onground = false
          jumpsleft -= 1
          justjumped = true
        }
      } else justjumped = false
      
      // Respawning
      if (rd("reset")) w.respawn
    }
    
    // Applying gravity
    def gravity {
      if (onground) jumpsleft = 2
      else {
        jumpsleft =
          if (jumpsleft >= 1) 1
          else                0
        speed.y = Lerps.lerp(speed.y, maxspeed.y, accelrate.y, dt)
      }
    }
    
    // Making sure the player doesn't exceed its max speed
    def fixSpeeds {
      if (speed.x >  maxspeed.x) speed.x =  maxspeed.x
      if (speed.x < -maxspeed.x) speed.x = -maxspeed.x
      
      if (speed.y >  maxspeed.y) speed.y =  maxspeed.y
      if (speed.y < -maxspeed.y) speed.y = -maxspeed.y
      
      if (onground) speed.y = 0
    }
    
    // Moving
    def move { translate(speed * dt) }
    
    // Handling collision logic
    def bound {
      var anycollision = false
      
      for (b <- w.blocks) {
        collision(b) match {
          case None     => Unit
          case Some(ct) => {
            anycollision = true
            
            ct match {
              case TopCollision    => {
                pos.y = b.bottom
                if (speed.y < 0) speed.y = (-speed.y / 12)
              }
              
              case BottomCollision => {
                if (speed.y >= 0) {
                  pos.y =  b.top - size.y
                  onground = true
                  jumpsleft = 2
                }
              }
                
              case LeftCollision   => {
                pos.x = b.right
                if (speed.x < 0) speed.x = (-speed.x / 6)
              }
              
              case RightCollision  => {
                pos.x = b.left - size.x
                if (speed.x > 0) speed.x = (-speed.x / 6)
              }
            }
          }
        }
      }
      
      for (cp <- w.checkpoints) {
        if (collides(cp)) {
          w.activeCheckpoint.activated = false
          
          cp.activated = true
          w.activeCheckpoint = cp
        }
      }
      
      if (!anycollision) onground = false
    }
    
    handleInput
    gravity
    fixSpeeds
    move
    bound
  }
}