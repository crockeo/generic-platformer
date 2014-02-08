package org.crockeo.genericplatformer

import com.badlogic.gdx.backends.lwjgl.LwjglApplication

import org.lwjgl.opengl.Display

import org.crockeo.genericplatformer.launcher.LauncherWindow
import org.crockeo.genericplatformer.editor.Editor
import org.crockeo.genericplatformer.game.Game

object Main {
  private var app: LwjglApplication = null
  
  def main(args: Array[String]) {
    val cfg = ConfigParser("config.cfg")
    
    if (args.length == 1) {
           if (args(0) == "editor") app = new LwjglApplication(new Editor(cfg), cfg.makeLwjglApplicationConfiguration)
      else if (args(0) == "game"  ) app = new LwjglApplication(new Game  (cfg), cfg.makeLwjglApplicationConfiguration)
      else                          main(Array.empty)
    } else {
      new LauncherWindow
    }
  }
}