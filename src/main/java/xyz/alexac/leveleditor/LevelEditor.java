/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor;

import javax.swing.SwingUtilities;
import xyz.alexac.leveleditor.ui.EditorWindow;

/**
 *
 * @author alex-ac
 */
public class LevelEditor implements Runnable {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new LevelEditor());
  }
  private final EditorWindow window_;

  private LevelEditor() {
    window_ = new EditorWindow();
  }

  @Override
  public void run() {
    window_.setVisible(true);
  }
}
