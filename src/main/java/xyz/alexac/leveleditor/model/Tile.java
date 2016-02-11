/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import java.util.Observable;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author alex-ac
 */
class Tile extends Observable implements JsonSerializable {
  private Vector2D position = new Vector2D();
  private String layer = null;

  public Vector2D getPosition() {
    return position;
  }

  public void setPosition(Vector2D position) {
    if (this.position == position) {
      return;
    }
    this.position = position;
    setChanged();
    notifyObservers("position");
  }

  public String getLayer() {
    return layer;
  }

  public void setLayer(String layer) {
    if (this.layer == layer) {
      return;
    }
    this.layer = layer;
    setChanged();
    notifyObservers("layer");
  }

  @Override
  public JsonObjectBuilder toJSON() {
    return Json.createObjectBuilder()
            .add("position", position.toJSON())
            .add("layer", layer);
  }
}
