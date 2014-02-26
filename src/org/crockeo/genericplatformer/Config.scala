package org.crockeo.genericplatformer

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.Input

class Config(val width: Int, val height: Int, val fullscreen: Boolean,
             val buttons: Map[String, Int], val keys: Map[String, Int]) {
  // toString
  override def toString: String = {
    def makeString(pref: String, map: Map[String, Int]): String =
      if (map.isEmpty) ""
      else             map.map(pair => pref + " " + pair._1 + " " + pair._2).reduceLeft(_ + "\n" + _)
      
    s"width ${width}\n"              +  
    s"height ${height}\n"            +
    s"fullscreen ${fullscreen}\n"    +
    s"${makeString("m", buttons)}\n" +
    s"${makeString("k", keys)}"
  }
    
  // Making a LwjglApplicationConfiguration
  def makeLwjglApplicationConfiguration: LwjglApplicationConfiguration = {
    val cfg = new LwjglApplicationConfiguration
    
    cfg.width = width
    cfg.height = height
    cfg.fullscreen = fullscreen
    
    cfg
  }
}

object ConfigParser extends Parser[Config] {
  val default = new Config(640, 480, false,
                          Map(
                            "mouseLeft" -> Input.Buttons.LEFT,
                            "mouseRight" -> Input.Buttons.RIGHT,
                            "mouseMiddle" -> Input.Buttons.MIDDLE
                          ),
                          
                          Map(
                            "left" -> Input.Keys.A,
                            "right" -> Input.Keys.D,
                            "jump" -> Input.Keys.SPACE,
                            "reset" -> Input.Keys.R,
                            
                            "camLeft" -> Input.Keys.LEFT,
                            "camRight" -> Input.Keys.RIGHT,
                            "camUp" -> Input.Keys.UP,
                            "camDown" -> Input.Keys.DOWN
                          ))
  
  def parseLine(cfg: Config, line: String): Config = {
    val sline = line.split(" ")
    
    if (sline.length > 0) {
      sline(0) match {
        case "width" =>
          if (sline.length == 2) new Config(sline(1).toInt, cfg.height, cfg.fullscreen,
                                            cfg.buttons,
                                            cfg.keys)
          else                   cfg
        
        case "height" =>
          if (sline.length == 2) new Config(cfg.width, sline(1).toInt, cfg.fullscreen,
                                            cfg.buttons,
                                            cfg.keys)
          else                   cfg
        case "fullscreen" =>
          if (sline.length == 2) new Config(cfg.width, cfg.height, sline(1).toBoolean,
                                            cfg.buttons, cfg.keys)
          else                   cfg
        
        case "m" =>
          if (sline.length == 3) new Config(cfg.width, cfg.height, cfg.fullscreen,
                                            cfg.buttons + (sline(1) -> sline(2).toInt),
                                            cfg.keys)
          else                   cfg
        case "k" =>
          if (sline.length == 3) new Config(cfg.width, cfg.height, cfg.fullscreen,
                                            cfg.buttons,
                                            cfg.keys + (sline(1) -> sline(2).toInt))
          else cfg
      }
    } else cfg
  }
}