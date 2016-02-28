/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import xyz.alexac.leveleditor.model.Block;
import xyz.alexac.leveleditor.model.Vector2D;
import xyz.alexac.leveleditor.model.Vector3D;

/**

 @author alex-ac
 */
public class BlockViewportController
        implements ViewportController,
                   Observer {
  private final Block block;
  private final Runnable redraw;
  private String theme = null;

  public BlockViewportController(Block b, Runnable redraw) {
    block = b;
    b.addObserver(this);
    this.redraw = redraw;
  }

  @Override
  public void dispose() {
    block.deleteObserver(this);
  }

  @Override
  public List<Image> getImages() {
    List<Image> images = new ArrayList<>();
    BufferedImage image = block.getImage(theme);
    if (image != null) {
      images.add(new Image(block.getOffset(), image));
    }
    return images;
  }

  @Override
  public int getMode() {
    return Viewport.MODE_VOXEL;
  }

  @Override
  public List<Vector3D> getVoxels() {
    return block.getVoxels();
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
  public void voxelClicked(Vector3D v) {
    if (block.getVoxels().contains(v)) {
      block.unsetVoxel(v);
    } else {
      block.setVoxel(v);
    }
  }

  public void setTheme(String theme) {
    if (this.theme != theme) {
      this.theme = theme;
      redraw.run();
    }
  }

  @Override
  public Color getCursorColor(Vector3D cursor) {
    if (block.getVoxels().contains(cursor)) {
      return Color.RED;
    }
    return Color.BLUE;
  }
}
