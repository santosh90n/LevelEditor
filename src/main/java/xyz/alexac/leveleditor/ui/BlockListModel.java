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
import xyz.alexac.leveleditor.model.Block;
import xyz.alexac.leveleditor.model.Project;

/**
 *
 * @author alex-ac
 */
public class BlockListModel implements ListModel<Block>, Observer {
  private Set<ListDataListener> listeners = new HashSet<>();
  private Project project = null;

  public void setProject(Project project) {
    if (this.project != null) {
      this.project.deleteObserver(this);
    }
    this.project = project;
    if (this.project != null) {
      this.project.addObserver(this);
    }
    notifyListeners();
  }

  @Override
  public void addListDataListener(ListDataListener l) {
    listeners.add(l);
  }

  @Override
  public Block getElementAt(int index) {
    if (project == null) {
      return null;
    }
    return project.getBlock(index);
  }

  @Override
  public int getSize() {
    if (project == null) {
      return 0;
    }
    return project.getBlocksCount();
  }

  @Override
  public void removeListDataListener(ListDataListener l) {
    listeners.remove(l);
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o == project) {
      if ((String) arg == "blocks") {
        notifyListeners();
      }
    }
  }

  private void notifyListeners() {
    ListDataEvent event =
                  new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0,
                                    getSize());
    for (ListDataListener l : listeners) {
      l.contentsChanged(event);
    }
  }

}
