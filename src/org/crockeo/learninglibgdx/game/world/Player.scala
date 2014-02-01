package org.crockeo.learninglibgdx.game.world

import org.crockeo.learninglibgdx.game.geom._
import org.crockeo.learninglibgdx.game.World
import org.crockeo.learninglibgdx.{ InputState, Graphics }

class Player(sp: Vector) extends WorldObject(sp, new Vector(32, 64)) {
  private val accelrate   : Vector = new Vector(500, 400 )
  private val airaccelrate: Vector = new Vector(150, 400 )
  private val decelrate   : Vector = new Vector(650, 0   )
  private val airdecelrate: Vector = new Vector(100, 0   )
  private val maxspeed    : Vector = new Vector(150, 1000)
  private val minspeed    : Vector = new Vector(5  , 5   )
  private val jumpspeed   : Float  = 300
  private val stepheight  : Float  = 15
  
  private var speed: Vector = new Vector(0, 0)
  private var onground: Boolean = true
  private var justjumped: Boolean = false
  private var jumpsleft: Int = 2
  private var maxheight: Float = 0
  
  // Renderable
  def render { Graphics.rect(pos, size) }
  
  // Updateable
  def update(w: World, is: InputState, dt: Float) {    
    // Handling the input
    def handleInput {
      val as =
        if (onground) accelrate
        else          airaccelrate
        
      val ds =
        if (onground) decelrate
        else          airdecelrate
        
      // Moving leftward
      if (is.left )
        if (speed.x > 0) speed.x -= ((as.x + ds.x) * dt)
        else             speed.x -= (as.x * dt)
        
      // Moving rightward
      if (is.right)
        if (speed.x < 0) speed.x += ((as.x + ds.x) * dt)
        else             speed.x += (as.x * dt)
      
      // Decelerating
      if (!is.left && !is.right) {
             if (speed.x >= -minspeed.x && speed.x <= minspeed.x) speed.x = 0
        else if (speed.x >  minspeed.x)                           speed.x -= (ds.x * dt)
        else if (speed.x < -minspeed.x)                           speed.x += (ds.x * dt)
      }
      
      // Jumping
      if (is.jump && jumpsleft > 0) {
        if (!justjumped) {
          if (is.left  && speed.x > minspeed.x) speed.x = -maxspeed.x / 3
          if (is.right && speed.x < minspeed.x) speed.x =  maxspeed.x / 3
          
          speed.y = -jumpspeed
          onground = false
          jumpsleft -= 1
          justjumped = true
        }
      } else justjumped = false
    }
    
    // Applying gravity
    def gravity {
      if (onground) jumpsleft = 2
      else {
        jumpsleft =
          if (jumpsleft >= 1) 1
          else                0
        speed.y += (accelrate.y * dt)
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
                speed.y = (-speed.y / 12)
              }
              
              case BottomCollision => {
                pos.y =  b.top - size.y
                onground = true
                jumpsleft = 2
              }
                
              case LeftCollision   => {
                pos.x = b.right
                speed.x = (-speed.x / 3)
              }
              
              case RightCollision  => {
                pos.x = b.left - size.x
                speed.x = (-speed.x / 3)
              }
            }
          }
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