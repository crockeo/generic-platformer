package org.crockeo.genericplatformer.launcher

import javax.swing._

class LauncherWindow extends JFrame {
  setTitle("Generic Platformer")
  
  val panel: LauncherPanel = new LauncherPanel(this)
  add(panel)
  pack
  setResizable(false)
  setLocationRelativeTo(null)
  
  setVisible(true)
}