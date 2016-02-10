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
public class Layer extends Observable implements JsonSerializable {
  private String name = "layer";
  private float z = 0;
  private boolean visible = true;

  public String getName() {
    return name;
  }

  public float getZ() {
    return z;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setName(String name) {
    if (this.name == name) {
      return;
    }
    this.name = name;
    hasChanged();
    notifyObservers("name");
  }

  public void setZ(float z) {
    if (this.z == z) {
      return;
    }
    this.z = z;
    hasChanged();
    notifyObservers("z");
  }

  public void setVisible(boolean visible) {
    if (this.visible == visible) {
      return;
    }
    this.visible = visible;
    hasChanged();
    notifyObservers("visible");
  }

  @Override
  public JsonObjectBuilder toJSON() {
    return Json.createObjectBuilder()
            .add("name", name)
            .add("z", z)
            .add("visible", visible);
  }

}
