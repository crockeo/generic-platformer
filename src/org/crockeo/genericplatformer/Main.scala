package org.crockeo.genericplatformer

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Main {
  def main(args: Array[String]) {
    val cfg = ConfigParser("config.cfg")
  
    new LwjglApplication(new Game(cfg), cfg.displayConfig.makeLwjglApplicationConfiguration)
  }
}