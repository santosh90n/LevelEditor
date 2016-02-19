/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import xyz.alexac.leveleditor.model.Vector2D;
import xyz.alexac.leveleditor.model.Vector3D;
import xyz.alexac.leveleditor.model.Voxel;

/**
 *
 * @author alex-ac
 */
public class RenderVoxel extends Voxel {
  public final BufferedImage left;
  public final BufferedImage right;
  public final BufferedImage top;

  public RenderVoxel(Voxel v, BufferedImage image, Vector2D offset,
                     int gridWidth, int gridHeight) {
    super(v.n, v.a, v.b, v.c);
    final int ux = gridWidth / 2 / pow_2_n;
    final int uy = gridHeight / 2 / pow_2_n;
    Vector2D uvOrigin = new Vector2D((v.a - v.b) * ux,
                                     -(v.a + v.b) * uy - 2 * v.c * uy).
             minus(offset);

    left = new BufferedImage(ux, uy * 3, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics g = left.getGraphics();
    Polygon mask = new Polygon();
    mask.addPoint(0, 0);
    mask.addPoint(ux, uy);
    mask.addPoint(ux, 3 * uy);
    mask.addPoint(0, 2 * uy);
    g.setClip(mask);
    g.drawImage(image, 0, 0, ux, 3 * uy, uvOrigin.x - ux, uvOrigin.y - 3 * uy,
                uvOrigin.x, uvOrigin.y, new Color(0, 0, 0, 0), null);

    right = new BufferedImage(ux, uy * 3, BufferedImage.TYPE_4BYTE_ABGR);
    g = right.getGraphics();
    mask = new Polygon();
    mask.addPoint(0, uy);
    mask.addPoint(ux, 0);
    mask.addPoint(ux, 2 * uy);
    mask.addPoint(0, 3 * uy);
    g.setClip(mask);
    g.drawImage(image, 0, 0, ux, 3 * uy, uvOrigin.x, uvOrigin.y - 3 * uy,
                uvOrigin.x + ux, uvOrigin.y, new Color(0, 0, 0, 0), null);

    top = new BufferedImage(ux * 2, uy * 2, BufferedImage.TYPE_4BYTE_ABGR);
    g = top.getGraphics();
    mask = new Polygon();
    mask.addPoint(0, uy);
    mask.addPoint(ux, 0);
    mask.addPoint(2 * ux, uy);
    mask.addPoint(ux, 2 * uy);
    g.setClip(mask);
    g.drawImage(image, 0, 0, 2 * ux, 2 * uy, uvOrigin.x - ux, uvOrigin.y - 4 *
                                                                           uy,
                uvOrigin.x + ux, uvOrigin.y - 2 * uy, new Color(0, 0, 0, 0),
                null);
  }

  private RenderVoxel(Voxel v, BufferedImage left, BufferedImage right,
                      BufferedImage top) {
    super(v.n, v.a, v.b, v.c);
    this.left = left;
    this.right = right;
    this.top = top;
  }

  @Override
  public RenderVoxel moved(Vector3D offset) {
    return new RenderVoxel(super.moved(offset), left, right, top);
  }

}
