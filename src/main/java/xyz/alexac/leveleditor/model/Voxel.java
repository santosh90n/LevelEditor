/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

/**
 *
 * @author alex-ac
 */
public class Voxel {
  public final int a;
  public final int b;
  public final int c;
  public final int n;

  public final float u;
  public final float x;
  public final float y;
  public final float z;

  public Voxel(int n, float x, float y, float z) {
    this.n = n;

    u = (float) Math.pow(2.0, -n);
    this.a = (int) Math.ceil(x / u);
    this.b = (int) Math.ceil(y / u);
    int c = (int) Math.ceil(z / u);
    if (c * u < -1) {
      c = (int) -Math.pow(2.0, n);
    }
    this.c = c;
    this.x = (float) u * a;
    this.y = (float) u * b;
    this.z = (float) u * c;
  }

  Voxel() {
    n = a = b = c = 0;
    u = 1.0f;
    x = y = z = 0.0f;
  }

  @Override
  public boolean equals(Object o) {
    if (o != null && o instanceof Voxel) {
      Voxel v = (Voxel) o;
      return v.x == x && v.y == y && v.z == z;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + Float.floatToIntBits(this.x);
    hash = 53 * hash + Float.floatToIntBits(this.y);
    hash = 53 * hash + Float.floatToIntBits(this.z);
    return hash;
  }

  @Override
  public String toString() {
    return "xyz.alexac.leveleditor.model.Voxel[x=" + x + ",y=" + y + ",z=" + z +
           "]";
  }
}
