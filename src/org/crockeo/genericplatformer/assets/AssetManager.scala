package org.crockeo.genericplatformer.assets

trait AssetManager[T] {
  private var cache: Map[String, T] = Map[String, T]()
  
  // The backend for loading an asset
  protected def loadBackend(path: String): T
  
  // Caching an asset
  def cache(path: String): Unit =
    if (!cache.contains(path)) cache = cache + (path -> loadBackend(path))
  
  // Decaching an asset
  def decache(path: String): Unit =
    if (cache.contains(path)) cache = cache - path
  
  // Loading an asset
  def load(path: String): T =
    if (cache.contains(path)) cache(path)
    else {
      val temp: T = loadBackend(path)
      cache = cache + (path -> temp)
      temp
    }
  
  // Caching all assets
  def init = {}
}