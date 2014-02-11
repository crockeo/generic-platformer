package org.crockeo.genericplatformer.editor

trait ButtonEvent

case object NoEvent        extends ButtonEvent
case object ButtonPressed  extends ButtonEvent
case object ButtonReleased extends ButtonEvent
case object ButtonHeld     extends ButtonEvent

object ButtonEvent {
  private var input: Map[String, Boolean] = Map.empty
  private var last: Map[String, Boolean] = Map.empty
  
  def update(input: Map[String, Boolean]): Unit = {
    last = this.input
    this.input = input
  }
  
  def apply(flag: String): ButtonEvent = {
         if (last.isEmpty               ) NoEvent
    else if ( input(flag) &&  last(flag)) ButtonHeld
    else if ( input(flag) && !last(flag)) ButtonPressed
    else if (!input(flag) &&  last(flag)) ButtonReleased
    else                                  NoEvent
  }
}