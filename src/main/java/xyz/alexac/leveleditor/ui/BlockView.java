/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import xyz.alexac.leveleditor.model.Block;
import xyz.alexac.leveleditor.model.Project;

/**
 *
 * @author alex-ac
 */
public class BlockView extends javax.swing.JPanel implements Observer {

  private Block block = null;

  private class BlockThemesListModel implements ListModel<String>, Observer {
    private final Set<ListDataListener> listeners = new HashSet<>();
    private Block block = null;
    private final List<String> themes = new ArrayList<>();

    public void setBlock(Block block) {
      if (this.block == block) {
        return;
      }
      if (this.block != null) {
        this.block.deleteObserver(this);
      }
      this.block = block;
      themes.clear();
      if (this.block != null) {
        this.block.addObserver(this);
        themes.add("<default>");
        themes.addAll(this.block.getThemes());
      }
      notifyListeners();
    }

    @Override
    public void addListDataListener(ListDataListener l) {
      listeners.add(l);
    }

    @Override
    public String getElementAt(int index) {
      if (index >= getSize()) {
        return null;
      }
      return themes.get(index);
    }

    @Override
    public int getSize() {
      return themes.size();
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
      listeners.remove(l);
    }

    @Override
    public void update(Observable o, Object arg) {
      if (o == block) {
        switch ((String) arg) {
          case "themedImages":
            themes.clear();
            themes.add("<default>");
            themes.addAll(block.getThemes());
            notifyListeners();
            break;
        }
      }
    }

    private void notifyListeners() {
      ListDataEvent event = new ListDataEvent(
                    this, ListDataEvent.CONTENTS_CHANGED, 0, getSize());
      for (ListDataListener l : listeners) {
        l.contentsChanged(event);
      }
    }
  };

  private class NewThemeComboBoxModel implements ComboBoxModel<String>, Observer {
    private final Set<ListDataListener> listeners = new HashSet<>();
    private Block block = null;
    private Project project = null;
    private final List<String> themes = new ArrayList<>();
    private String selected = null;

    public void setProject(Project project) {
      if (this.project == project) {
        return;
      }
      if (this.project != null) {
        this.project.deleteObserver(this);
      }
      this.project = project;
      themes.clear();
      if (this.project != null) {
        this.project.addObserver(this);
        buildThemes();
      }
      notifyListeners();
    }

    public void setBlock(Block block) {
      if (this.block == block) {
        return;
      }
      if (this.block != null) {
        this.block.deleteObserver(this);
      }
      this.block = block;
      themes.clear();
      if (this.block != null) {
        this.block.addObserver(this);
        buildThemes();
      }
      notifyListeners();
    }

    @Override
    public void addListDataListener(ListDataListener l) {
      listeners.add(l);
    }

    @Override
    public String getElementAt(int index) {
      if (themes.size() <= index) {
        return null;
      }
      return themes.get(index);
    }

    @Override
    public Object getSelectedItem() {
      return selected;
    }

    @Override
    public void setSelectedItem(Object anItem) {
      selected = (String) anItem;
    }

    @Override
    public int getSize() {
      return themes.size();
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
      listeners.remove(l);
    }

    @Override
    public void update(Observable o, Object arg) {
      if (o == project) {
        switch ((String) arg) {
          case "themes":
            buildThemes();
            notifyListeners();
            break;
        }
      } else if (o == block) {
        switch ((String) arg) {
          case "themedImages":
          case "defaultImage":
            buildThemes();
            notifyListeners();
            break;
        }
      }
    }

    private void buildThemes() {
      themes.clear();
      if (block == null || project == null) {
        return;
      }
      if (block.getDefaultImage() == null) {
        themes.add("<default>");
      } else {
        themes.addAll(project.getThemes());
        themes.removeAll(block.getThemes());
      }
      if (themes.size() == 1) {
        selected = themes.get(0);
      } else {
        selected = null;
      }
    }

    private void notifyListeners() {
      ListDataEvent event = new ListDataEvent(
                    this, ListDataEvent.CONTENTS_CHANGED, 0, getSize());
    }
  }

  private final BlockThemesListModel themesListModel =
                                     new BlockThemesListModel();
  private final NewThemeComboBoxModel newImageThemeModel =
                                      new NewThemeComboBoxModel();

  private final JFileChooser fileChooser = new JFileChooser();

  /**
   * Creates new form BlockView
   */
  public BlockView() {
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "*.png - Portable Network Graphics.", "png");
    fileChooser.setFileFilter(filter);
    initComponents();
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o == block) {
      switch ((String) arg) {
        case "name":
          if (!nameField.getText().equals(block.getName())) {
            nameField.setText(block.getName());
          }
          break;
      }
    }
  }

  private void changeName() {
    if (block == null ||
        nameField.getText().isEmpty() ||
        block.getName() == nameField.getText()) {
      return;
    }
    block.setName(nameField.getText());
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    nameLabel = new javax.swing.JLabel();
    nameField = new javax.swing.JTextField();
    addImageButton = new javax.swing.JButton();
    themesListScroll = new javax.swing.JScrollPane();
    themesList = new javax.swing.JList<>();
    removeImageButton = new javax.swing.JButton();
    newImageTheme = new javax.swing.JComboBox<>();
    themeLabel = new javax.swing.JLabel();

    setBorder(javax.swing.BorderFactory.createTitledBorder("Block"));
    setLayout(new java.awt.GridBagLayout());

    nameLabel.setText("Name:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    add(nameLabel, gridBagConstraints);

    nameField.setText("name");
    nameField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(DocumentEvent e) {
        changeName();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        changeName();
      }
      @Override
      public void insertUpdate(DocumentEvent e) {
        changeName();
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.2;
    add(nameField, gridBagConstraints);

    addImageButton.setText("Add Image");
    addImageButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addImage(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    add(addImageButton, gridBagConstraints);

    themesList.setModel(themesListModel);
    themesListScroll.setViewportView(themesList);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.gridheight = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.weighty = 0.1;
    add(themesListScroll, gridBagConstraints);

    removeImageButton.setText("Remove Image");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    add(removeImageButton, gridBagConstraints);

    newImageTheme.setModel(newImageThemeModel);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    add(newImageTheme, gridBagConstraints);

    themeLabel.setText("Theme:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    add(themeLabel, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

  private BufferedImage loadImage(File file) {
    Iterator<ImageReader> it = ImageIO.
                          getImageReadersByMIMEType("image/png");
    if (it.hasNext()) {
      try {
        ImageReader reader = it.next();
        reader.setInput(new FileImageInputStream(file));
        BufferedImage image = reader.read(0);
        return image;
      } catch (IOException ex) {
        Logger.getLogger(BlockView.class.getName()).log(Level.SEVERE, null,
                                                        ex);
      }
    }
    return null;
  }

  private void addImage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addImage
    if (block == null || newImageThemeModel.getSelectedItem() == null) {
      return;
    }
    JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
    if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(window)) {
      if (block.getDefaultImage() == null) {
        block.setDefaultImage(loadImage(fileChooser.getSelectedFile()));
      } else {
        block.setTheme((String) newImageThemeModel.getSelectedItem(),
                       loadImage(fileChooser.getSelectedFile()));
      }
    }
  }//GEN-LAST:event_addImage

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addImageButton;
  private javax.swing.JTextField nameField;
  private javax.swing.JLabel nameLabel;
  private javax.swing.JComboBox<String> newImageTheme;
  private javax.swing.JButton removeImageButton;
  private javax.swing.JLabel themeLabel;
  private javax.swing.JList<String> themesList;
  private javax.swing.JScrollPane themesListScroll;
  // End of variables declaration//GEN-END:variables

  void setBlock(Block b) {
    if (block == b) {
      return;
    }
    if (block != null) {
      block.deleteObserver(this);
    }
    block = b;
    if (block != null) {
      block.addObserver(this);
      nameField.setText(block.getName());
    }
    themesListModel.setBlock(block);
    newImageThemeModel.setBlock(block);
  }

  void setProject(Project project) {
    newImageThemeModel.setProject(project);
  }
}
