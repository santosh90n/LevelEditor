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
  public int x = 0;
  public int y = 0;
  public int z = 0;

  public Voxel(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  Voxel() {
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Voxel) {
      Voxel v = (Voxel) o;
      return v.x == x && v.y == y && v.z == z;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + this.x;
    hash = 97 * hash + this.y;
    hash = 97 * hash + this.z;
    return hash;
  }

  @Override
  public String toString() {
    return "xyz.alexac.leveleditor.model.Voxel[x=" + x + ",y=" + y + ",z=" + z +
           "]";
  }
}
