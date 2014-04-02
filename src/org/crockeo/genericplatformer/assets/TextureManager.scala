package org.crockeo.genericplatformer.assets

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx

object TextureManager extends AssetManager[Texture] {
  // The backend for loading an asset
  protected def loadBackend(path: String): Texture =
    new Texture(Gdx.files.local(path))
  
  def rLoad(path: String): TextureRegion =
    new TextureRegion(load(path))
  
  // Loading all assets
  override def init = {}
}