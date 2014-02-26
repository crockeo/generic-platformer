package org.crockeo.genericplatformer.geom

// The types of collision
trait CollisionType

case object TopCollision    extends CollisionType
case object BottomCollision extends CollisionType
case object LeftCollision   extends CollisionType
case object RightCollision  extends CollisionType

class CollisionRectangle(var pos: Vector, var size: Vector) {
  // toString
  override def toString: String =
    s"${pos}\n${size}"
  
  // Copying the CollisionRectangle
  def copy: CollisionRectangle = new CollisionRectangle(pos.copy, size.copy)
    
  // Accessors
  def x: Float = pos.x
  def y: Float = pos.y
  def width : Float = size.x
  def height: Float = size.y
  
  // Mutators
  def setPosition(pos : Vector): Unit = this.pos = pos
  def setX       (x   : Float ): Unit = setPosition(new Vector(x, pos.y))
  def setY       (y   : Float ): Unit = setPosition(new Vector(pos.x, y))
  def setSize    (size: Vector): Unit = this.size = size
  def setWidth   (w   : Float ): Unit = setSize(new Vector(w, size.y))
  def setHeight  (h   : Float ): Unit = setSize(new Vector(size.x, h))
  
  // Moving the collision rectangle
  def translate(d: Vector): Unit = pos += d
  
  // Resizing the collision rectangle
  def resize   (d: Vector): Unit = size += d
  
  // Getting specific locations in the collision rectangle
  def center: Vector = pos + (size / 2)
  def top   : Float = pos.y
  def bottom: Float = pos.y + size.y
  def left  : Float = pos.x
  def right : Float = pos.x + size.x
  
  // Checking relative location
  def ==     (cr: CollisionRectangle): Boolean = center == cr.center
  def above  (cr: CollisionRectangle): Boolean = center.above(cr.center)
  def below  (cr: CollisionRectangle): Boolean = center.below(cr.center)
  def leftOf (cr: CollisionRectangle): Boolean = center.leftOf(cr.center)
  def rightOf(cr: CollisionRectangle): Boolean = center.rightOf(cr.center)
  
  // Checking if the collision rectangle contains a vector
  def contains(v: Vector): Boolean =
    (left <= v.x) && (right >= v.x) && (top <= v.y) && (bottom >= v.y)
  
  // Separating Axis Theorem collision
  def collision(cr: CollisionRectangle): Option[CollisionType] = {
    val t: Vector = center.distanceSplit(cr.center) - (size / 2) - (cr.size / 2)
    
    if (t.x <= 0 && t.y <= 0) {
      if (t.y >= t.x) {
        if      (above(cr)) Some(BottomCollision)
        else if (below(cr)) Some(TopCollision)
        else                None
      } else {
        if      (leftOf (cr)) Some(RightCollision)
        else if (rightOf(cr)) Some(LeftCollision)
        else                  None
      }
    } else None
  }
  
  // Checking if the CollisionRectangle collides at all
  def collides(cr: CollisionRectangle): Boolean =
    collision(cr) match {
      case Some(_) => true
      case None    => false
    }
  
  // Collision algorithm when you know you'll have a collision
  def collisionGuaranteed(cr: CollisionRectangle): CollisionType =
    collision(cr) match {
      case Some(ct) => ct
      case None     => throw new Exception("No collision")
    }
}