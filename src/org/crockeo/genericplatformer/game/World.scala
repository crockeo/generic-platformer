package org.crockeo.genericplatformer.game

import org.crockeo.genericplatformer.game.traits._
import org.crockeo.genericplatformer.game.world._
import org.crockeo.genericplatformer.geom.Vector
import org.crockeo.genericplatformer.{ Graphics, Parser }

class World(val sp: Vector, var blocks: List[Block], var checkpoints: List[Checkpoint]) extends Renderable
                                                 with    Updateable {
  override def toString: String = {
    val spString: String = s"sp ${sp.x} ${sp.y}"
    def blockString(b: Block): String = s"b ${b.pos.x} ${b.pos.y} ${b.size.x} ${b.size.y}"
    
    s"${spString}" + blocks.map(b => "\n" + blockString(b))
  }
  
  var activeCheckpoint: Checkpoint = new Checkpoint(sp.copy)
  var player: Player = makeRespawnPlayer
  
  // Making the player that the respawn function uses
  def makeRespawnPlayer: Player =
    new Player(activeCheckpoint.pos.copy)
  
  def respawn = { player = makeRespawnPlayer }
  
  // Adding or removing either a block or a checkpoint
  private def add[T](t: T, b: Boolean): Unit =
    if (b) blocks = t.asInstanceOf[Block] +: blocks
    else   checkpoints = t.asInstanceOf[Checkpoint] +: checkpoints
    
  private def remove[T](t: T, b: Boolean): Unit = {
    val bef =
      if (b) blocks.takeWhile(_ != t)
      else   checkpoints.takeWhile(_ != t)
        
    if (b) blocks = bef.asInstanceOf[List[Block]] ++ blocks.drop(bef.length + 1)
    else   checkpoints = bef.asInstanceOf[List[Checkpoint]] ++ checkpoints.drop(bef.length + 1)
  }
  
  // Adding or removing a block
  def addBlock(b: Block): Unit = add[Block](b, true)
  def removeBlock(b: Block): Unit = remove[Block](b, true)
  
  // Adding or removing a checkpoint
  def addCheckpoint(c: Checkpoint): Unit = add[Checkpoint](c, false)
  def removeCheckpoint(c: Checkpoint): Unit = remove[Checkpoint](c, false)
  
  // Renderable
  def render {
    Graphics.start
      player.render
      blocks.foreach(_.render)
      checkpoints.foreach(_.render)
    Graphics.end
  }
  
  // Updateable
  def update(w: World, kd: Map[String, Boolean], dt: Float) {    
    player.update(w, kd, dt)
    blocks.foreach(_.update(w, kd, dt))
  }
}

object World {
  def empty: World =
    new World(new Vector(0, 0), List.empty, List.empty)
}

object WorldParser extends Parser[World] {
  val default: World = new World(new Vector(0, 0), List.empty, List.empty)
  
  def parseLine(w: World, line: String): World = {
    val sline = line.split(' ')
    
    sline(0) match {
      case "sp" =>
        if (sline.length == 3) new World(new Vector(sline(1).toFloat, sline(2).toFloat),
                                         w.blocks,
                                         w.checkpoints)
        else                   w
      
      case "b"  =>
        if (sline.length == 5) new World(w.sp,
        	                             w.blocks :+ new Block(new Vector(sline(1).toFloat, sline(2).toFloat),
                                                               new Vector(sline(3).toFloat, sline(4).toFloat)),
                                         w.checkpoints)
        else                   w
      
      case "cp" =>
        if (sline.length == 3) new World(w.sp,
                                         w.blocks,
                                         w.checkpoints :+ new Checkpoint(new Vector(sline(1).toFloat, sline(2).toFloat)))
        else                   w
      
      case default => w
    }
  }
}