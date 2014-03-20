package org.crockeo.genericplatformer.assets

import com.badlogic.gdx.graphics.g2d.{ TextureRegion, SpriteBatch, Animation }
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Array

// Actor for updating the animations
class AnimationActor(val animation: Animation) {
  private var time: Float = 0.0f
  
  // Updating the AnimationActor
  def update(dt: Float): Unit =
    time += dt
  
  // Getting the current frame
  def getFrame: TextureRegion =
    animation.getKeyFrame(time)
}

object AnimationManager {
  // The cache
  private var cache: Map[String, AnimationActor] = Map[String, AnimationActor]()
  
  // The backend for loading an asset
  private def loadBackend(path: String,
                  width: Int, height: Int,
                  timeStep: Float): AnimationActor = {
    var time: Float = 0.0f
    
    val frames: Array[TextureRegion] = new Array[TextureRegion]()
    TextureRegion.split(TextureManager.load(path),
                        width, height).foreach { _.foreach { frames.add(_) } }
    
    val aa: AnimationActor = new AnimationActor(new Animation(timeStep, frames))
    
    aa
  }
  
  // Updating all AnimationActors
  def update(dt: Float) =
    cache.values.foreach { _.update(dt) }
  
  // Caching an asset
  def cache(path: String,
            width: Int, height: Int,
            timeStep: Float): Unit =
    if (!cache.contains(path)) cache = cache + (path -> loadBackend(path, width, height, timeStep))
    
  // Decaching an asset
  def decache(path: String): Unit =
    if (cache.contains(path)) cache = cache - path
    
  // Loading an asset
  def load(path: String,
           width: Int, height: Int,
           timeStep: Float): AnimationActor =
    if (cache.contains(path)) cache(path)
    else {
      val temp: AnimationActor = loadBackend(path, width, height, timeStep)
      cache = cache + (path -> temp)
      temp
    }
    
  // Caching all assets
  def init = {}
}