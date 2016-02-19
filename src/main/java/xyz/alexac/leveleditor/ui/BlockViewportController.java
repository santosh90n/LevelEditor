/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import xyz.alexac.leveleditor.model.Block;
import xyz.alexac.leveleditor.model.Vector2D;
import xyz.alexac.leveleditor.model.Vector3D;
import xyz.alexac.leveleditor.model.Voxel;

/**
 *
 * @author alex-ac
 */
public class BlockViewportController implements ViewportController, Observer {
  private final Block block;
  private final RenderBlockInstance renderBlockInstance;
  private final Runnable redraw;

  public BlockViewportController(Block b, Runnable redraw) {
    block = b;
    b.addObserver(this);
    this.redraw = redraw;
    renderBlockInstance = new RenderBlockInstance(block, new Vector3D());
  }

  @Override
  public List<Image> getImages() {
    List<Image> images = new ArrayList<>();
    BufferedImage image = block.getImage("<default>");
    if (image != null) {
      images.add(new Image(block.getOffset(), image));
    }
    return images;
  }

  @Override
  public int getMode() {
    return Viewport.MODE_DEFAULT;
  }

  @Override
  public List<RenderVoxel> getVoxels(int gridWidth, int gridHeight) {
    return new ArrayList<>();
  }

  @Override
  public void tileClicked(Vector2D position) {
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o == block) {
      redraw.run();
    }
  }

  @Override
  public void voxelClicked(Voxel v) {
  }

}
