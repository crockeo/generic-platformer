package org.crockeo.genericplatformer.assets

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.Gdx

object MusicManager extends AssetManager[Music] {
  // The backend for loading an asset
  protected def loadBackend(path: String): Music =
    Gdx.audio.newMusic(Gdx.files.local(path))
  
  // Loading all assets
  override def init = {}
}