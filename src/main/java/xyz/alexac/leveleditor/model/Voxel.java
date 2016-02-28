/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.model;

/**

 @author alex-ac
 */
public class Voxel
        extends Vector3D {
  public final int value;

  public Voxel(Vector3D origin, int value) {
    super(origin.x, origin.y, origin.z);
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o) || !(o instanceof Voxel)) {
      return false;
    }
    Voxel v = (Voxel) o;
    return value == v.value;
  }
}
