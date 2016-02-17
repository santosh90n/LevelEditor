/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.util.ArrayList;
import java.util.List;
import xyz.alexac.leveleditor.model.Block;
import xyz.alexac.leveleditor.model.BlockInstance;
import xyz.alexac.leveleditor.model.Vector2D;
import xyz.alexac.leveleditor.model.Vector3D;
import xyz.alexac.leveleditor.model.Voxel;

/**
 *
 * @author alex-ac
 */
public class BlockViewportController implements ViewportController {
  private final Block block;

  public BlockViewportController(Block b) {
    block = b;
  }

  @Override
  public List<BlockInstance> getBlocks() {
    List<BlockInstance> instances = new ArrayList<>();
    instances.add(new BlockInstance(block, new Vector3D()));
    return instances;
  }

  @Override
  public int getMode() {
    return Viewport.MODE_DEFAULT;
  }

  @Override
  public void tileClicked(Vector2D position) {
  }

  @Override
  public void voxelClicked(Voxel v) {
  }

}
