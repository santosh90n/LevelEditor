/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.util.ArrayList;
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
  private final List<String> themes = new ArrayList<>();
  private final List<Block> blocks = new ArrayList<>();

  public int getBlocksCount() {
    return blocks.size();
  }

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

  public String getTheme(int index) {
    if (index < themes.size()) {
      return themes.get(index);
    }
    return null;
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

  public void renameTheme(String theme, String name) {
    if (!themes.contains(theme) || themes.contains(name)) {
      return;
    }

    themes.remove(theme);
    themes.add(name);
    setChanged();
    notifyObservers("themes");
  }

  @Override
  public JsonObjectBuilder toJSON() {
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
            .add("themes", themes)
            .add("blocks", blocks);
  }
}
