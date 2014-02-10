package org.crockeo.genericplatformer.geom

object Lerps {
  // Lerping between two floats
  def lerp(f1: Float, f2: Float, steps: Float, dt: Float): Float =
    f1 + ((f2 - f1) * (dt / steps))
   
  // Lerping between two vectors
  def lerp(v1: Vector, v2: Vector, steps: Float, dt: Float): Vector =
    v1 + ((v2 - v1) * (dt / steps))
}