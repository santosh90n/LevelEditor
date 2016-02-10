/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author alex-ac
 */
public class Block extends Observable implements JsonSerializable {
  private String name = "block";
  private String defaultTheme = null;
  private Map<String, BufferedImage> themedImages = new TreeMap<>();
  private Vector2D offset = new Vector2D();
  private List<Tile> tiles = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (this.name == name) {
      return;
    }
    this.name = name;
    setChanged();
    notifyObservers("name");
  }

  public String getDefaultTheme() {
    return defaultTheme;
  }

  public void setDefaultTheme(String theme) {
    if (theme == null ||
        theme.isEmpty() ||
        theme.equals(defaultTheme) ||
        !themedImages.containsKey(theme)) {
      return;
    }
    defaultTheme = theme;
    setChanged();
    notifyObservers("defaultTheme");
  }

  public String[] getThemes() {
    return (String[]) themedImages.keySet().toArray();
  }

  public BufferedImage getImage(String theme) {
    if (themedImages.containsKey(theme)) {
      return themedImages.get(theme);
    }
    return null;
  }

  public void removeTheme(String theme) {
    if (!themedImages.containsKey(theme)) {
      return;
    }
    themedImages.remove(theme);
    if (theme.equals(defaultTheme)) {
      if (themedImages.isEmpty()) {
        defaultTheme = null;
      } else {
        defaultTheme = themedImages.keySet().iterator().next();
      }
      setChanged();
      notifyObservers("defaultTheme");
    }
    setChanged();
    notifyObservers("themedImages");
  }

  public void setTheme(String theme, BufferedImage image) {
    if (themedImages.containsKey(theme) &&
        themedImages.get(theme).equals(image)) {
      return;
    }

    themedImages.put(theme, image);
    setChanged();
    notifyObservers("themedImages");
    if (defaultTheme == null) {
      defaultTheme = theme;
      setChanged();
      notifyObservers("defaultTheme");
    }
  }

  public Vector2D getOffset() {
    return offset;
  }

  public void setOffset(Vector2D offset) {
    if (offset == null || this.offset.equals(offset)) {
      return;
    }
    this.offset = offset;
    setChanged();
    notifyObservers("offset");
  }

  public Tile[] getTiles() {
    return (Tile[]) tiles.toArray();
  }

  public void addTile(Tile tile) {
    if (tiles.contains(tile)) {
      return;
    }
    tiles.add(tile);
    setChanged();
    notifyObservers("tiles");
  }

  public void removeTile(Tile tile) {
    if (!tiles.contains(tile)) {
      return;
    }
    tiles.remove(tile);
    setChanged();
    notifyObservers("tiles");
  }

  @Override
  public JsonObjectBuilder toJSON() {
    // JsonArrayBuilder tiles = Json.createArrayBuilder();
    return null;
  }
}
