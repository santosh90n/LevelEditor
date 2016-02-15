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
  public final float x;
  public final float y;
  public final float z;

  public Voxel(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  Voxel() {
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
