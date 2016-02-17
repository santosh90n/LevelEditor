/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.util.List;
import xyz.alexac.leveleditor.model.BlockInstance;
import xyz.alexac.leveleditor.model.Vector2D;
import xyz.alexac.leveleditor.model.Voxel;

/**
 *
 * @author alex-ac
 */
public interface ViewportController {
  public int getMode();

  public void tileClicked(Vector2D position);

  public void voxelClicked(Voxel v);

  public List<BlockInstance> getBlocks();
}
