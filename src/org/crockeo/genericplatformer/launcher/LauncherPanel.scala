package org.crockeo.genericplatformer.launcher

import org.crockeo.genericplatformer.Main
import org.crockeo.genericplatformer.IO

import java.awt.event._
import javax.swing._
import java.awt._

class LauncherPanel(window: LauncherWindow) extends JPanel {
  private object LauncherPanelApplicationListener extends ActionListener {
    def actionPerformed(e: ActionEvent) {
           if (e.getSource.equals(editorButton)) Main.main(Array("editor"))
      else if (e.getSource.equals(gameButton  )) Main.main(Array("game"  ))
      else if (e.getSource.equals(quitButton  )) System.exit(0)
      
      window.hide
    }
  }
  
  override def getPreferredSize: Dimension = new Dimension(250, 25)
  
  private val editorButton: JButton = new JButton("Editor")
  editorButton.addActionListener(LauncherPanelApplicationListener)
  add(editorButton)
  
  private val gameButton: JButton = new JButton("Game")
  gameButton.addActionListener(LauncherPanelApplicationListener)
  add(gameButton)
  
  private val quitButton: JButton = new JButton("Quit")
  quitButton.addActionListener(LauncherPanelApplicationListener)
  add(quitButton)
}