/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.Component;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import xyz.alexac.leveleditor.model.Block;
import xyz.alexac.leveleditor.model.Project;

/**
 *
 * @author alex-ac
 */
public class ProjectView extends javax.swing.JPanel implements Observer {
  private Project project = null;
  private final ThemesListModel themesListModel = new ThemesListModel();
  private final BlockListModel blocksListModel = new BlockListModel();
  private int themeNumber = 0;
  private int blockNumber = 0;
  private final StateController stateController = new StateController(this);

  public class StateController extends Observable implements
          ListSelectionListener {
    private final ProjectView view;

    public StateController(ProjectView view) {
      this.view = view;
    }

    public String getCurrentTheme() {
      if (this.view.project == null ||
          this.view.themesList.isSelectionEmpty()) {
        return null;
      }
      return this.view.themesList.getSelectedValue();
    }

    public Block getCurrentBlock() {
      if (this.view.project == null ||
          this.view.blocksList.isSelectionEmpty()) {
        return null;
      }
      return this.view.blocksList.getSelectedValue();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (e.getSource() == this.view.themesList) {
        setChanged();
        notifyObservers("currentTheme");
      } else if (e.getSource() == this.view.blocksList) {
        setChanged();
        notifyObservers("currentBlock");
      }
    }
  }

  public StateController getStateController() {
    return stateController;
  }

  /**
   * Creates new form ProjectView
   */
  public ProjectView() {
    initComponents();
    themesList.addListSelectionListener(stateController);
    blocksList.addListSelectionListener(stateController);
    stateController.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o == project) {
      switch ((String) arg) {
        case "gridWidth":
          if ((Integer) gridWidthSpinner.getModel().getValue() != project.
              getGridWidth()) {
            gridWidthSpinner.getModel().setValue(project.getGridWidth());
          }
          break;
        case "gridHeight":
          if ((Integer) gridHeightSpinner.getModel().getValue() != project.
              getGridHeight()) {
            gridHeightSpinner.getModel().setValue(project.getGridHeight());
          }
          break;
      }
    } else if (o == stateController) {
      switch ((String) arg) {
        case "currentTheme": {
          boolean selected = !themesList.isSelectionEmpty();
          deleteThemeButton.setEnabled(selected);
          renameThemeButton.setEnabled(selected);
          break;
        }
        case "currentBlock": {
          boolean selected = !blocksList.isSelectionEmpty();
          deleteBlockButton.setEnabled(selected);
          renameBlockButton.setEnabled(selected);
          break;
        }
      }
    }
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

    gridWidthLabel = new javax.swing.JLabel();
    gridWidthSpinner = new javax.swing.JSpinner();
    gridHeightLabel = new javax.swing.JLabel();
    gridHeightSpinner = new javax.swing.JSpinner();
    themesScrollPane = new javax.swing.JScrollPane();
    themesList = new javax.swing.JList<>();
    addThemeButton = new javax.swing.JButton();
    deleteThemeButton = new javax.swing.JButton();
    filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
    renameThemeButton = new javax.swing.JButton();
    blocksListScroll = new javax.swing.JScrollPane();
    blocksList = new javax.swing.JList<>();
    filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
    addBlockButton = new javax.swing.JButton();
    deleteBlockButton = new javax.swing.JButton();
    renameBlockButton = new javax.swing.JButton();

    setBorder(javax.swing.BorderFactory.createTitledBorder("Project"));
    setLayout(new java.awt.GridBagLayout());

    gridWidthLabel.setText("Grid Width:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    add(gridWidthLabel, gridBagConstraints);

    gridWidthSpinner.setModel(new javax.swing.SpinnerNumberModel(256, 1, null, 1));
    gridWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        updateWidth(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    gridBagConstraints.weightx = 0.1;
    add(gridWidthSpinner, gridBagConstraints);

    gridHeightLabel.setText("Grid Height:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
    add(gridHeightLabel, gridBagConstraints);

    gridHeightSpinner.setModel(new javax.swing.SpinnerNumberModel(128, 1, null, 1));
    gridHeightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        updateHeight(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    gridBagConstraints.weightx = 0.1;
    add(gridHeightSpinner, gridBagConstraints);

    themesList.setBorder(javax.swing.BorderFactory.createTitledBorder("Themes"));
    themesList.setModel(themesListModel);
    themesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    themesScrollPane.setViewportView(themesList);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.gridheight = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.weighty = 0.1;
    add(themesScrollPane, gridBagConstraints);

    addThemeButton.setText("Add");
    addThemeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addTheme(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(addThemeButton, gridBagConstraints);

    deleteThemeButton.setText("Delete");
    deleteThemeButton.setEnabled(false);
    deleteThemeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        deleteTheme(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(deleteThemeButton, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 3;
    add(filler2, gridBagConstraints);

    renameThemeButton.setText("Rename");
    renameThemeButton.setEnabled(false);
    renameThemeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        renameTheme(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(renameThemeButton, gridBagConstraints);

    blocksList.setBorder(javax.swing.BorderFactory.createTitledBorder("Blocks"));
    blocksList.setModel(blocksListModel);
    blocksList.setCellRenderer(new BlockListCellRenderer());
    blocksListScroll.setViewportView(blocksList);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.gridheight = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.weighty = 0.1;
    add(blocksListScroll, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 7;
    gridBagConstraints.gridwidth = 3;
    add(filler3, gridBagConstraints);

    addBlockButton.setText("Add");
    addBlockButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addBlock(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(addBlockButton, gridBagConstraints);

    deleteBlockButton.setText("Delete");
    deleteBlockButton.setEnabled(false);
    deleteBlockButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        deleteBlock(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 9;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(deleteBlockButton, gridBagConstraints);

    renameBlockButton.setText("Rename");
    renameBlockButton.setEnabled(false);
    renameBlockButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        renameBlock(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 10;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(renameBlockButton, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

  private void updateWidth(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_updateWidth
    if (project != null) {
      project.setGridWidth((Integer) gridWidthSpinner.getModel().getValue());
    }
  }//GEN-LAST:event_updateWidth

  private void updateHeight(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_updateHeight
    if (project != null) {
      project.setGridHeight((Integer) gridHeightSpinner.getModel().getValue());
    }
  }//GEN-LAST:event_updateHeight

  private void addTheme(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTheme

    if (project == null) {
      return;
    }
    project.addTheme("theme #" + Integer.toString(++themeNumber));
  }//GEN-LAST:event_addTheme

  private void deleteTheme(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTheme
    if (project == null || themesList.isSelectionEmpty()) {
      return;
    }
    project.removeTheme(themesList.getSelectedValue());
  }//GEN-LAST:event_deleteTheme

  private void renameTheme(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameTheme
    if (project == null || themesList.isSelectionEmpty()) {
      return;
    }
    JFrame frame = null;
    for (Component component = this; component != null; component = component.
                                                        getParent()) {
      if (component instanceof JFrame) {
        frame = (JFrame) component;
        break;
      }
    };
    String theme = themesList.getSelectedValue();
    JDialog dialog = new RenameDialog(frame, true, theme,
                                      (String name) -> project.renameTheme(
                                              theme, name));
    dialog.setVisible(true);
  }//GEN-LAST:event_renameTheme

  private void addBlock(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBlock
    if (project == null) {
      return;
    }
    Block block = new Block();
    block.setName("block #" + Integer.toString(++blockNumber));
    project.addBlock(block);
  }//GEN-LAST:event_addBlock

  private void deleteBlock(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBlock
    if (project == null || blocksList.isSelectionEmpty()) {
      return;
    }
    project.removeBlock(blocksList.getSelectedValue());
  }//GEN-LAST:event_deleteBlock

  private void renameBlock(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameBlock
    if (project == null || blocksList.isSelectionEmpty()) {
      return;
    }
    JFrame frame = null;
    for (Component component = this; component != null; component = component.
                                                        getParent()) {
      if (component instanceof JFrame) {
        frame = (JFrame) component;
        break;
      }
    };
    Block block = blocksList.getSelectedValue();
    JDialog dialog = new RenameDialog(frame, true, block.getName(),
                                      (String name) -> block.setName(name));
    dialog.setVisible(true);
  }//GEN-LAST:event_renameBlock

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addBlockButton;
  private javax.swing.JButton addThemeButton;
  private javax.swing.JList<Block> blocksList;
  private javax.swing.JScrollPane blocksListScroll;
  private javax.swing.JButton deleteBlockButton;
  private javax.swing.JButton deleteThemeButton;
  private javax.swing.Box.Filler filler2;
  private javax.swing.Box.Filler filler3;
  private javax.swing.JLabel gridHeightLabel;
  private javax.swing.JSpinner gridHeightSpinner;
  private javax.swing.JLabel gridWidthLabel;
  private javax.swing.JSpinner gridWidthSpinner;
  private javax.swing.JButton renameBlockButton;
  private javax.swing.JButton renameThemeButton;
  private javax.swing.JList<String> themesList;
  private javax.swing.JScrollPane themesScrollPane;
  // End of variables declaration//GEN-END:variables

  void setProject(Project project) {
    if (this.project == project) {
      return;
    }
    if (this.project != null) {
      this.project.deleteObserver(this);
    }
    this.project = project;
    if (this.project != null) {
      this.project.addObserver(this);
      this.gridWidthSpinner.getModel().setValue(this.project.getGridWidth());
      this.gridHeightSpinner.getModel().setValue(this.project.getGridHeight());
      this.themeNumber = this.project.getThemesCount();
      this.blockNumber = this.project.getBlocksCount();
    }
    this.themesListModel.setProject(project);
    this.blocksListModel.setProject(project);
  }
}
