package org.crockeo.learninglibgdx.game.geom

import com.badlogic.gdx.math.Vector2

class Vector(var x: Float, var y: Float) {
  // toString
  override def toString: String =
    s"{ ${x}, ${y} }"
  
  // Mapping a function onto the vector by changing the values
  private def mapChange(fn: (Float, Float) => Float)(v: Vector) = set(mapKeep(fn)(v))
  
  // Mapping a function onto the vector by making a new vector
  private def mapKeep(fn: (Float, Float) => Float)(v: Vector): Vector =
    new Vector(fn(x, v.x), fn(y, v.y))
  
  // Making a vector out of a single value
  private def vec(f: Float): Vector = new Vector(f, f)
  
  // Converting the vector to a native vector
  def toNative: Vector2 = new Vector2(x, y)
  
  // Getting the distance between this vector and another vector
  def distanceSplit(v: Vector): Vector = (this - v).mapKeep(Math.abs)
  def distance(v: Vector): Float = distanceSplit(v).x + distanceSplit(v).y
    
  
  // Setting the value of the vector to another vector
  def set(v: Vector): Unit = {
    x = v.x
    y = v.y
  }
  
  // Mapping a function onto the vector by creating a new vector
  def mapKeep(fn: Float => Float): Vector = new Vector(fn(x), fn(y))
  def mapChange(fn: Float => Float): Unit = set(mapKeep(fn))
  
  // Vector operations
  val + = mapKeep(_ + _)_
  val - = mapKeep(_ - _)_
  val * = mapKeep(_ * _)_
  val / = mapKeep(_ / _)_
  
  val += = mapChange(_ + _)_
  val -= = mapChange(_ - _)_
  val *= = mapChange(_ * _)_
  val /= = mapChange(_ / _)_
  
  // Scalar operations
  def +(f: Float): Vector = this + vec(f)
  def -(f: Float): Vector = this - vec(f)
  def *(f: Float): Vector = this * vec(f)
  def /(f: Float): Vector = this / vec(f)
  
  def +=(f: Float): Unit = this += vec(f)
  def -=(f: Float): Unit = this -= vec(f)
  def *=(f: Float): Unit = this *= vec(f)
  def /=(f: Float): Unit = this /= vec(f)
  
  // Checking relative location
  def on     (v: Vector): Boolean = (x == v.x) && (y == v.y)
  def above  (v: Vector): Boolean = y < v.y
  def below  (v: Vector): Boolean = y > v.y
  def leftOf (v: Vector): Boolean = x < v.x
  def rightOf(v: Vector): Boolean = x > v.x
}