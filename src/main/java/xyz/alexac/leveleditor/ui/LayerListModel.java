/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import xyz.alexac.leveleditor.model.DrawingLayer;
import xyz.alexac.leveleditor.model.ProjectSettings;

/**
 *
 * @author alex-ac
 */
public class LayerListModel implements Observer, ListModel<DrawingLayer> {
  private final Set<ListDataListener> listeners_;
  private final ProjectSettings settings_;

  LayerListModel(ProjectSettings settings) {
    listeners_ = new HashSet<>();
    settings_ = settings;
    settings_.addObserver(this);
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listeners_.add(l);
  }

  @Override
  public DrawingLayer getElementAt(int index) {
    for (Iterator<DrawingLayer> it = settings_.getLayers(); it.hasNext();) {
      if (index == 0) {
        return it.next();
      }
      it.next();
      index--;
    }
    return null;
  }

  @Override
  public int getSize() {
    return settings_.getLayersCount();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listeners_.remove(l);
  }

  @Override
  public void update(Observable o, Object arg) {
    ListDataEvent event =
                  new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0,
                                    getSize());
    for (ListDataListener l : listeners_) {
      l.contentsChanged(event);
    }
  }

}
