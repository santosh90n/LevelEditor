/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import xyz.alexac.leveleditor.model.Vector2D;
import xyz.alexac.leveleditor.model.Voxel;

/**

 @author alex-ac
 */
public interface ViewportController {
  public int getMode();

  public void tileClicked(Vector2D position);

  public void voxelClicked(Voxel v);

  public List<RenderVoxel> getVoxels(int gridWidth, int gridHeight);

  public Color getCursorColor(Voxel cursor);

  public class Image {
    public final Vector2D offset;
    public final BufferedImage image;

    public Image(Vector2D offset, BufferedImage image) {
      this.offset = offset;
      this.image = image;
    }
  }

  public List<Image> getImages();

  public void dispose();
}
