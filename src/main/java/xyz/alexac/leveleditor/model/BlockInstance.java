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
public class BlockInstance {
  public final Vector3D origin;
  public final Block block;

  public BlockInstance(Block block, Vector3D position) {
    origin = position;
    this.block = block;
  }

  public boolean doesIntersects(BlockInstance instance) {
    for (Voxel v1 : block.getVoxels()) {
      for (Voxel v2 : instance.block.getVoxels()) {
        if (v1.doesIntersects(v2)) {
          return true;
        }
      }
    }
    return false;
  }
}
