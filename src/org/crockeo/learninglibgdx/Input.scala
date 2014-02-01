package org.crockeo.learninglibgdx

import com.badlogic.gdx.Gdx
impimport org.crockeo.learninglibgdx.InputConfig
ort org.crockeo.genericplatformer.InputConfig

// Current input state
case class InputState (val left: Boolean, val right: Boolean, val jump: Boolean, val camUp: Boolean, val camDown: Boolean, val camLeft: Boolean, val camRight: Boolean)

object InputConstructor {
  // Constructing an input state from an input config
  def apply(ic: InputConfig): InputState =
    InputState(Gdx.input.isKeyPressed(ic.left), Gdx.input.isKeyPressed(ic.right), Gdx.input.isKeyPressed(ic.jump),
               Gdx.input.isKeyPressed(ic.camUp), Gdx.input.isKeyPressed(ic.camDown), Gdx.input.isKeyPressed(ic.camLeft), Gdx.input.isKeyPressed(ic.camRight))
}