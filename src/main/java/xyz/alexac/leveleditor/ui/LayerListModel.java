/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import xyz.alexac.leveleditor.model.Layer;
import xyz.alexac.leveleditor.model.Project;

/**
 *
 * @author alex-ac
 */
public class LayerListModel implements Observer, ListModel<Layer> {
  private final Set<ListDataListener> listeners_ = new HashSet<>();
  private Project project = null;
  private Set<Layer> layers = new HashSet<>();

  LayerListModel() {
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listeners_.add(l);
  }

  @Override
  public Layer getElementAt(int index) {
    if (project == null) {
      return null;
    }
    Layer[] layers = project.getLayers();
    if (index < layers.length) {
      return layers[index];
    }
    return null;
  }

  @Override
  public int getSize() {
    if (project == null) {
      return 0;
    }
    return project.getLayersCount();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listeners_.remove(l);
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o == project) {
      if ((String) arg == "layers") {
        Set<Layer> layers = new HashSet<>();
        for (Layer layer : project.getLayers()) {
          layers.add(layer);
        }

        if (this.layers.containsAll(layers)) {
          // Something was removed.
          this.layers.stream()
                  .filter((Layer l) -> !layers.contains(l))
                  .forEach((Layer l) -> l.deleteObserver(this));
          this.layers.retainAll(layers);
        } else if (layers.containsAll(this.layers)) {
          // Something was added.
          layers.stream()
                  .filter((Layer l) -> !layers.contains(arg))
                  .forEach((Layer l) -> l.addObserver(this));
          this.layers.addAll(layers);
        } else {
          for (Layer layer : this.layers) {
            layer.deleteObserver(this);
          }
          for (Layer layer : layers) {
            layer.addObserver(this);
          }
          this.layers.clear();
          layers.addAll(layers);
        }
        notifyListeners();
      }
    } else if ((String) arg == "name") {
      Layer[] layers = project.getLayers();
      for (int i = 0; i < layers.length; i++) {
        if (o == layers[i]) {
          notifyListeners(i);
          break;
        }
      }
    }
  }

  private void notifyListeners() {
    ListDataEvent event =
                  new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0,
                                    getSize());
    for (ListDataListener l : listeners_) {
      l.contentsChanged(event);
    }
  }

  private void notifyListeners(int i) {
    ListDataEvent event =
                  new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, i, i);
    for (ListDataListener l : listeners_) {
      l.contentsChanged(event);
    }
  }

  void setProject(Project project) {
    if (this.project == project) {
      return;
    }
    if (this.project != null) {
      this.project.deleteObserver(this);
      for (Layer layer : this.layers) {
        layer.deleteObserver(this);
      }
    }
    this.project = project;
    layers.clear();
    if (this.project != null) {
      this.project.addObserver(this);
      for (Layer layer : this.project.getLayers()) {
        this.layers.add(layer);
        layer.addObserver(this);
      }
    }
    notifyListeners();
  }

}
