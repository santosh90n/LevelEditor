/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author alex-ac
 */
public class Vector2D implements JsonSerializable {
  public final int x;
  public final int y;

  public Vector2D() {
    x = 0;
    y = 0;
  }

  public Vector2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Vector2D minus(Vector2D v) {
    return minus(v.x, v.y);
  }

  public Vector2D minus(int x, int y) {
    return new Vector2D(this.x - x, this.y - y);
  }

  public Vector2D plus(Vector2D v) {
    return plus(v.x, v.y);
  }

  public Vector2D plus(int x, int y) {
    return new Vector2D(this.x + x, this.y + y);
  }

  public Vector2D scale(float scale) {
    return new Vector2D((int) (x * scale), (int) (y * scale));
  }

  @Override
  public JsonObjectBuilder toJSON() {
    return Json.createObjectBuilder()
            .add("x", x)
            .add("y", y);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof Vector2D)) {
      return false;
    }
    Vector2D other = (Vector2D) o;
    return x == other.x && y == other.y;
  }

  @Override
  public String toString() {
    return "Vector2D(" + x + "," + y + ")";
  }

  static Vector2D fromJSON(JsonObject object) {
    return new Vector2D(object.getInt("x"), object.getInt("y"));
  }
}
