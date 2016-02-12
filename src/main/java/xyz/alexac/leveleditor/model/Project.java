/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
  private final List<Layer> layers = new ArrayList<>();
  private final List<String> themes = new ArrayList<>();
  private final List<Block> blocks = new ArrayList<>();

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
    setChanged();
    notifyObservers("gridWidth");
  }

  public void setGridHeight(int height) {
    if (gridHeight == height) {
      return;
    }
    gridHeight = height;
    setChanged();
    notifyObservers("gridHeight");
  }

  public Layer[] getLayers() {
    Layer[] layers = new Layer[this.layers.size()];
    Iterator<Layer> it = this.layers.iterator();
    for (int i = 0; i < this.layers.size() && it.hasNext(); i++) {
      layers[i] = it.next();
    }
    return layers;
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
    setChanged();
    notifyObservers("layers");
  }

  public int getLayersCount() {
    return layers.size();
  }

  public String getTheme(int index) {
    if (index < themes.size()) {
      return themes.get(index);
    }
    return null;
  }

  public void removeLayer(Layer layer) {
    if (!layers.contains(layer)) {
      return;
    }
    layers.remove(layer);
    setChanged();
    notifyObservers("layers");
  }

  public String[] getThemes() {
    String[] themes = new String[this.themes.size()];
    Iterator<String> it = this.themes.iterator();
    for (int i = 0; i < themes.length && it.hasNext(); i++) {
      themes[i] = it.next();
    }
    return themes;
  }

  public int getThemesCount() {
    return themes.size();
  }

  public void addTheme(String theme) {
    if (themes.contains(theme)) {
      return;
    }
    themes.add(theme);
    setChanged();
    notifyObservers("themes");
  }

  public void removeTheme(String theme) {
    if (!themes.contains(theme)) {
      return;
    }
    themes.remove(theme);
    setChanged();
    notifyObservers("themes");
  }

  public Block[] getBlocks() {
    return (Block[]) blocks.toArray();
  }

  public Block getBlock(int i) {
    return blocks.get(i);
  }

  public void addBlock(Block block) {
    if (blocks.contains(block)) {
      return;
    }
    blocks.add(block);
    setChanged();
    notifyObservers("blocks");
  }

  public void removeBlock(Block block) {
    if (!blocks.contains(block)) {
      return;
    }
    blocks.remove(block);
    setChanged();
    notifyObservers("blocks");
  }

  @Override
  public JsonObjectBuilder toJSON() {
    JsonArrayBuilder layers = Json.createArrayBuilder();
    for (Layer layer : this.layers) {
      layers.add(layer.toJSON());
    }
    JsonArrayBuilder blocks = Json.createArrayBuilder();
    for (Block block : this.blocks) {
      blocks.add(block.toJSON());
    }
    JsonArrayBuilder themes = Json.createArrayBuilder();
    for (String theme : this.themes) {
      themes.add(theme);
    }
    return Json.createObjectBuilder()
            .add("gridWidth", gridWidth)
            .add("gridHeight", gridHeight)
            .add("layers", layers)
            .add("themes", themes)
            .add("blocks", blocks);
  }
}
