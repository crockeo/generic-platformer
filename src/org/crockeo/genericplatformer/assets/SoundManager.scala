package org.crockeo.genericplatformer.assets

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.Gdx

object SoundManager extends AssetManager[Sound] {
  // The backend for loading an asset
  protected def loadBackend(path: String): Sound =
    Gdx.audio.newSound(Gdx.files.local(path))
  
  // Loading all assets
  override def init = {}
}