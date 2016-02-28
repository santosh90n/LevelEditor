/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

/**

 @author alex-ac
 */
public class Box {
  public final Vector3D origin;
  public final Vector3D size;

  public Box(int x, int y, int z, int width, int height, int depth) {
    origin = new Vector3D(x, y, z);
    size = new Vector3D(width, height, depth);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !(o instanceof Box)) {
      return false;
    }
    Box other = (Box) o;
    return origin.equals(other.origin) && size.equals(other.size);
  }

  @Override
  public String toString() {
    return "Box(origin = " + origin + ", size = " + size + ")";
  }
}
