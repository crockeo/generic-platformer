package org.crockeo.genericplatformer.game.traits

import com.badlogic.gdx.graphics.g2d.{ TextureRegion, SpriteBatch, Animation }

import org.crockeo.genericplatformer.assets.AnimationActor

trait Animated {
  private var fHori: Boolean = false
  private var fVert: Boolean = false
  
  // Getting the animation actor
  def getAnimationActor: AnimationActor
  
  // Getting the animation
  def getAnimation: Animation =
    getAnimationActor.animation
  
  // Setting the flippedness
  private def maintainFlip(tr: TextureRegion): TextureRegion = {
    tr.flip( fHori && !tr.isFlipX() ||
            !fHori &&  tr.isFlipX(),
             fVert && !tr.isFlipY() ||
            !fVert &&  tr.isFlipY())
              
    tr
  }
    
  def setFlip(hori: Boolean, vert: Boolean): Unit = {
    fHori = hori
    fVert = vert
  }
  
  // Getting the current texture region
  def getFrame: TextureRegion =
    maintainFlip(getAnimationActor.getFrame)
}