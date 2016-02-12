/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.util.Observable;
import java.util.Observer;
import xyz.alexac.leveleditor.model.Layer;
import xyz.alexac.leveleditor.model.Project;

/**
 *
 * @author alex-ac
 */
public class ProjectView extends javax.swing.JPanel implements Observer {
  private Project project = null;
  private LayerListModel layerListModel = new LayerListModel();
  private ThemesListModel themesListModel = new ThemesListModel();
  private int layerNumber = 0;
  private int themeNumber = 0;

  /**
   * Creates new form ProjectView
   */
  public ProjectView() {
    initComponents();
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
    layersListScroll = new javax.swing.JScrollPane();
    layersList = new javax.swing.JList<>();
    addLayerButton = new javax.swing.JButton();
    deleteLayerButton = new javax.swing.JButton();
    themesScrollPane = new javax.swing.JScrollPane();
    themesList = new javax.swing.JList<>();
    addThemeButton = new javax.swing.JButton();
    deleteThemeButton = new javax.swing.JButton();
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
    filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));

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

    layersList.setBorder(javax.swing.BorderFactory.createTitledBorder("Layers"));
    layersList.setModel(layerListModel);
    layersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    layersList.setCellRenderer(new LayerListCellRenderer());
    layersListScroll.setViewportView(layersList);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.gridheight = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.weighty = 0.1;
    add(layersListScroll, gridBagConstraints);

    addLayerButton.setText("Add");
    addLayerButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addLayer(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(addLayerButton, gridBagConstraints);

    deleteLayerButton.setText("Delete");
    deleteLayerButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        deleteLayer(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(deleteLayerButton, gridBagConstraints);

    themesList.setBorder(javax.swing.BorderFactory.createTitledBorder("Themes"));
    themesList.setModel(themesListModel);
    themesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    themesScrollPane.setViewportView(themesList);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 7;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.gridheight = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.1;
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
    gridBagConstraints.gridy = 7;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(addThemeButton, gridBagConstraints);

    deleteThemeButton.setText("Delete");
    deleteThemeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        deleteTheme(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.1;
    add(deleteThemeButton, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 3;
    add(filler1, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.gridwidth = 3;
    add(filler2, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

  private void updateWidth(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_updateWidth
    if (project != null) {
      project.setGridHeight((Integer) gridWidthSpinner.getModel().getValue());
    }
  }//GEN-LAST:event_updateWidth

  private void updateHeight(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_updateHeight
    if (project != null) {
      project.setGridHeight((Integer) gridHeightSpinner.getModel().getValue());
    }
  }//GEN-LAST:event_updateHeight

  private void addLayer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLayer
    if (project == null) {
      return;
    }
    Layer layer = new Layer();
    layer.setName("layer #" + Integer.toString(++layerNumber));
    project.addLayer(layer);
  }//GEN-LAST:event_addLayer

  private void deleteLayer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLayer
    if (project == null || layersList.isSelectionEmpty()) {
      return;
    }
    Layer layer = layersList.getSelectedValue();
    project.removeLayer(layer);
  }//GEN-LAST:event_deleteLayer

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

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addLayerButton;
  private javax.swing.JButton addThemeButton;
  private javax.swing.JButton deleteLayerButton;
  private javax.swing.JButton deleteThemeButton;
  private javax.swing.Box.Filler filler1;
  private javax.swing.Box.Filler filler2;
  private javax.swing.JLabel gridHeightLabel;
  private javax.swing.JSpinner gridHeightSpinner;
  private javax.swing.JLabel gridWidthLabel;
  private javax.swing.JSpinner gridWidthSpinner;
  private javax.swing.JList<Layer> layersList;
  private javax.swing.JScrollPane layersListScroll;
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
      this.layerNumber = this.project.getLayersCount();
      this.themeNumber = this.project.getThemesCount();
    }
    this.layerListModel.setProject(project);
    this.themesListModel.setProject(project);
  }
}
