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
 Voxel is a cube which edges are parallel to x, y, z axes. Voxel can be
 described by 4 integer numbers.

 a, b, c, n are integer. n >= 0, c >= -1

 But more comfortable values are float:

 u - the size. x, y, z - coordinates of the origin point (the point with
 minimum coordinates).

 u = 2 ^ (- n) x = a * u; y = b * u; z = c * u.

 It's preferred to operate with integer values.

 @author alex-ac
 */
public class Voxel
        implements JsonSerializable {
  public final int a;
  public final int b;
  public final int c;
  public final int n;
  public final int p;

  public final float u;
  public final float x;
  public final float y;
  public final float z;

  public Voxel(int n, float x, float y, float z) {
    if (n < 0) {
      n = 0;
    }

    this.n = n;
    int pow_2_n = 1;
    for (int i = 0; i < n; ++i) {
      pow_2_n <<= 1;
    }
    p = pow_2_n;

    u = (float) 1 / pow_2_n;
    this.a = (int) Math.ceil(x * pow_2_n);
    this.b = (int) Math.ceil(y * pow_2_n);
    int c = (int) Math.ceil(z * pow_2_n);
    if (c < -1) {
      c = -1;
    }
    this.c = c;
    this.x = (float) u * a;
    this.y = (float) u * b;
    this.z = (float) u * c;
  }

  public Voxel(int n, int a, int b, int c) {
    if (n < 0) {
      n = 0;
    }
    this.n = n;
    int pow_2_n = 1;
    for (int i = 0; i < n; ++i) {
      pow_2_n <<= 1;
    }
    p = pow_2_n;

    if (c < -1) {
      c = -1;
    }

    this.a = a;
    this.b = b;
    this.c = c;

    u = (float) 1 / pow_2_n;
    x = u * a;
    y = u * b;
    z = u * c;
  }

  public Voxel() {
    n = a = b = c = 0;
    p = 1;
    u = 1.0f;
    x = y = z = 0.0f;
  }

  public boolean doesIntersects(Voxel v) {
    float dx = v.x - x;
    float dy = v.y - y;
    float dz = v.z - z;

    return (dx > 0 ? dx < u : -dx < v.u) && (dy > 0 ? dy < u : -dy < v.u) && (dz
            > 0 ? dz < u : -dz < v.u);
  }

  @Override
  public boolean equals(Object o) {
    if (o != null && o instanceof Voxel) {
      Voxel v = (Voxel) o;
      return v.n == n && v.a == a && v.b == b && v.c == c;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + this.a;
    hash = 17 * hash + this.b;
    hash = 17 * hash + this.c;
    hash = 17 * hash + this.n;
    return hash;
  }

  @Override
  public String toString() {
    return "xyz.alexac.leveleditor.model.Voxel[n=" + n + "x=" + x + ",y=" + y
            + ",z=" + z + "]";
  }

  public Voxel moved(Vector3D offset) {
    return new Voxel(n, a + offset.x, b + offset.y, c + offset.z);
  }

  @Override
  public JsonObjectBuilder toJSON() {
    return Json.createObjectBuilder()
            .add("n", n)
            .add("a", a)
            .add("b", b)
            .add("c", c);
  }

  public static Voxel fromJSON(JsonObject object) {
    return new Voxel(object.getInt("n"), object.getInt("a"), object.getInt("b"),
                     object.getInt("c"));
  }
}
