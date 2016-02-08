/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author alex-ac
 */
public class ProjectSettings extends Observable {
  private int tileWidth_;
  private int tileHeight_;
  private final List<DrawingLayer> layers_;

  public ProjectSettings() {
    tileWidth_ = 256;
    tileHeight_ = 128;
    layers_ = new LinkedList<>();
    layers_.add(new DrawingLayer("default", 0.0));
  }

  public int getLayersCount() {
    return layers_.size();
  }

  public void setTileWidth(int w) {
    tileWidth_ = w;
    setChanged();
    notifyObservers();
  }

  public void setTileHeight(int h) {
    tileHeight_ = h;
    setChanged();
    notifyObservers();
  }

  public int getTileWidth() {
    return tileWidth_;
  }

  public int getTileHeight() {
    return tileHeight_;
  }

  public Iterator<DrawingLayer> getLayers() {
    return layers_.iterator();
  }

  public void addLayer(DrawingLayer layer) {
    if (layers_.contains(layer)) {
      return;
    }
    layers_.add(layer);
    Collections.sort(layers_, (DrawingLayer a, DrawingLayer b) -> {
               return a.getZ() < b.getZ() ? -1 : 1;
             });

    setChanged();
    notifyObservers();
  }

  public void removeLayer(DrawingLayer layer) {
    if (!layers_.contains(layer)) {
      return;
    }
    layers_.remove(layer);

    setChanged();
    notifyObservers();
  }
}
