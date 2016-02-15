/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author alex-ac
 */
public class Vector3D implements JsonSerializable {
  public final int x;
  public final int y;
  public final int z;

  public Vector3D() {
    x = y = z = 0;
  }

  public Vector3D(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!(o instanceof Vector3D)) {
      return false;
    }
    Vector3D v = (Vector3D) o;
    return v.x == x && v.y == y && v.z == z;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + this.x;
    hash = 97 * hash + this.y;
    hash = 97 * hash + this.z;
    return hash;
  }

  @Override
  public JsonObjectBuilder toJSON() {
    return Json.createObjectBuilder()
            .add("x", x)
            .add("y", y)
            .add("z", z);
  }

  @Override
  public String toString() {
    return "Vector3D(" + x + ", " + y + ", " + z + ")";
  }
}
