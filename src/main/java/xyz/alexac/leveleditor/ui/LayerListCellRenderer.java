/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import xyz.alexac.leveleditor.model.DrawingLayer;

/**
 *
 * @author alex-ac
 */
public class LayerListCellRenderer extends JLabel implements
        ListCellRenderer<DrawingLayer> {
  @Override
  public Component getListCellRendererComponent(
          JList<? extends DrawingLayer> list,
          DrawingLayer value, int index,
          boolean isSelected,
          boolean cellHasFocus) {
    setText(value.getName() + "(visible: " + value.isVisible() +
            ", collisions: " + value.isCollisions() + ")");
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setOpaque(true);
    return this;
  }

}
