package org.crockeo.genericplatformer

import com.badlogic.gdx.Gdx

object InputConstructor {
  // Constructing an input state from an input config
  def apply(cfg: Config): Map[String, Boolean] =
    cfg.buttons.mapValues(Gdx.input.isButtonPressed(_)) ++
    cfg.keys   .mapValues(Gdx.input.isKeyPressed   (_))
}