/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.image.BufferedImage;
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
public class RenderBlockInstance extends BlockInstance {
  private String theme = null;
  private List<RenderVoxel> voxels = null;
  private int gridWidth = 0;
  private int gridHeight = 0;

  public RenderBlockInstance(Block block, Vector3D position) {
    super(block, position);
  }

  private static List<RenderVoxel> prepareVoxels(Block block, String theme,
                                                 int gridWidth, int gridHeight) {
    BufferedImage blockImage = block.getImage(theme);
    Vector2D imageOrigin = block.getOffset();
    List<RenderVoxel> result = new ArrayList<>();
    for (Voxel v : block.getVoxels()) {
      result.add(new RenderVoxel(v, blockImage, imageOrigin, gridWidth,
                                 gridHeight));
    }
    return result;
  }

  public List<RenderVoxel> getVoxels(String theme, int gridWidth, int gridHeight) {
    if (this.theme == null ? theme == null : this.theme.equals(theme) &&
                                             this.gridWidth == gridWidth &&
                                             this.gridHeight == gridHeight) {
      return voxels;
    }

    this.theme = theme;
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
    voxels = new ArrayList<>();
    for (RenderVoxel v : prepareVoxels(block, theme, gridWidth, gridHeight)) {
      voxels.add(v.moved(origin));
    }

    return voxels;
  }
}
