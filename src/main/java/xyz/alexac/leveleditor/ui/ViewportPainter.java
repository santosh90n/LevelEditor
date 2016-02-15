/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.alexac.leveleditor.ui;

import xyz.alexac.leveleditor.model.Block;
import xyz.alexac.leveleditor.model.Vector3D;

/**
 *
 * @author alex-ac
 */
public interface ViewportPainter {
  public void instanceBlock(Block block, Vector3D position);
}
