/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import xyz.alexac.leveleditor.model.LevelElement;
import xyz.alexac.leveleditor.model.ProjectSettings;
import xyz.alexac.leveleditor.ui.EditorWindow;
import xyz.alexac.leveleditor.ui.EditorWindowDelegate;

/**
 *
 * @author alex-ac
 */
public class LevelEditor implements Runnable, EditorWindowDelegate {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new LevelEditor());
  }
  private final EditorWindow window_;
  private final JFileChooser fileChooser_;
  private final ProjectSettings settings_;

  private LevelEditor() {
    settings_ = new ProjectSettings();
    window_ = new EditorWindow(this, settings_);
    fileChooser_ = new JFileChooser();
    FileNameExtensionFilter filter =
        new FileNameExtensionFilter("*.png Portable network graphics", "png");
    fileChooser_.setFileFilter(filter);
  }

  @Override
  public void run() {
    window_.setVisible(true);
  }

  @Override
  public void openObject() {
    if (JFileChooser.APPROVE_OPTION == fileChooser_.showOpenDialog(window_)) {
      Iterator<ImageReader> imageReadersByFormatName =
              ImageIO.getImageReadersByFormatName("png");
      if (imageReadersByFormatName.hasNext()) {
        try {
          ImageReader reader = imageReadersByFormatName.next();
          reader.setInput(new FileImageInputStream(fileChooser_.getSelectedFile()));
          BufferedImage image = reader.read(0);
          LevelElement element = new LevelElement();
          element.setImage(image);
          window_.setElement(element);
        } catch (IOException ex) {
          Logger.getLogger(LevelEditor.class.getName()).log(Level.SEVERE, null,
                                                            ex);
        }
      }
    }
  }
}
