/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author alex-ac
 */
public class Project extends Observable implements JsonSerializable {

  private int gridWidth = 256;
  private int gridHeight = 128;
  private List<Layer> layers = new ArrayList<>();

  public int getGridWidth() {
    return gridWidth;
  }

  public int getGridHeight() {
    return gridHeight;
  }

  public void setGridWidth(int width) {
    if (gridWidth == width) {
      return;
    }
    gridWidth = width;
    hasChanged();
    notifyObservers("gridWidth");
  }

  public void setGridHeight(int height) {
    if (gridHeight == height) {
      return;
    }
    gridHeight = height;
    hasChanged();
    notifyObservers("gridHeight");
  }

  public Layer[] getLayers() {
    return (Layer[]) layers.toArray();
  }

  public Layer getLayer(int i) {
    return layers.get(i);
  }

  public void addLayer(Layer layer) {
    if (layers.contains(layer)) {
      return;
    }
    layers.add(layer);
    Collections.sort(layers, (Layer a, Layer b) -> a.getZ() < b.getZ() ? -1
                                                             : 1);
    hasChanged();
    notifyObservers("layers");
  }

  public void removeLayer(Layer layer) {
    if (!layers.contains(layer)) {
      return;
    }
    layers.remove(layer);
    hasChanged();
    notifyObservers("layers");
  }

  @Override
  public JsonObjectBuilder toJSON() {
    JsonArrayBuilder layers = Json.createArrayBuilder();
    for (Layer layer : this.layers) {
      layers.add(layer.toJSON());
    }
    return Json.createObjectBuilder()
            .add("gridWidth", gridWidth)
            .add("gridHeight", gridHeight)
            .add("layers", layers);
  }
}
