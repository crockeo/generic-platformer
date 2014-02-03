package org.crockeo.genericplatformer

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.Input

// Configuration for the display
case class DisplayConfig(val width: Int, val height: Int, val fullscreen: Boolean) {  
  override def toString: String =
    s"dc ${width} ${height} ${fullscreen}"
    
  def makeLwjglApplicationConfiguration: LwjglApplicationConfiguration = {
    val cfg = new LwjglApplicationConfiguration
    
    cfg.title = "Learning LibGDX"
    cfg.width = width
    cfg.height = height
    cfg.fullscreen = fullscreen
    
    cfg
  }
}

// Configuration for input
case class InputConfig(val left: Int, val right: Int, val jump: Int,
                       val reset: Int,
                       val camLeft: Int, val camRight: Int, val camUp: Int, val camDown: Int) { 
  override def toString: String =
    s"ic ${left} ${right} ${jump} ${camLeft} ${reset} ${camRight} ${camUp} ${camDown}"
}

// The general config file
case class Config(val displayConfig: DisplayConfig, val inputConfig: InputConfig) {
  override def toString: String =
    displayConfig.toString + "\n" + inputConfig.toString
}

object ConfigParser extends Parser[Config] {
  val default: Config = Config(DisplayConfig(640, 480, false), InputConfig(Input.Keys.A, Input.Keys.D, Input.Keys.SPACE,
                                                                           Input.Keys.R,
                                                                           Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN))
  
  def parseLine(cfg: Config, line: String): Config = {
    val sline = line.split(' ')
    
    sline(0) match {
      case "dc" =>
        if (sline.length == 4) new Config(new DisplayConfig(sline(1).toInt, sline(2).toInt, sline(3).toBoolean), cfg.inputConfig)
        else                   cfg
      
      case "ic" =>
        if (sline.length == 7) new Config(cfg.displayConfig, new InputConfig(sline(1).toInt, sline(2).toInt, sline(3).toInt, sline(4).toInt, sline(5).toInt, sline(6).toInt, sline(7).toInt, sline(8).toInt))
        else                   cfg
        
      case default => cfg
    }
  }
}