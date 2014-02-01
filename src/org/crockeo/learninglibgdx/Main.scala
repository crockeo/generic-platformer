package org.crockeo.learninglibgdx

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import org.crockeo.genericplatformer.Gimport org.crockeo.learninglibgdx.ConfigParser
ame
import org.crockeo.learninglibgdx.Game

object Main {
  def main(args: Array[String]) {
    val cfg = ConfigParser("config.cfg")
  
    new LwjglApplication(new Game(cfg), cfg.displayConfig.makeLwjglApplicationConfiguration)
  }
}